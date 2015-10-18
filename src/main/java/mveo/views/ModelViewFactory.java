package mveo.views;

import mveo.util.Templates;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;

@Component
public class ModelViewFactory {

	public ModelAndView getModelView(String viewName) {
		ModelAndView mv = null;
		if (Templates.HOME.equals(viewName)) {
			mv = new HomeViewLogics(viewName);
		} else if (Templates.DOWNLOAD.equals(viewName)) {
			mv = new DownloadViewLogics();
		} else {
			mv = new SimpleViewLogics(viewName);
		}
		mv.setViewName(viewName);
		return mv;
	}
}
