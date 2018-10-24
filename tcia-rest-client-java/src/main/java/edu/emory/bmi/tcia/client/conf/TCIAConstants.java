package edu.emory.bmi.tcia.client.conf;

/**
 * The TCIA Constants
 */
public class TCIAConstants {

	// In the form "Authorization: ldap base64EncodedUNandPW"
	public static final String AUTH_SEPARATOR = ":";

	// The location of the configuration file for further configurations.
	public static final String TCIA_CONF_FILE = "src/main/resources/config.yaml";

	// The character that separates the auth mechanism against the value, as in "ldap base64EncodedUNandPW"
	public static final String AUTH_VAL_SEPARATOR = " ";
}
