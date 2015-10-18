package mveo.util;

import java.util.Collection;

import mveo.services.RoleEnum;
import mveo.services.UserWrapper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

	public static boolean hasRole(RoleEnum roleUser) {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return false;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return false;

		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		return authorities.contains(new SimpleGrantedAuthority(roleUser.toString()));

	}

	public static String getCurrentUserName() {

		UserWrapper usrWrapper = getUserPrincipal();
		String usrName = "";
		if (usrWrapper != null) {
			usrName = usrWrapper.getUsername();
		}
		return usrName;
	}

	private static UserWrapper getUserPrincipal() {
		SecurityContext context = SecurityContextHolder.getContext();
		if (context == null)
			return null;

		Authentication authentication = context.getAuthentication();
		if (authentication == null)
			return null;

		UserWrapper usrWrapper = (UserWrapper) authentication.getPrincipal();
		return usrWrapper;
	}

}
