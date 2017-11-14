package fr.exp.log_api;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.*;
import ch.qos.logback.core.util.StatusPrinter;

public class Hello {

	static Logger helloLogger = LoggerFactory.getLogger(Hello.class);
	static ch.qos.logback.classic.Logger loggerTest = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("logger.test");
	static ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger(org.slf4j.Logger.ROOT_LOGGER_NAME);
	static ch.qos.logback.classic.Logger traceLogger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("logger.traces");

	public static void main(String[] args) {

		loggerTest.trace("loggerTest Trace log");// <== It's disable because of the logger.test level
		loggerTest.debug("loggerTest Debug log");// <== It's disable because of the logger.test level
		loggerTest.info("loggerTest Info log");
		loggerTest.warn("loggerTest Warn log");
		loggerTest.error("loggerTest Error log");

		helloLogger.trace("helloLogger Trace log");
		helloLogger.debug("helloLogger Debug log");
		helloLogger.info("helloLogger Info log");
		helloLogger.warn("helloLogger Warn log");
		helloLogger.error("helloLogger Error log");

		traceLogger.trace("traceLogger Trace log");
		traceLogger.debug("traceLogger Debug log");
		traceLogger.info("traceLogger Info log");
		traceLogger.warn("traceLogger Warn log");
		traceLogger.error("traceLogger Error log");

		rootLogger.trace("rootLogger Trace log");
		if (rootLogger.isDebugEnabled()) {
			Object entry = new Object();
			rootLogger.debug("The new entry is {}.", entry);// 30 fois plus performant
			// que de concaténer la
			// chaine à la main!
			Object[] paramArray = { entry, entry, entry };
			rootLogger.debug("Value {} was inserted between {} and {}.", paramArray);
		}
		if (rootLogger.isInfoEnabled()) {
			rootLogger.info("rootLogger Info log");
		}
		rootLogger.warn("rootLogger Warn log");
		try {
			ArrayList<String> list = new ArrayList<String>();
			list.get(1);
		} catch (Exception e) {
			rootLogger.error("An error had been thrown! That's bad my friend...", e);
		}
	}
}
