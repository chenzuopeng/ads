package com.ffcs.ads.utils;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 *
 * @Copyright: Copyright (c) 2008 FFCS All Rights Reserved 
 * @Company: 北京福富软件有限公司 
 * @author 陈作朋 Mar 12, 2011
 * @version 1.00.00
 * @history:
 * 
 */
public class FileHelperTest {
	
	@Test
	public void test1() throws Exception{
		File file1 = new File(FileUtils.getTempDirectory(), "file1");
		file1.createNewFile();
		FileUtils.write(file1, "123456");
		
		File bakFile=new File(FileUtils.getTempDirectory(), "file1.bak");
		FileHelper.copyFile(file1,bakFile);
		FileUtils.write(bakFile, FileUtils.readFileToString(bakFile)+"fafsaf");
		Assert.assertFalse(FileHelper.equalsFile(file1, bakFile));
		
	}
}
