package mveo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mveo.services.DirectoryManagerService;
import mveo.services.RoleEnum;
import mveo.util.SecurityUtils;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private DirectoryManagerService dirmanager;

	public AuthenticationSuccessHandler(String url, DirectoryManagerService dirmanager2) {
		super(url);
		this.dirmanager = dirmanager2;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		super.onAuthenticationSuccess(request, response, authentication);
		if (SecurityUtils.hasRole(RoleEnum.ROLE_USER) || SecurityUtils.hasRole(RoleEnum.ROLE_ADMIN)) {
			String userName = SecurityUtils.getCurrentUserName();

			dirmanager.initDirectory(userName);
		}

	}
}
