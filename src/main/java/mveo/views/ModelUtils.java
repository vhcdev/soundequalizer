package mveo.views;

import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class ModelUtils {

	public static void addErrorMsg(Model model, String msg) {
		if (model instanceof RedirectAttributes) {
			RedirectAttributes redir = (RedirectAttributes) model;
			redir.addFlashAttribute("hasError", Boolean.TRUE);
			redir.addFlashAttribute("errorMsg", msg);
		} else {

		}
		model.addAttribute("hasError", Boolean.TRUE);
		model.addAttribute("errorMsg", msg);
	}

}
