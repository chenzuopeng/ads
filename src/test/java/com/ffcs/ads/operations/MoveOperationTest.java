package com.ffcs.ads.operations;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.ffcs.ads.utils.FileHelper;

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
public class MoveOperationTest extends AbstractTest {

	File srcDir;

	File destDir;

	File srcDirBackup;

	File srcFile;

	File destFile;

	File srcFileBackup;

	@Before
	public void init() throws Exception {

		initContent();

		this.srcDir = new File(AbstractTest.tmpDir, "src-div");
		this.srcDir.mkdir();

		this.destDir = new File(AbstractTest.tmpDir, "dest-dir");

		File file1 = new File(this.srcDir, "file1");
		file1.createNewFile();
		FileUtils.write(file1, "123456");

		File file2 = new File(this.srcDir, "file2.log");
		file2.createNewFile();
		FileUtils.write(file2, "afsdfasfas");

		File subSrcDir = new File(this.srcDir, "sub");
		subSrcDir.mkdir();

		File file3 = new File(subSrcDir, "file3.log");
		file3.createNewFile();
		FileUtils.write(file3, "afsdf2342342");

		File file4 = new File(subSrcDir, "file4");
		file4.createNewFile();
		FileUtils.write(file4, "afsdfasfas42424");

		this.srcDirBackup = new File(FileUtils.getTempDirectoryPath(), "backup-src-div");
		FileHelper.copyDirectory(this.srcDir, this.srcDirBackup, null);

		this.srcFile = new File(AbstractTest.tmpDir, "srcfile");
		this.srcFile.createNewFile();
		FileUtils.write(this.srcFile, "123456");

		this.destFile = new File(AbstractTest.tmpDir, "destfile");

		this.srcFileBackup = new File(AbstractTest.tmpDir, "backup-srcfile");

		FileHelper.copyFile(this.srcFile, this.srcFileBackup);
	}

	@Test
	public void testDir() throws Exception {
		String[] excludes = new String[] { "\\Q.log\\E" };
		MoveOperation operation = new MoveOperation();
		operation.setSrc(this.srcDir.getAbsolutePath());
		operation.setDest(this.destDir.getAbsolutePath());
		operation.setExcludes(excludes);
		operation.execute();
		Assert.assertFalse(this.srcDir.exists());
		Assert.assertTrue(FileHelper.equalsDir(this.srcDirBackup, this.destDir, excludes));
		operation.rollback();
		Assert.assertTrue(this.srcDir.exists());
		Assert.assertTrue(FileHelper.equalsDir(this.srcDirBackup, this.srcDir, excludes));
	}

	@Test
	public void testFile() throws Exception {
		MoveOperation operation = new MoveOperation();
		operation.setSrc(this.srcFile.getAbsolutePath());
		operation.setDest(this.destFile.getAbsolutePath());
		operation.execute();
		Assert.assertFalse(this.srcFile.exists());
		Assert.assertTrue(FileHelper.equalsFile(this.srcFileBackup, this.destFile));
		operation.rollback();
		Assert.assertTrue(this.srcFile.exists());
		Assert.assertTrue(FileHelper.equalsFile(this.srcFileBackup, this.srcFile));
	}

}
