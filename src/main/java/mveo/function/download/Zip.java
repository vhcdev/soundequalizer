package mveo.function.download;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import mveo.io.FileWrapper;
import mveo.services.DirectoryManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Zip {
	private static final String ZIP_POSTFIX = "dl.zip";
	static final int BUFFER = 2048;
	@Autowired
	private DirectoryManagerService dirmanager;

	public File compress() {
		List<FileWrapper> normalizingRp = dirmanager.getNormalingReport();
		String zipName = Long.toString(System.currentTimeMillis()) + ZIP_POSTFIX;
		String path = dirmanager.getZipDir().getPath() + "/" + zipName;
		File zipfile = new File(path);
		try {
			if (!normalizingRp.isEmpty()) {
				dirmanager.clearZipDir();
				ZipOutputStream zipOutput = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipfile)));
				for (FileWrapper file : normalizingRp) {
					zipFile(zipOutput, file.getFile());
				}
				zipOutput.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return zipfile;
	}

	private void zipFile(ZipOutputStream zipOutput, File file) throws IOException, FileNotFoundException {

		ZipEntry zentry = new ZipEntry(file.getName());
		zipOutput.putNextEntry(zentry);

		BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file), BUFFER);
		byte data[] = new byte[BUFFER];
		int count;
		while ((count = bufferedInputStream.read(data, 0, BUFFER)) != -1) {
			zipOutput.write(data, 0, count);
		}
		bufferedInputStream.close();
	}
}
