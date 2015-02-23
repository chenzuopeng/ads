package com.ffcs.ads.operations;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ffcs.ads.ContextFactory;
import com.ffcs.ads.utils.DateHelper;
import com.ffcs.ads.utils.ExcludesFileFilter;
import com.ffcs.ads.utils.FileHelper;

/**
 * 
 * 拷贝文件或目录的操作.每次使用最好重新生成一个此类的实例对象.
 * 
 * @author dylan.chen Mar 10, 2011
 * 
 */
public class CopyOperation extends AbstractOperation {

	private static final long serialVersionUID = -3878803428351376251L;

	private static Logger logger = LoggerFactory.getLogger(CopyOperation.class);

	private String backupDirPath;

	private String src;

	private String dest;

	private String[] excludes;

	/**
	 * 是否备份目标
	 */
	private boolean isBackup = true;

	/**
	 * 复制时,新增到目标的文件或目录的列表
	 */
	private List<String> addFiles = new ArrayList<String>();

	/**
	 * 复制时,被覆盖掉的目标文件列表
	 */
	private List<String> overwritedFiles = new ArrayList<String>();

	public void execute() throws Exception {
		logger.info("执行 - {}", this);
		File srcFile = new File(getSrc());
		File destFile = new File(getDest());
		this.backupDirPath = getBackupDirPath();
		setNeedToRollback(true);
		if (srcFile.isFile()) {
			doFile(srcFile, destFile);
		} else {
			doDirectory(srcFile, destFile);
		}
	}

	public String getBackupDirPath() {
		String id = String.format("%s-%s", DateHelper.getCurrentTimeStamp("yyyyMMddHHmmss"),
				RandomStringUtils.randomNumeric(2));
		return FilenameUtils.concat("copy_backups", id);
	}

	private File getBackupFile(String destFilePath) {
		return new File(ContextFactory.getContext().getBackupsDir(), this.backupDirPath + destFilePath);
	}

	private void backupFile(File destFile) throws Exception {
		if (destFile.exists()) {
			String destFilePath = destFile.getAbsolutePath();
			this.overwritedFiles.add(destFilePath);
			File backupFile = getBackupFile(destFilePath);
			copyFile(destFile, backupFile);
		} else {
			this.addFiles.add(destFile.getAbsolutePath());
		}
	}

	private void backupDirectory(File destDir) throws Exception {
		if (!destDir.exists()) {
			this.addFiles.add(destDir.getAbsolutePath());
		}
	}

	private void copyFile(File srcFile, File destFile) throws Exception {
		FileHelper.copyFile(srcFile, destFile);
		if (!FileHelper.equalsFile(srcFile, destFile)) {
			throw new Exception(String.format("复制文件[%s]到[%s]失败", srcFile, destFile));
		}
	}

	private void doFile(File srcFile, File destFile) throws Exception {
		if (this.isBackup) {
			backupFile(destFile);
		}
		copyFile(srcFile, destFile);
	}

	private void doDirectory(File srcDir, File destDir) throws Exception {
		if (this.isBackup) {
			backupDirectory(destDir);
		}
		File[] fileOfSrcDirs = srcDir.listFiles(((FileFilter) new ExcludesFileFilter(this.excludes)));
		
		if(ArrayUtils.isEmpty(fileOfSrcDirs)){
			return;
		}
		
		for (File fileOfSrcDir : fileOfSrcDirs) {
			File fileOfDestDir = new File(destDir, fileOfSrcDir.getName());
			if (fileOfSrcDir.isFile()) {
				doFile(fileOfSrcDir, fileOfDestDir);
			} else {
				doDirectory(fileOfSrcDir, fileOfDestDir);
			}
		}
		if (!FileHelper.equalsDir(srcDir, destDir, this.excludes)) {
			throw new Exception(String.format("复制目录[%s]到[%s]失败", srcDir, destDir));
		}
	}

	public void rollback() throws Exception {
		logger.info("回退 - {}", this);
		if (!this.isBackup) {
			return;
		}
		// 还原覆盖掉的文件
		for (String overwritedFile : this.overwritedFiles) {
			copyFile(getBackupFile(overwritedFile), new File(overwritedFile));
		}
		// 删除新增的文件
		for (String addFile : this.addFiles) {
			File file = new File(addFile);
			if (file.exists()) {
				FileUtils.forceDelete(file);
			}
		}
	}

	public String getMessage() {
		return String.format("复制 %s 到 %s", getSrc(), getDest());
	}

	/**
	 * 当提供的是相对路径时,默认为相对于当前工作目录下的补丁目录.
	 */
	public String getSrc() {
		return FilenameUtils.concat(ContextFactory.getContext().getPatchsDir(), this.src);
		// return this.src;
	}

	/**
	 * 设置源.
	 * 
	 * @param src
	 *            源
	 */
	public void setSrc(String src) {
		this.src = src;
	}

	/**
	 * 由于xtream为对象赋值是使用是反射,如果要想赋值后对属性进行相关处理(常规放在setter方法中),只能放在getter方法中处理,
	 * 然后类中用到此属性的地方,不能直接访问属性,而是通过属性的getter方法方法访问.
	 */
	public String getDest() {
		return FilenameUtils.concat(ContextFactory.getContext().getBackupsDir(), this.dest);
		// return this.dest;
	}

	/**
	 * 设置目标
	 * 
	 * @param dest
	 *            目标
	 */
	public void setDest(String dest) {
		this.dest = dest;
	}

	public String[] getExcludes() {
		return this.excludes;
	}

	public void setExcludes(String[] excludes) {
		this.excludes = excludes;
	}

	public boolean isBackup() {
		return this.isBackup;
	}

	public void setBackup(boolean isBackup) {
		this.isBackup = isBackup;
	}

}
