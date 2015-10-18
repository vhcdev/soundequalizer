package mveo;

import mveo.util.Templates;
import mveo.views.ModelViewFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SimpleController {

	public static final String REDIRECT_TO_HOME = "redirect:/home";
	@Autowired
	private ModelViewFactory mvFactory;

	@RequestMapping("/login")
	public ModelAndView handleLogin() {
		return mvFactory.getModelView("login");
	}

	@RequestMapping("/faq")
	public ModelAndView handleFaQ() {
		return mvFactory.getModelView("faq");
	}

	@RequestMapping("/about")
	public ModelAndView handleAbout() {
		return mvFactory.getModelView("about");
	}

	@RequestMapping("/howto")
	public ModelAndView handleHowto() {
		return mvFactory.getModelView("howtopage");
	}

	@RequestMapping(value = "/home")
	public ModelAndView handleHomeRequest() {
		return mvFactory.getModelView(Templates.HOME);

	}

	@RequestMapping("/")
	public ModelAndView home() {
		return mvFactory.getModelView(Templates.HOME);
	}

}
