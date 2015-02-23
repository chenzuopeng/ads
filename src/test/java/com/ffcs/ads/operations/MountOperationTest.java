package com.ffcs.ads.operations;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
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
public class MountOperationTest extends AbstractTest{

	private File srcFile;

	private File destFile;

	@Before
	public void init() throws Exception {
		srcFile = new File(FileUtils.getTempDirectory(),"src");
		srcFile.mkdirs();
		destFile = new File(FileUtils.getTempDirectory(),"dest");
		destFile.mkdirs();
	}

	@After
	public void destroy() throws Exception {
		FileUtils.deleteQuietly(srcFile);
		FileUtils.deleteQuietly(destFile);
	}

	@Test
	public void testMount() throws Exception {
		MountOperation operation = new MountOperation();
		operation.setSrc(srcFile.getAbsolutePath());
		operation.setDest(destFile.getAbsolutePath());
		operation.execute();
		Assert.assertTrue(CommonHelper.hasMounted(destFile.getAbsolutePath()));
		operation.rollback();
		Assert.assertFalse(CommonHelper.hasMounted(destFile.getAbsolutePath()));
	}
	
	@Test
	public void testMountAndCreateDestFile() throws Exception {
		File destFile1=new File(FileUtils.getTempDirectory(),"dest1");
		MountOperation operation = new MountOperation();
		operation.setSrc(srcFile.getAbsolutePath());
		operation.setDest(destFile1.getAbsolutePath());
		operation.execute();
		Assert.assertTrue(CommonHelper.hasMounted(destFile1.getAbsolutePath()));
		Assert.assertTrue(destFile1.exists());
		operation.rollback();
		Assert.assertFalse(CommonHelper.hasMounted(destFile1.getAbsolutePath()));
		Assert.assertFalse(destFile1.exists());
	}

}
