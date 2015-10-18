package mveo.function.equalizer;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import mveo.io.FileWrapper;
import mveo.services.DirectoryManagerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Mp3Gain {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DirectoryManagerService dirmanager;

	private boolean execute(Path targetFile) throws IOException, InterruptedException {

		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec("mp3gain -a " + targetFile.toString());
		int exitValue = process.waitFor();
		boolean result = false;
		if (exitValue == 0) {
			result = true;
		}

		return result;
	}

	public boolean processEqualize2() {

		List<FileWrapper> uploadReport = dirmanager.getUploadReports();
		if (uploadReport.isEmpty()) {
			return false;
		}

		for (FileWrapper fw : uploadReport) {
			boolean result = false;
			try {
				result = this.execute(fw.getPath());
				if (result) {
					Path uploadFilePath = FileSystems.getDefault().getPath(dirmanager.getUploadDir().getPath(),
							fw.getFileName());
					Path downloadFilePath = FileSystems.getDefault().getPath(dirmanager.getDownloadDir().getPath(),
							fw.getFileName());
					// move to download dir
					Files.move(uploadFilePath, downloadFilePath, StandardCopyOption.REPLACE_EXISTING);

				}

			} catch (IOException | InterruptedException e) {
				log.error("Can not equalize file" + fw.getFileName(), e);
			}

		}
		return true;

	}

}
