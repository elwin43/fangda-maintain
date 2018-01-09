/*
 * Copyright (c) 2015-2025 by HRT All rights reserved
 */
package com.fangda.maintain.web.logging;

import com.fangda.maintain.springboot.ServerControlHandler;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.lookup.StrLookup;

/**
 * @description 加载Points REST API应用的Log4j2自定义属性。
 * @version 1.0.0
 */
@Plugin(name = "points", category = StrLookup.CATEGORY)
public class MaintainRestCustomLoggingPropsLookup extends CRTLog4j2CustomPropertiesLookup {

	@Override
	protected String getDefaultApplicationPropertiesClasspath() {
		return "application.properties";
	}

	@Override
	protected String getEnvironmentApplicationPropertiesFilePath() {
		return "../../conf/application.properties";
	}

	@Override
	protected Class<?> getBoostrapClass() {
		return ServerControlHandler.class;
	}

}
