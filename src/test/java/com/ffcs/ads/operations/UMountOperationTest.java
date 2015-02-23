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
 * @author 陈作朋 Mar 11, 2011
 * @version 1.00.00
 * @history:
 * 
 */
public class UMountOperationTest extends AbstractTest{

	private File srcFile;

	private File destFile;

	@Before
	public void init() throws Exception {
		srcFile = new File(FileUtils.getTempDirectory(),"src");
		srcFile.mkdirs();
		destFile = new File(FileUtils.getTempDirectory(),"dest");
		destFile.mkdirs();
		MountOperation operation = new MountOperation();
		operation.setSrc(srcFile.getAbsolutePath());
		operation.setDest(destFile.getAbsolutePath());
		operation.execute();
	}

	@After
	public void destroy() throws Exception {
		UMountOperation operation = new UMountOperation();
		operation.setSrc(this.srcFile.getAbsolutePath());
		operation.setDest(this.destFile.getAbsolutePath());
		operation.execute();
		FileUtils.deleteQuietly(srcFile);
		FileUtils.deleteQuietly(destFile);
	}

	@Test
	public void test1() throws Exception {
		UMountOperation operation = new UMountOperation();
		operation.setSrc(this.srcFile.getAbsolutePath());
		operation.setDest(this.destFile.getAbsolutePath());
		operation.execute();
		Assert.assertFalse(CommonHelper.hasMounted(this.destFile.getAbsolutePath()));
		operation.rollback();
		Assert.assertTrue(CommonHelper.hasMounted(this.destFile.getAbsolutePath()));
	}
}
