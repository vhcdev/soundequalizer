package mveo.function.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mveo.io.DownloadFileInfo;
import mveo.io.FileWrapper;
import mveo.services.DirectoryManagerService;
import mveo.util.Templates;
import mveo.views.DownloadViewLogics;
import mveo.views.ModelViewFactory;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
@RequestMapping("/download")
public class DownloadController {

	/**
	 * Size of a byte buffer to read/write file
	 */
	private static final int BUFFER_SIZE = 4096;

	public static final String REDIRECT_TO_ME = "redirect:/download";

	@Autowired
	private ModelViewFactory mvFactory;
	@Autowired
	private DirectoryManagerService dirManager;
	@Autowired
	private Zip zipper;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView handleDownloadRequest() {
		DownloadViewLogics modelView = (DownloadViewLogics) mvFactory.getModelView(Templates.DOWNLOAD);

		String zipurl = "/download/zip/";

		modelView.setDownloadZipUrl(zipurl);
		modelView.setHasDownloadFile(!dirManager.getNormalingReport().isEmpty());
		modelView.setFileDownloadInfo(generateDownloadFileLink());
		return modelView;
	}

	private List<DownloadFileInfo> generateDownloadFileLink() {
		ArrayList<DownloadFileInfo> infos = new ArrayList<DownloadFileInfo>();
		List<FileWrapper> normalizingReport = dirManager.getNormalingReport();

		for (FileWrapper report : normalizingReport) {

			final DownloadFileInfo info = new DownloadFileInfo();
			String checkSum = report.getCheckSumMd5();
			info.setLink("/download/" + checkSum);
			info.setName(report.getFileName());
			info.setFileid(checkSum);
			infos.add(info);

		}
		return infos;
	}

	@RequestMapping(value = "/{fileid}")
	public void downloadFile(@PathVariable("fileid") String fileId, HttpServletRequest request,
			HttpServletResponse response) throws FileNotFoundException, IOException {
		File file = dirManager.findFile(fileId);
		String fullPath = file.getAbsolutePath();
		log.info("download file: " + fullPath);
		if (!StringUtils.isEmpty(fullPath)) {

			createDownload(request, response, fullPath);
		}

	}

	@RequestMapping(value = "/zip/")
	public void handleFileDownload(HttpServletRequest request, HttpServletResponse response) throws IOException {
		File zipfile = zipper.compress();
		// File file = dirManager.findFile(fileId);
		String fullPath = zipfile.getAbsolutePath();
		log.info("download zip: " + fullPath);
		createDownload(request, response, fullPath);
	}

	private void createDownload(HttpServletRequest request, HttpServletResponse response, String fullPath)
			throws FileNotFoundException, IOException {
		// get absolute path of the application
		ServletContext context = request.getServletContext();

		// construct the complete absolute path of the file

		File downloadFile = new File(fullPath);
		FileInputStream inputStream = new FileInputStream(downloadFile);
		// get MIME type of the file
		String mimeType = context.getMimeType(fullPath);
		if (mimeType == null) {
			// set to binary type if MIME mapping not found
			mimeType = "application/octet-stream";
		}
		// set content attributes for the response
		response.setContentType(mimeType);
		response.setContentLength((int) downloadFile.length());

		// set headers for the response
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
		response.setHeader(headerKey, headerValue);

		// get output stream of the response
		OutputStream outStream = response.getOutputStream();

		byte[] buffer = new byte[BUFFER_SIZE];
		int bytesRead = -1;

		// write bytes read from the input stream into the output stream
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}

		inputStream.close();
		outStream.close();
	}
}
