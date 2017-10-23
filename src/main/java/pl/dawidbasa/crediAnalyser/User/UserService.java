package pl.dawidbasa.crediAnalyser.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
}
