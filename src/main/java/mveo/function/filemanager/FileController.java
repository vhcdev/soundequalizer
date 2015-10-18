package mveo.function.filemanager;

import javax.servlet.http.HttpServletRequest;

import mveo.services.DirectoryManagerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Secured({ "ROLE_ADMIN", "ROLE_USER" })
@RequestMapping("/user")
public class FileController {

	@Autowired
	private DirectoryManagerService dirmanager;

	@RequestMapping("/delete/{fileid}")
	public String deleteFile(@PathVariable("fileid") String fileId, HttpServletRequest request) {
		dirmanager.moveToTrash(fileId);
		String referer = request.getHeader("Referer");
		if (referer != null) {
			return "redirect:" + referer;
		} else {
			return "redirect:/";
		}

	}
}
