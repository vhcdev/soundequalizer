package mveo.services;

public interface MveoUserService {
	public boolean hasUser(String emailUsrName);

	public void createUser(String email, String password);
}
