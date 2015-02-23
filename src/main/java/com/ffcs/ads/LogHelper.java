package com.ffcs.ads;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.varia.LevelRangeFilter;

import com.ffcs.ads.utils.FileHelper;

/**
 * 
 * 
 * 此类提供日志相关操作.
 * 
 * @author dylan.chen Mar 13, 2011
 * 
 */
public class LogHelper {

	private static final String DEPLOY_LOGGER_NAME = "DEPLOY_LOGGER";

	private LogHelper() {
	}
	
	/**
	 * 初始化控制台日志
	 */
	public static void initializeConsoleLogger() {
		PatternLayout layoutOfAppenderOfRootLogger = new PatternLayout("%d [%t] %-5p %-30.30c %X{traceId}-%m%n");
		ConsoleAppender consoleAppender = new ConsoleAppender(layoutOfAppenderOfRootLogger);
		consoleAppender.addFilter(new LevelRangeFilter() {
			{
				/**
				 * 控制台输出的日志:最小级别为INFO,最大为OFF.
				 */
				setLevelMin(Level.INFO);
				setLevelMax(Level.OFF);
				setAcceptOnMatch(true);
			}
		});
		BasicConfigurator.configure(consoleAppender);
	}

	/**
	 * 初始化文件系统日志
	 */
	public static void initializeFileLogger() {
		try {
			Logger rootLogger = Logger.getRootLogger();
			PatternLayout layout = new PatternLayout("%d [%t] %-5p %-30.30c %X{traceId}-%m%n");
			String logsDirPath = FilenameUtils.concat(ContextFactory.getContext().getWorkDir(), "logs");
			FileHelper.createFile(logsDirPath);
			String logFilePath = FilenameUtils.concat(logsDirPath, "output.log");
			FileAppender fileAppender = new FileAppender(layout, logFilePath);
			rootLogger.addAppender(fileAppender);
			rootLogger.setLevel(Level.DEBUG);
			initializeDeployLogger();
		} catch (Exception e) {
			throw new RuntimeException("初始化文件系统日志失败", e);
		}
	}

	/**
	 * 初始化部署日志
	 */
	private static void initializeDeployLogger() throws Exception {
		String deployLogFilePath = FilenameUtils.concat(ContextFactory.getContext().getWorkDir(), "deploy.log");
		PatternLayout layoutOfDeployLogger = new PatternLayout("%d -%m%n");
		FileAppender deployAppender = new FileAppender(layoutOfDeployLogger, deployLogFilePath);
		Logger deployLogger = Logger.getLogger(DEPLOY_LOGGER_NAME);
		deployLogger.addAppender(deployAppender);
		deployLogger.setAdditivity(false);
		deployLogger.setLevel(Level.INFO);
	}

	public static void outputToDeployLogger(String message) {
		if (message != null) {
			Logger.getLogger(LogHelper.DEPLOY_LOGGER_NAME).info(message);
		}
	}

}
