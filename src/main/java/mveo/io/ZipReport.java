package mveo.io;

import java.io.File;

public class ZipReport extends SimpleReport {

	private File zipFile;
	private String message;

	public void setZipFile(File zipfile) {
		this.zipFile = zipfile;
	}

	public File getZipFile() {
		return this.zipFile;
	}

	public void setMessage(String string) {
		this.message = string;

	}

	public String getMessage() {
		return this.message;
	}

}
