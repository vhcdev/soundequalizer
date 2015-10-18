package mveo.io;

public class SimpleReport {

	private boolean successful;
	private Exception error;
	protected String checkSumMd5;
	private String contentType = "";
	private String fileName = "";
	private String path = "";

	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getPath() {
		return this.path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public boolean isSuccessful() {
		return this.successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public Exception getError() {
		return this.error;
	}

	public void setError(Exception error) {
		this.error = error;
	}

	public String getCheckSumMd5() {
		return this.checkSumMd5;
	}

	public void setCheckSumMd5(String checkSumMd5) {
		this.checkSumMd5 = checkSumMd5;
	}

}
