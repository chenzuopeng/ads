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
public class AvailableHttpURLAssertTest extends AbstractTest{
   
	@Test
	public void test1() throws Exception{
		AvailableHttpURLAssert availableHttpURLAssert=new AvailableHttpURLAssert();
		availableHttpURLAssert.setUrl("http://www.163.com");
		availableHttpURLAssert.setExpected(true);
		availableHttpURLAssert.execute();
	}
	
	@Test(expected=Exception.class)
	public void test2() throws Exception{
		AvailableHttpURLAssert availableHttpURLAssert=new AvailableHttpURLAssert();
		availableHttpURLAssert.setUrl("http://www.163.com");
		availableHttpURLAssert.setExpected(false);
		availableHttpURLAssert.execute();
	}
	
	@Test(expected=Exception.class)
	public void test3() throws Exception{
		AvailableHttpURLAssert availableHttpURLAssert=new AvailableHttpURLAssert();
		availableHttpURLAssert.setUrl("http://afsfafsaf");
		availableHttpURLAssert.setExpected(true);
		availableHttpURLAssert.execute();
	}
	
	@Test
	public void test4() throws Exception{
		AvailableHttpURLAssert availableHttpURLAssert=new AvailableHttpURLAssert();
		availableHttpURLAssert.setUrl("http://afsfafsaf");
		availableHttpURLAssert.setExpected(false);
		availableHttpURLAssert.execute();
	}
	
	@Test(expected=Exception.class)
	public void test5() throws Exception{
		AvailableHttpURLAssert availableHttpURLAssert=new AvailableHttpURLAssert();
		availableHttpURLAssert.setUrl("http://100.0.0.0");
		availableHttpURLAssert.setExpected(true);
		availableHttpURLAssert.execute();
	}
	
	@Test
	public void test6() throws Exception{
		AvailableHttpURLAssert availableHttpURLAssert=new AvailableHttpURLAssert();
		availableHttpURLAssert.setUrl("http://100.0.0.0");
		availableHttpURLAssert.setExpected(false);
		availableHttpURLAssert.execute();
	}
	
}
