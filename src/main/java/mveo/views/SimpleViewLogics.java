package mveo.views;

import java.util.ArrayList;
import java.util.List;

import mveo.services.RoleEnum;
import mveo.util.SecurityUtils;

import org.springframework.web.servlet.ModelAndView;

public class SimpleViewLogics extends ModelAndView {

	final private List<String> errorMsg = new ArrayList<String>();

	public SimpleViewLogics() {
		init();
		initCustom();
	}

	protected void initCustom() {

	}

	public SimpleViewLogics(String viewName) {
		super(viewName);
		init();
		initCustom();
	}

	private void init() {
		this.addObject("hasError", !errorMsg.isEmpty());
		this.addObject("errorMsg", errorMsg);
		this.addObject("isUser", SecurityUtils.hasRole(RoleEnum.ROLE_USER));
		this.addObject("isAdmin", SecurityUtils.hasRole(RoleEnum.ROLE_ADMIN));
		this.addObject("isUserOrAdmin",
				SecurityUtils.hasRole(RoleEnum.ROLE_ADMIN) || SecurityUtils.hasRole(RoleEnum.ROLE_USER));
	}

	public void addErrorMsg(String msg) {
		errorMsg.add(msg);
		this.addObject("hasError", !errorMsg.isEmpty());
	}

}
