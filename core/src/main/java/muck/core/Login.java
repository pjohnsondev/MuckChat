package muck.core;

public class Login {
	private String username;
	private String password;
	private Id<ClientId> clientId;

	public Login() {

	}

	public Login(String username, String password, Id<ClientId> id) {
		this.username = username;
		this.password = password;
		this.clientId = id;
	}

	public Id<ClientId> getClientId() {
		return clientId;
	}

	public void setClientId(Id<ClientId> id) {
		this.clientId = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
