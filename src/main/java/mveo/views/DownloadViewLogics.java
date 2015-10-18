package mveo.views;

import java.util.List;

import mveo.io.DownloadFileInfo;

public class DownloadViewLogics extends SimpleViewLogics {

	public void setDownloadZipUrl(String zipurl) {
		this.addObject("getDownloadZipFileLink", zipurl);
	}

	public void setHasDownloadFile(boolean b) {
		this.addObject("hasDownloadFile", b);
	}

	public void setFileDownloadInfo(List<DownloadFileInfo> filesDownloadInfo) {
		this.addObject("getFilesDownloadInfo", filesDownloadInfo);
	}
}
