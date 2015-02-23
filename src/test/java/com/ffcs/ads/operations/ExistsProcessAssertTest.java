package com.ffcs.ads.operations;

import org.junit.Test;

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
public class ExistsProcessAssertTest extends AbstractTest{

	@Test(expected=Exception.class)
	public void test1() throws Exception{
		ExistsProcessAssert existsProcessAssert=new ExistsProcessAssert();
		existsProcessAssert.setProcessFlag("java");
		existsProcessAssert.setExpected(false);
		existsProcessAssert.execute();
	}
	
	@Test
	public void test2() throws Exception{
		ExistsProcessAssert existsProcessAssert=new ExistsProcessAssert();
		existsProcessAssert.setProcessFlag("java");
		existsProcessAssert.setExpected(true);
		existsProcessAssert.execute();
	}
	
	@Test(expected=Exception.class)
	public void test3() throws Exception{
		ExistsProcessAssert existsProcessAssert=new ExistsProcessAssert();
		existsProcessAssert.setProcessFlag("2222222");
		existsProcessAssert.setExpected(true);
		existsProcessAssert.execute();
	}
	
	@Test
	public void test4() throws Exception{
		ExistsProcessAssert existsProcessAssert=new ExistsProcessAssert();
		existsProcessAssert.setProcessFlag("1111111");
		existsProcessAssert.setExpected(false);
		existsProcessAssert.execute();
	}
	
}
