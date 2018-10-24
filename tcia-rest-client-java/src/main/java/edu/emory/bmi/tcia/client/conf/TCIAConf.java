package edu.emory.bmi.tcia.client.conf;

/**
 * Configurations read from the conf file.
 */
public class TCIAConf {
	private String authheader;
	private String authflag;
	private String username;
	private String password;
	private String baseurl;
	private String resource;

	public String getBaseurl() {
		return baseurl;
	}

	public void setBaseurl(String baseurl) {
		this.baseurl = baseurl;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getAuthheader() {
		return authheader;
	}

	public void setAuthheader(String authheader) {
		this.authheader = authheader;
	}

	public String getAuthflag() {
		return authflag;
	}

	public void setAuthflag(String authflag) {
		this.authflag = authflag;
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
