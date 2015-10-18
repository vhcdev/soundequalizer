package mveo.function.equalizer;

import mveo.function.download.DownloadController;
import mveo.function.download.Zip;
import mveo.services.DirectoryManagerService;
import mveo.util.Templates;
import mveo.views.ModelUtils;
import mveo.views.ModelViewFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Scope("session")
public class Mp3EqualizerController {

	public static final String EQUALIZING = "/equalizing";

	@Autowired
	private Zip zip;

	@Autowired
	private Mp3Gain mp3Gain;
	@Autowired
	private ModelViewFactory mvFactory;
	@Autowired
	private DirectoryManagerService directoryManager;

	@RequestMapping(value = EQUALIZING)
	public ModelAndView handleEqualizing(Model model) {
		// trigger equalizing process
		directoryManager.refreshUploadReport();

		boolean result = mp3Gain.processEqualize2();
		if (!result) {

			ModelUtils.addErrorMsg(model, "We can not process your files.");

			return mvFactory.getModelView(Templates.HOME);

		} else {
			zip.compress();
			return new ModelAndView(DownloadController.REDIRECT_TO_ME);
		}
	}

}
