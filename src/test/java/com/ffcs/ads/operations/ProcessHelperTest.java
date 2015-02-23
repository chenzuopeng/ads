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
public class ProcessHelperTest extends AbstractTest{

	@Test
	public void testExistProcess1() throws Exception {
		boolean b = CommonHelper.existsProcess("java");
		Assert.assertTrue(b);
	}

	@Test
	public void testExistProcess2() throws Exception {
		boolean b = CommonHelper.existsProcess("java1");
		Assert.assertFalse(b);
	}

}
