package mveo.services;

import mveo.db.entity.Users;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class UserWrapper extends User {

	private Users mveouser;

	public UserWrapper(Users mveouser) {

		super(mveouser.getUsername(), mveouser.getPassword(), mveouser.getEnabled(), true, true, true, AuthorityUtils
				.createAuthorityList(RolesUtils.toString(mveouser.getRoles())));
		this.mveouser = mveouser;

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
