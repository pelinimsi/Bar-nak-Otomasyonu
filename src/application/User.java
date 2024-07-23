package application;

public class User {
	public static User current;
	public int id;
	public String username;
	public String password;
	public boolean is_admin;
	User(int id,String username,String password,boolean is_admin){
		this.id = id;
		this.username = username;
		this.password = password;
		this.is_admin = is_admin;
		User.current = this;
	}
}
