package com.ffcs.ads.utils;

import org.apache.commons.lang.StringUtils;

import com.ffcs.ads.operations.CommandExecuter;
import com.ffcs.ads.operations.CommandExecuter.Result;

/**
 *
 * 提供一些关于系统进程信息的操作.
 * 
 * @author dylan.chen Mar 10, 2011
 *
 */
public class CommonHelper {

	private CommonHelper() {
	}

	/**
	 * 是否存在指定进程
	 * @param procFlag 进程表示
	 * @return true 存在,false 不存在
	 * @throws Exception
	 */
	public static boolean existsProcess(String procFlag) throws Exception {
		String cmd = String.format("ps -ef|grep %s|egrep -v grep", procFlag);
		CommandExecuter commandExecuter = new CommandExecuter(new String[] { cmd }, null);
		commandExecuter.execute();
		return Result.SUCCESS == commandExecuter.getResult() && StringUtils.isNotBlank(commandExecuter.getOutput());
	}

	public static boolean hasMounted(String input) throws Exception{
		String cmd=String.format("mount|grep %s",input);
		CommandExecuter commandExecuter=new CommandExecuter(new String[] {cmd},null);
		commandExecuter.execute();
		return Result.SUCCESS == commandExecuter.getResult() && StringUtils.isNotBlank(commandExecuter.getOutput());
	}

}
