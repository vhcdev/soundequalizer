package mveo.function.registration;

import javax.validation.Valid;

import mveo.SimpleController;
import mveo.services.MveoUserService;
import mveo.util.Templates;
import mveo.views.ModelViewFactory;
import mveo.views.SimpleViewLogics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/registration")
public class RegistrationController {

	public static final String REDIRECT_TO_ME = "redirect:/registration";
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MveoUserService usersService;
	@Autowired
	private ModelViewFactory mvFactory;

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView initPage() {

		ModelAndView modelAndView = mvFactory.getModelView(Templates.REGISTRATION);
		modelAndView.addObject("registrationForm", new RegistrationForm());
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView submitRegitration(@Valid RegistrationForm form, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return mvFactory.getModelView(Templates.REGISTRATION);

		} else {
			String emailUsrName = form.getEmail();

			if (usersService.hasUser(emailUsrName)) {
				//
				// ModelUtils.addErrorMsg(model, "Your username " + emailUsrName
				// + " is already exist. Please choose another one");
				log.warn("Registration failed: User exists" + emailUsrName);
				SimpleViewLogics mvRegistration = (SimpleViewLogics) mvFactory.getModelView(Templates.REGISTRATION);
				mvRegistration.addErrorMsg("This email " + emailUsrName
						+ " is already exist. Please choose another one");
				return mvRegistration;

			} else {
				usersService.createUser(form.getEmail(), form.getPassword());
			}

		}

		return new ModelAndView(SimpleController.REDIRECT_TO_HOME);
	}
}
