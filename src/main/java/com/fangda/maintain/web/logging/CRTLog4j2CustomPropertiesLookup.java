/*
 * Copyright (c) 2015-2025 by HRT All rights reserved
 */
package com.fangda.maintain.web.logging;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.fangda.maintain.web.disconf.DisconfLocationConfigurer;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.lookup.StrLookup;

/**
 * @description 为log4j2.xml提供适合于CRT项目使用的自定义属性，本类为抽象类，实际使用时需要创建相应的子类。
 * @version 1.0.0
 */
public abstract class CRTLog4j2CustomPropertiesLookup implements StrLookup {

	protected final Map<String, String> customProperties = new HashMap<String, String>();

	/**
	 * 子类需告知当前应用application.properties文件所在的classpath路径，如"config/application.properties".
	 * 
	 * @return
	 */
	protected abstract String getDefaultApplicationPropertiesClasspath();

	/**
	 * 子类需告知当前应用application.properties所在的服务器路径，如"../../config/application.properties"。
	 * 
	 * @return
	 */
	protected abstract String getEnvironmentApplicationPropertiesFilePath();

	/**
	 * Whether also use the file path of
	 * getEnvironmentApplicationPropertiesFilePath() as the Disconf file
	 * location. Default is false.
	 * 
	 * @return
	 */
	protected boolean alsoUseEnvAppPropertiesPathAsDisconfLocation() {
		return false;
	}

	/**
	 * 子类需告知当前应用所使用的main启动类，用于定位classpath路径中的application.properties文件。
	 * 
	 * @return
	 */
	protected abstract Class<?> getBoostrapClass();

	public CRTLog4j2CustomPropertiesLookup() {
		super();

		String defaultConfigClasspath = getDefaultApplicationPropertiesClasspath();
		String environmentCofnigFilePath = getEnvironmentApplicationPropertiesFilePath();
		Class<?> boostrapClass = getBoostrapClass();

		if (null == defaultConfigClasspath || 0 == defaultConfigClasspath.trim().length()) {
			throw new RuntimeException(getClass().getName()
					+ "中须通过getDefaultApplicationConfigClasspath()指定JAR默认application.properties的classpath路径，如：config/application.properties");
		}
		if (null == environmentCofnigFilePath || 0 == environmentCofnigFilePath.trim().length()) {
			throw new RuntimeException(getClass().getName()
					+ "中须通过getEnvironmentApplicationConfigFilePath()指定服务器环境专属的application.properties的物理路径，如：../../config/application.properties");
		}
		if (null == boostrapClass) {
			throw new RuntimeException(getClass().getName() + "中须通过getBoostrapClass()指定当前应用对应的main启动类");
		}

		loadCustomLoggingProperties(defaultConfigClasspath, environmentCofnigFilePath, getBoostrapClass());

		if (alsoUseEnvAppPropertiesPathAsDisconfLocation()) {
			DisconfLocationConfigurer.setCustomDisconfLocation(environmentCofnigFilePath);
		}
	}

	@Override
	public String lookup(String key) {
		return customProperties.get(key);
	}

	@Override
	public String lookup(LogEvent event, String key) {
		return customProperties.get(key);
	}

	/**
	 * 加载默认配置和环境专属配置文件，并进行合并。
	 * 
	 * @param defaultConfigClasspath
	 *            默认application.properties所在的classpath路径。
	 * @param environmentCofnigFilePath
	 *            环境专属的application.properties所在的物理路径。
	 * @param boostrapClass
	 *            应用的main启动类
	 */
	private void loadCustomLoggingProperties(String defaultConfigClasspath, String environmentCofnigFilePath,
			Class<?> boostrapClass) {

		Map<String, String> mergedProperties = new HashMap<String, String>();

		Properties defaultProperties = loadPropertiesFromClasspath(defaultConfigClasspath, boostrapClass);
		Iterator<Object> ite = defaultProperties.keySet().iterator();
		while (ite.hasNext()) {
			String propKey = (String) ite.next();
			mergedProperties.put(propKey, defaultProperties.getProperty(propKey));
		}

		Properties overrideProperties = loadPropertiesFromSystemFilePath(environmentCofnigFilePath);
		if (null != overrideProperties) {
			ite = overrideProperties.keySet().iterator();
			while (ite.hasNext()) {
				String propKey = (String) ite.next();
				mergedProperties.put(propKey, overrideProperties.getProperty(propKey));
			}
		}

		customProperties.clear();
		customProperties.putAll(mergedProperties);
	}

	/**
	 * 从JAR（classpath）加载默认的配置文件
	 * 
	 * @param propertiesClasspath
	 * @param boostrapClass
	 * @return
	 * @throws RuntimeException
	 */
	private static Properties loadPropertiesFromClasspath(String propertiesClasspath, Class<?> boostrapClass)
			throws RuntimeException {
		if (propertiesClasspath.startsWith("/")) {
			propertiesClasspath = propertiesClasspath.substring(1);
		}
		Properties props = new Properties();
		InputStream in = null;

		try {
			in = getClassLoader(boostrapClass).getResourceAsStream(propertiesClasspath);
			props.load(in);
		} catch (IOException e) {
			throw new RuntimeException("从classpath加载[" + propertiesClasspath + "]失败，错误信息：" + e.getMessage(), e);
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}

		return props;
	}

	/**
	 * 从外部物理路径加载环境专属的配置文件
	 * 
	 * @param propertiesFilePath
	 * @return
	 */
	private static Properties loadPropertiesFromSystemFilePath(String propertiesFilePath) {
		Properties props = new Properties();
		InputStream in = null;

		try {
			in = new FileInputStream(propertiesFilePath);
			props.load(in);
			System.out.println("成功加载环境专属的配置文件[" + propertiesFilePath + "]，共" + props.size() + "个属性被识别");
		} catch (IOException e) {
			// 如果加载环境专属配置文件路径失败，说明可能为本地开发环境
			// 由于在该时机Log4j2还没初始，还不能使用使用Logger API输出日志
			// 使用System.out输出提示
			System.out.println("无法从文件系统加载环境专属的配置文件路径[" + propertiesFilePath
					+ "]，如果当前不是本地开发环境而看到了此提示，请检查当前环境是否缺少了该配置文件，或该配置文件路径放置是否正确");
		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}

		return props;
	}

	private static ClassLoader getClassLoader(Class<?> boostrapClass) {
		ClassLoader cl = null;
		try {
			cl = Thread.currentThread().getContextClassLoader();
		} catch (Throwable ex) {
			// Cannot access thread context ClassLoader - falling back...
		}
		if (cl == null) {
			// No thread context class loader -> use class loader of this class.
			cl = boostrapClass.getClassLoader();
			if (cl == null) {
				// getClassLoader() returning null indicates the bootstrap
				// ClassLoader
				try {
					cl = ClassLoader.getSystemClassLoader();
				} catch (Throwable ex) {
					// Cannot access system ClassLoader - oh well, maybe the
					// caller can live with null...
				}
			}
		}
		return cl;
	}

}
