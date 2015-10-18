package mveo.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import mveo.util.Tools;

public class FileWrapper {

	private File file;

	public FileWrapper(File fileEntry) {
		this.file = fileEntry;
	}

	public Path getPath() {
		if (file != null) {
			return file.toPath();
		}
		return null;
	}

	public String getCheckSumMd5() {
		String checkSum = "";
		try {
			checkSum = Tools.calculateChecksum(file);
		} catch (IOException e) {

			e.printStackTrace();
		}
		return checkSum;
	}

	public String getFileName() {
		String filename = "";
		if (file != null) {
			filename = file.getName();
		}
		return filename;
	}

	public File getFile() {
		return this.file;
	}

}
