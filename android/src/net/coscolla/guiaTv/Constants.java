package net.coscolla.guiaTv;

public class Constants {

	public static final String SERVER = "http://192.168.2.117:9000";
	public static final String SERVER_CONTEXT ="/";
	
	public static String getServerUrl()
	{
		return SERVER + SERVER_CONTEXT;
	}
}
