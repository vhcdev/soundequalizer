package mveo.function.upload;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpSession;

import mveo.io.AjaxMsg;
import mveo.io.MsgType;
import mveo.services.DirectoryManagerService;
import mveo.services.RoleEnum;
import mveo.util.SecurityUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class UploadController {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	private int configmaxfiles;
	@Autowired
	private Environment env;

	@Autowired
	private DirectoryManagerService dirmanager;

	@PostConstruct
	public void init() {
		configmaxfiles = 5;
		try {
			configmaxfiles = Integer.parseInt(env.getProperty("free.maxupload.files"));
		} catch (Exception e) {
			log.warn("no config parameter for free.maxupload.files in application.properties" + " default value is: 5");
		}

	}

	/**
	 * Handle ajax upload file post request
	 * 
	 * @param files
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/uploaddir", method = RequestMethod.POST, consumes = "multipart/form-data")
	@ResponseBody
	public AjaxMsg handleFilesUploadAnonymous(@RequestParam("files[]") MultipartFile[] files, Model model,
			HttpSession session) {
		AjaxMsg ms = new AjaxMsg();

		if (files.length > configmaxfiles && !SecurityUtils.hasRole(RoleEnum.ROLE_USER)) {
			ms.setType(MsgType.ERROR);
			ms.setText("Please login to upload more than " + configmaxfiles + " files");

		} else {

			if (processUploadingFiles(files, session)) {
				ms.setType(MsgType.INFO);
				ms.setText("Upload successful");
			}
		}
		return ms;
	}

	private boolean processUploadingFiles(MultipartFile[] files, HttpSession session) {
		boolean rs = false;
		try {

			if (!SecurityUtils.hasRole(RoleEnum.ROLE_USER) || SecurityUtils.hasRole(RoleEnum.ROLE_ADMIN)) {
				dirmanager.initDirectory(session.getId());
			}

			for (MultipartFile file : files) {
				// save to upload folder
				if (!file.isEmpty()) {
					String contenType = file.getContentType();

					if ("audio/mp3".equals(contenType)) {
						try {
							String name = "" + file.getOriginalFilename();
							name = name.replaceAll("[^a-zA-Z0-9.-]", "_");

							Path targetFile = FileSystems.getDefault().getPath(dirmanager.getUploadDir().getPath(),
									name);
							byte[] bytes = file.getBytes();
							File mp3File = new File(name);
							BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(mp3File));
							stream.write(bytes);
							stream.close();

							Files.move(mp3File.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);

						} catch (IOException e) {

							log.error("exception ", e);
						}
					}
				}
			}
			dirmanager.refreshUploadReport();
			rs = true;

		} catch (Exception e) {
			log.error("", e);
			rs = false;
		}
		return rs;

	}
}
