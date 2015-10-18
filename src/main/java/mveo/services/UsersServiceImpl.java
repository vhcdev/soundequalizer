package mveo.services;

import java.util.HashSet;
import java.util.Set;

import mveo.db.entity.Roles;
import mveo.db.entity.UserRepository;
import mveo.db.entity.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsersServiceImpl implements UserDetailsService, MveoUserService {

	@Autowired
	private UserRepository userDao;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Users user = userDao.findByUsername(username);
		if (user != null) {

			return new UserWrapper(user);

		}
		return null;
	}

	public boolean hasUser(String emailUsrName) {
		Users usr = userDao.findByUsername(emailUsrName);

		if (usr != null) {
			return true;
		}

		return false;
	}

	@Transactional
	public void createAdmin(String email, String password) {
		Users usr = newUser(email, password);
		Roles role = newRoleAdmin(usr);
		usr.getRoles().add(role);
		userDao.save(usr);

	}

	private Roles newRoleAdmin(Users usr) {
		Roles role = new Roles();
		role.setAuthority(RoleEnum.ROLE_ADMIN.toString());
		role.setUser(usr);
		return role;
	}

	private Users newUser(String email, String password) {
		Users usr = new Users();
		usr.setEnabled(true);
		usr.setUsername(email);
		usr.setPassword(password);
		Set<Roles> roles = new HashSet<Roles>();
		usr.setRoles(roles);

		Roles role = newRoleUser(roles);
		role.setUser(usr);
		return usr;
	}

	private Roles newRoleUser(Set<Roles> roles) {
		Roles role = new Roles();
		role.setAuthority(RoleEnum.ROLE_USER.toString());
		roles.add(role);
		return role;
	}

	@Transactional
	public void createUser(String email, String password) {
		Users usr = newUser(email, password);
		userDao.save(usr);

	}
}
