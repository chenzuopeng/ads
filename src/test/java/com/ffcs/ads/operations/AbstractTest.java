package com.ffcs.ads.operations;

import java.io.File;

import mockit.NonStrictExpectations;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.varia.LevelRangeFilter;
import org.easymock.EasyMock;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.ffcs.ads.Config;
import com.ffcs.ads.ContextFactory;

/**
 * 
 * 
 * @Copyright: Copyright (c) 2008 FFCS All Rights Reserved
 * @Company: 北京福富软件有限公司
 * @author 陈作朋 Mar 13, 2011
 * @version 1.00.00
 * @history:
 * 
 */
public class AbstractTest {

	protected static File tmpDir;

	@BeforeClass
	public static void initClass(){
		AbstractTest.tmpDir = new File(FileUtils.getTempDirectoryPath(), "MoveOperationTest");
		AbstractTest.tmpDir.mkdir();
		initLog();
	}
	
	private static void initLog(){
		PatternLayout layoutOfAppenderOfRootLogger = new PatternLayout("%d [%t] %-5p %-30.30c %X{traceId}-%m%n");
		ConsoleAppender consoleAppender = new ConsoleAppender(layoutOfAppenderOfRootLogger);
		consoleAppender.addFilter(new LevelRangeFilter() {
			{
				/**
				 * 控制台输出的日志:最小级别为DEBUG,最大为OFF.
				 */
				setLevelMin(Level.DEBUG);
				setLevelMax(Level.OFF);
				setAcceptOnMatch(true);
			}
		});
		BasicConfigurator.configure(consoleAppender);
	}

	protected static void initContent() {
		
		new NonStrictExpectations(Config.class) {
			{
				this.invoke(Config.class, "getInstance", new Object[0]);
				Config config = EasyMock.createMock(Config.class);
				EasyMock.expect(config.getWorkspaceHome()).andReturn(tmpDir.getAbsolutePath());
				EasyMock.replay(config);
				result = config;
			}
		};
		ContextFactory.init(false, "ads-target-dir");
	}
	
	@AfterClass
	public static void destroyClass(){
		FileUtils.deleteQuietly(AbstractTest.tmpDir);
	}

}
