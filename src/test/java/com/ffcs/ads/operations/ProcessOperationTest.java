package com.ffcs.ads.operations;

import org.junit.Assert;
import org.junit.Test;

import com.ffcs.ads.utils.CommonHelper;

/**
 *
 *
 * @Copyright: Copyright (c) 2008 FFCS All Rights Reserved
 * @Company: 北京福富软件有限公司
 * @author 陈作朋 Mar 10, 2011
 * @version 1.00.00
 * @history:
 *
 */
public class ProcessOperationTest extends AbstractTest{

	final static String PROCESS_FLAG_TOMCAT="org.apache.catalina.startup.Bootstrap";

	@Test
	public void testStartup() throws Exception{
		ProcessOperation operation=new ProcessOperation();
		operation.setProcessFlag(PROCESS_FLAG_TOMCAT);
		operation.setStartupCommand("/home/czp/java/tomcat/6.0.26/bin/startup.sh");
		operation.setShutdownCommand("/home/czp/java/tomcat/6.0.26/bin/shutdown.sh");
		operation.setStartup(false);
		operation.execute();
		
		operation.setStartup(true);
		operation.execute();
        Assert.assertTrue(CommonHelper.existsProcess(PROCESS_FLAG_TOMCAT));
        operation.rollback();
        Assert.assertFalse(CommonHelper.existsProcess(PROCESS_FLAG_TOMCAT));
	}

	@Test
	public void testShutdown() throws Exception{
		ProcessOperation operation=new ProcessOperation();
		operation.setProcessFlag(PROCESS_FLAG_TOMCAT);
		operation.setStartupCommand("/home/czp/java/tomcat/6.0.26/bin/startup.sh");
		operation.setShutdownCommand("/home/czp/java/tomcat/6.0.26/bin/shutdown.sh");
		operation.setStartup(true);
		operation.execute();
		
		operation.setStartup(false);
		operation.execute();
        Assert.assertFalse(CommonHelper.existsProcess(PROCESS_FLAG_TOMCAT));
        operation.rollback();
        Assert.assertTrue(CommonHelper.existsProcess(PROCESS_FLAG_TOMCAT));
	}
	
	
}
