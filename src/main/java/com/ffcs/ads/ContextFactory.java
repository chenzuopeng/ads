package com.ffcs.ads;

import org.apache.commons.io.FilenameUtils;

import com.ffcs.ads.utils.DateHelper;
import com.ffcs.ads.utils.FileHelper;

/**
 * 
 * 上下文工厂类.
 * 
 * @author dylan.chen Mar 28, 2011
 * 
 */
public final class ContextFactory {

	private static final String DIR_NAME_WORKDIR = "workdir";

	private static final String DIR_NAME_PATCHS = "patchs";

	private static final String DIR_NAME_BACKUPS = "backups";

	private static Config config = Config.getInstance();
	
	private static AdsContext currentContext;
	
	public synchronized static void init(boolean isRollback,String targetDir){
		if(currentContext==null){
			currentContext=isRollback?initContextForRollback(targetDir):initContextForDeploy(targetDir);
		}
	}
	
	private static AdsContext initContextForDeploy(String targetDir){
		
		AdsContext context=new AdsContext();
		
		/**
		 * 获取目标目录
		 */
		context.setTargetDir(FilenameUtils.concat(config.getWorkspaceHome(), targetDir));

		/**
		 * 获取补丁目录
		 */
		context.setPatchsDir(FilenameUtils.concat(context.getTargetDir(), DIR_NAME_PATCHS));

		/**
		 * 初始化工作目录
		 */
		String currentTimeStamp = DateHelper.getCurrentTimeStamp("yyyyMMddHHmmss");
		context.setWorkDir(FilenameUtils.concat(FilenameUtils.concat(context.getTargetDir(), DIR_NAME_WORKDIR),currentTimeStamp));
		try {
			FileHelper.createFile(context.getWorkDir());
		} catch (Exception e) {
			throw new RuntimeException("初始化工作目录失败", e);
		}

		/**
		 * 初始化备份目录
		 */
		context.setBackupsDir(FilenameUtils.concat(context.getWorkDir(), DIR_NAME_BACKUPS));
		try {
			FileHelper.createFile(context.getBackupsDir());
		} catch (Exception e) {
			throw new RuntimeException("初始化备份目录失败", e);
		}
		
		return context;
	}
	
	private static AdsContext initContextForRollback(String targetDir){
		AdsContext context=new AdsContext();
		context.setTargetDir(targetDir.substring(0,targetDir.lastIndexOf(DIR_NAME_WORKDIR)));
		context.setWorkDir(targetDir);
		context.setBackupsDir(FilenameUtils.concat(context.getWorkDir(), DIR_NAME_BACKUPS));
		return context;
	}

	public synchronized static AdsContext getContext() {
		if(currentContext==null){
			throw new RuntimeException("使用init方法,初始化上下文工厂.");
		}
        return currentContext;
	}

}
