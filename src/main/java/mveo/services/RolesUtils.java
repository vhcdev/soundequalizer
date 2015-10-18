package mveo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import mveo.db.entity.Roles;

public class RolesUtils {

	public static String[] toString(Set<Roles> roles) {
		List<String> rolesStr = new ArrayList<String>();
		for (Roles r : roles) {
			rolesStr.add(r.getAuthority());
		}
		return rolesStr.toArray(new String[rolesStr.size()]);

	}
}
