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
public class CopyOperationTest extends AbstractTest {

	File srcDir;

	File destDir;

	File srcDirBackup;

	File srcFile;

	File destFile;

	File destFileBackup;

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

		this.srcDirBackup = new File(AbstractTest.tmpDir, "backup-src-div");
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
		CopyOperation operation = new CopyOperation();
		operation.setSrc(this.srcDir.getAbsolutePath());
		operation.setDest(this.destDir.getAbsolutePath());
		operation.setExcludes(excludes);
		operation.execute();
		Assert.assertTrue(FileHelper.equalsDir(this.srcDir, this.destDir, excludes));
		operation.rollback();
		Assert.assertFalse(this.destDir.exists());
	}

	@Test
	public void testDir1() throws Exception {
		this.destDir.mkdirs();
		File file1 = new File(this.destDir, "file1");
		file1.createNewFile();

		File subSrcDir = new File(this.destDir, "sub");
		subSrcDir.mkdir();

		File file3 = new File(subSrcDir, "file3.log");
		file3.createNewFile();
		FileUtils.write(file3, "1");

		File file4 = new File(subSrcDir, "file4");
		file4.createNewFile();
		FileUtils.write(file4, "2");

		File destFileBackup = new File(AbstractTest.tmpDir, "destDirBackup");
		FileHelper.copyDirectory(this.destDir, destFileBackup, null);

		String[] excludes = new String[] { "\\Q.log\\E" };
		CopyOperation operation = new CopyOperation();
		operation.setSrc(this.srcDir.getAbsolutePath());
		operation.setDest(this.destDir.getAbsolutePath());
		operation.setExcludes(excludes);
		operation.execute();
		Assert.assertTrue(FileHelper.equalsDir(this.srcDir, this.destDir, excludes));
		operation.rollback();
		Assert.assertTrue(FileHelper.equalsDir(this.destDir, destFileBackup, (String[]) null));
	}

	@Test
	public void testDir2() throws Exception {
		this.destDir.mkdirs();
		File file1 = new File(this.destDir, "file1");
		file1.createNewFile();

		File destFileBackup = new File(AbstractTest.tmpDir, "destDirBackup");
		FileHelper.copyDirectory(this.destDir, destFileBackup, null);

		String[] excludes = new String[] { "\\Q.log\\E" };
		CopyOperation operation = new CopyOperation();
		operation.setSrc(this.srcDir.getAbsolutePath());
		operation.setDest(this.destDir.getAbsolutePath());
		operation.setExcludes(excludes);
		operation.execute();
		Assert.assertTrue(FileHelper.equalsDir(this.srcDir, this.destDir, excludes));
		operation.rollback();
		Assert.assertTrue(FileHelper.equalsDir(this.destDir, destFileBackup, (String[]) null));
	}

	@Test
	public void testFile() throws Exception {
		CopyOperation operation = new CopyOperation();
		operation.setSrc(this.srcFile.getAbsolutePath());
		operation.setDest(this.destFile.getAbsolutePath());
		operation.execute();
		Assert.assertTrue(FileHelper.equalsFile(this.destFile, this.srcFile));
		operation.rollback();
		Assert.assertFalse(this.destFile.exists());
	}

	@Test
	public void testFile1() throws Exception {
		this.destFile.createNewFile();
		File destFileBackup = new File(AbstractTest.tmpDir, "destFileBackup");
		FileHelper.copyFile(this.destFile, destFileBackup);
		CopyOperation operation = new CopyOperation();
		operation.setSrc(this.srcFile.getAbsolutePath());
		operation.setDest(this.destFile.getAbsolutePath());
		operation.execute();
		Assert.assertTrue(FileHelper.equalsFile(this.destFile, this.srcFile));
		operation.rollback();
		Assert.assertTrue(FileHelper.equalsFile(this.destFile, destFileBackup));
	}

}
