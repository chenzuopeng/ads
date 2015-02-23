package com.ffcs.ads;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ffcs.ads.Main.RuntimeArgs;

/**
 * 
 * 
 * @author dylan.chen Mar 12, 2011
 * 
 */
public class Ads {

	private static Logger logger = LoggerFactory.getLogger(Ads.class);

	private DeployFileParser deployFileParser = new DeployFileParser();

	private DeployExecuter deployExecuter = new DeployExecuter();

	private RuntimeArgs ags;
	
	public Ads(RuntimeArgs ags) {
		this.ags = ags;
	}

	public void execute() {
        if (ags.isRollback()) {
        	rollback();
		}else{
			deploy();
		}
	}
	
	private void deploy(){
		//获取部署文件
		File deployFile=null;
		if(ags.getDeployFilePath()==null){
			deployFile=new File("deploy.xml");
		}else{
			deployFile=new File(this.ags.getDeployFilePath());
		}
		
		//解析部署文件
		Deploy deploy = null;
		try {
			deploy = this.deployFileParser.parse(deployFile);
		} catch (Exception e) {
			logger.error("解析部署文件失败", e);
			return;
		}
		
		//获取目标目录
		String targetDir=deploy.getBaseDir();
		if(StringUtils.isBlank(targetDir)){
			targetDir=FilenameUtils.getFullPathNoEndSeparator(deployFile.getAbsolutePath());
		}
		
		// 初始化全局上下文
		ContextFactory.init(false, targetDir);
		// 初始化日志模块
		LogHelper.initializeFileLogger();
		
		this.deployExecuter.deploy(deploy.getOperations());
	}
	
	private void rollback(){
		//获取目标目录
		String targetDir=ags.getRollbackFilePath();
		if(StringUtils.isBlank(targetDir)){
			targetDir=System.getProperty("user.dir");
		}
		
		// 初始化全局上下文
		ContextFactory.init(true, targetDir);
		// 初始化日志模块
		LogHelper.initializeFileLogger();
		
		this.deployExecuter.rollback();
	}

}
