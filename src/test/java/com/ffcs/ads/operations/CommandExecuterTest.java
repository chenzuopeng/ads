package com.ffcs.ads.operations;

import org.junit.Assert;
import org.junit.Test;

import com.ffcs.ads.operations.CommandExecuter.Result;

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
public class CommandExecuterTest extends AbstractTest{

	@Test
	public void test1() throws Exception{
		CommandExecuter commandExecuter=new CommandExecuter(new String[] { "ps -ef|grep java|egrep -v grep" },null);
		commandExecuter.execute();
		Result result=commandExecuter.getResult();
	    String output=commandExecuter.getOutput();
	    Assert.assertEquals(Result.SUCCESS,result);
	    Assert.assertTrue(output.contains("java"));
	}

}
