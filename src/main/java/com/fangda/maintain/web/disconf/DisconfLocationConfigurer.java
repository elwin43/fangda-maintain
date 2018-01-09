package com.fangda.maintain.web.disconf;

import java.io.File;
import java.io.IOException;

/**
 * @description Override the default Disconf configuration location.
 * @version 1.0.0
 */
public abstract class DisconfLocationConfigurer {
	private static final String DISCONF_LOCATION_JVM_ARG = "disconf.conf";

	/**
	 * Specify the JVM argument for Disconf
	 * 
	 * @param disconfLocation
	 */
	public static void setCustomDisconfLocation(String disconfLocation) {
		if (null != disconfLocation && !disconfLocation.trim().isEmpty()) {
			File file = new File(disconfLocation);
			if (file.exists()) {
				try {
					String physicalPath = file.getCanonicalPath();
					System.setProperty(DISCONF_LOCATION_JVM_ARG, physicalPath);
					System.out.println("The Disconf configuration location has set to: " + physicalPath);
				} catch (IOException e) {
					throw new RuntimeException("无法解析的文件路径：" + disconfLocation);
				}
			}
		}
	}

}
