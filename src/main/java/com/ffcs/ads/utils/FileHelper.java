package com.ffcs.ads.utils;

import java.io.File;
import java.io.FileFilter;

import org.apache.commons.io.FileUtils;

/**
 * 
 * 提供文件操作的工具类.
 * 
 * @author dylan.chen Mar 10, 2011
 * 
 */
public class FileHelper {

	private FileHelper() {
	}

	/**
	 * 比较2个目录是否相同.
	 * 
	 * @param dir1
	 *            目录1
	 * @param dir2
	 *            目录2
	 * @param excludes
	 *            排错列表
	 * @return true 相同;false 不同
	 * @throws Exception
	 */
	public static boolean equalsDir(File dir1, File dir2, String[] excludes) throws Exception {
		return equalsDir(dir1, dir2, new ExcludesFileFilter(excludes));
	}

	/**
	 * 比较2个目录是否相同.
	 * 
	 * @param dir1
	 *            目录1
	 * @param dir2
	 *            目录2
	 * @param excludesFileFilter
	 *            用于排除的过滤器
	 * @return true 相同;false 不同
	 * @throws Exception
	 */
	public static boolean equalsDir(File dir1, File dir2, FileFilter excludesFileFilter) throws Exception {
		if (!dir2.isDirectory() || !dir1.isDirectory()) {
			return false;
		}
		File[] filesOfDir1 = dir1.listFiles(excludesFileFilter);
		for (File fileAtDir1 : filesOfDir1) {
			File fileAtDir2 = new File(dir2, fileAtDir1.getName());
			if (fileAtDir1.isFile()) {
				if (!fileAtDir2.exists() || !equalsFile(fileAtDir1, fileAtDir2)) {
					return false;
				}
			} else {
				equalsDir(fileAtDir1, fileAtDir2, excludesFileFilter);
			}
		}
		return true;
	}

	/**
	 * 比较2个文件是否相同.
	 * 
	 * @param file1
	 * @param file2
	 * @return true 相同;false 不同
	 * @throws Exception
	 */
	public static boolean equalsFile(File file1, File file2) throws Exception {
		long sumCRC1 = FileUtils.checksumCRC32(file1);
		long sumCRC2 = FileUtils.checksumCRC32(file2);
		return sumCRC1 == sumCRC2;
	}

	/**
	 * 复制目录到指定的位置.
	 * 
	 * @param srcDir
	 *            源目录
	 * @param destDir
	 *            目标路径
	 * @param excludes
	 *            排错列表
	 * @throws Exception
	 */
	public static void copyDirectory(File srcDir, File destDir, String[] excludes) throws Exception {
		FileUtils.copyDirectory(srcDir, destDir, new ExcludesFileFilter(excludes));
	}

	/**
	 * 复制文件到指定的位置.
	 * 
	 * @param srcFile
	 *            源文件
	 * @param destFile
	 *            目标文件
	 * @throws Exception
	 */
	public static void copyFile(File srcFile, File destFile) throws Exception {
		FileUtils.copyFile(srcFile, destFile);
	}

	/**
	 * 生成文件或目录.当生成一个目录时,会生成所有不存在的父目录.
	 * 
	 * @param filePath
	 *            文件路径
	 * @throws Exception
	 */
	public static void createFile(String filePath) throws Exception {
		File fileToCreate = new File(filePath);
		if (fileToCreate.exists()) {
			return;
		}
		if (fileToCreate.isFile()) {
			fileToCreate.createNewFile();
		} else {
			fileToCreate.mkdirs();
		}
	}

	/**
	 * 判断指定文件或目录是否存在.
	 * 
	 * @param filePath
	 *            文件路径
	 * @return true 存在;false 不存在
	 */
	public static boolean existsFile(String filePath) {
		return new File(filePath).exists();
	}
}
