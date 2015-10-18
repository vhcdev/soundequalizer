package mveo.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import mveo.io.FileWrapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class DirectoryManagerService {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private Environment env;

	private boolean hasDownloadDir = false;
	private boolean hasUploadDir = false;
	private boolean hasZipDir = false;
	private boolean hasTrashDir = false;
	private File trashDir;
	private File downloadDir;
	private File uploadDir;
	private File zipDir;
	private List<FileWrapper> uploadFiles = new ArrayList<FileWrapper>();
	private List<FileWrapper> normalizingFiles = new ArrayList<FileWrapper>();
	private FileWrapper zipFile = null;

	public void refreshAll() {
		this.refreshNormalizingReport();
		this.refreshUploadReport();
		this.refreshZipFile();
	}

	public void refreshUploadReport() {

		uploadFiles.clear();

		if (uploadDir != null) {
			for (final File fileEntry : uploadDir.listFiles()) {
				if (!fileEntry.isDirectory()) {
					uploadFiles.add(new FileWrapper(fileEntry));
				}
			}
		}
	}

	public void refreshNormalizingReport() {

		normalizingFiles.clear();

		if (downloadDir != null) {
			for (final File fileEntry : downloadDir.listFiles()) {
				if (!fileEntry.isDirectory()) {
					normalizingFiles.add(new FileWrapper(fileEntry));
				}
			}
		}
	}

	public void refreshZipFile() {

		if (zipDir != null) {
			for (final File fileEntry : zipDir.listFiles()) {
				if (!fileEntry.isDirectory()) {
					this.zipFile = new FileWrapper(fileEntry);
					break;
				}
			}
		}
	}

	public void initDirectory(String usrDir) {

		downloadDir = createDirectory("appdata/" + usrDir + "/" + env.getProperty("dowload.dir"));
		uploadDir = createDirectory("appdata/" + usrDir + "/" + env.getProperty("upload.dir"));
		zipDir = createDirectory("appdata/" + usrDir + "/" + env.getProperty("zip.dir"));
		trashDir = createDirectory("appdata/" + usrDir + "/" + env.getProperty("trash.dir"));
		if (downloadDir != null) {
			hasDownloadDir = true;
		}
		if (uploadDir != null) {
			hasUploadDir = true;
		}
		if (zipDir != null) {
			hasZipDir = true;
		}
		if (trashDir != null) {
			hasTrashDir = true;
		}

	}

	public boolean isHasDownloadDir() {
		return this.hasDownloadDir;
	}

	public boolean isHasUploadDir() {
		return this.hasUploadDir;
	}

	public boolean isHasZipDir() {
		return this.hasZipDir;
	}

	public File getDownloadDir() {
		return this.downloadDir;
	}

	public void setDownloadDir(File downloadDir) {
		this.downloadDir = downloadDir;
	}

	public File getUploadDir() {
		return this.uploadDir;
	}

	public void setUploadDir(File uploadDir) {
		this.uploadDir = uploadDir;
	}

	public File getZipDir() {
		return this.zipDir;
	}

	public void setZipDir(File zipDir) {
		this.zipDir = zipDir;
	}

	public boolean hasDownloadDir() {
		if (downloadDir != null) {
			return true;
		}
		return false;
	}

	private File createDirectory(String dir) {
		File d = new File(dir);
		if (!d.exists()) {

			boolean ok = d.mkdirs();
			if (!ok) {
				log.error("error  can not create directory " + d.getAbsolutePath());
				return null;
			}
		}
		return d;
	}

	public List<FileWrapper> getUploadReports() {
		this.refreshUploadReport();
		return this.uploadFiles;

	}

	public List<FileWrapper> getNormalingReport() {
		this.refreshNormalizingReport();
		return this.normalizingFiles;
	}

	public FileWrapper getZipFile() {
		if (this.zipFile == null) {
			this.refreshZipFile();
		}
		return this.zipFile;
	}

	public File findFile(String fileChecksum) {
		for (FileWrapper file : normalizingFiles) {
			if (file.getCheckSumMd5().equals(fileChecksum)) {
				return file.getFile();
			}
		}

		if (this.zipFile != null && this.zipFile.getCheckSumMd5().equals(fileChecksum)) {
			return this.zipFile.getFile();
		}
		return null;
	}

	public void clearZipDir() {
		clearFolder(zipDir);
	}

	private void clearFolder(File folder) {
		File[] files = folder.listFiles();
		if (files != null) { // some JVMs return null for empty dirs
			for (File f : files) {
				if (f.isDirectory()) {
					clearFolder(f);
				} else {
					f.delete();
				}
			}
		}
	}

	public void moveToTrash(String fileId) {
		File file = this.findFile(fileId);
		if (file != null) {

			Path targetFile = FileSystems.getDefault().getPath(trashDir.getAbsolutePath(), file.getName());
			try {
				Files.move(file.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {

				e.printStackTrace();
			}
		}

	}
}
