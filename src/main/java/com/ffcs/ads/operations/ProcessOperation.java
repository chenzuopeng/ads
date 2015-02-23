package com.ffcs.ads.operations;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ffcs.ads.utils.CommonHelper;

/**
 * 
 * 进程开启或关闭操作. 当前只支持shell类型为sh的系统.
 * 
 * @author dylan.chen Mar 10, 2011
 * 
 */
public class ProcessOperation extends ExecOperation {

	private static final long serialVersionUID = -2768558472366749688L;

	private static Logger logger = LoggerFactory.getLogger(ProcessOperation.class);

	private String startupCommand;

	private String shutdownCommand;

	private boolean startup = true;

	private String processFlag;

	/**
	 * shell类型
	 */
	private String shell;

	/**
	 * 等待时间,单位：秒,默认为10秒 注：用于命令执行延迟,为了校验那些需要一定时间执行成功才能看到执行效果的命令.
	 */
	private long waitFor = 10;
	
	@Override
	public void executeInternal() throws Exception {
		super.setCmd(getCommand(this.startup?this.startupCommand:this.shutdownCommand));
		super.executeInternal();
		sleep();
	}

	@Override
	public boolean validateExecute() throws Exception {
		if(this.startup){
			return CommonHelper.existsProcess(this.processFlag);
		}else{
			return !CommonHelper.existsProcess(this.processFlag);
		}
	}
	
	@Override
	public void rollbackInternal() throws Exception {
		super.setRollback(getCommand(this.startup?this.shutdownCommand:this.startupCommand));
		super.rollbackInternal();
		sleep();
	}

	@Override
	protected boolean validateRollback() throws Exception {
		if(this.startup){
			return !CommonHelper.existsProcess(this.processFlag);
		}else{
			return CommonHelper.existsProcess(this.processFlag);
		}
	}
	
	
	private String getCommand(String input) throws Exception {
		if (StringUtils.isBlank(this.shell) || "sh".equals(this.shell.toLowerCase())) {
			return "sh " + input;
		}
		throw new Exception("不支持的shell类型");
	}


	private void sleep() throws Exception {
		long sleep = this.waitFor * 1000;
		logger.debug("执行延迟:{}", sleep);
		Thread.sleep(sleep);
	}

	@Override
	public String getMessage() {
		if (this.startup) {
			return String.format("通过命令 %s 启动进程 %s", this.getStartupCommand(), this.getProcessFlag());
		} else {
			return String.format("通过命令 %s 关闭进程 %s", this.getShutdownCommand(), this.getProcessFlag());
		}
	}

	public String getProcessFlag() {
		return this.processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}

	public String getStartupCommand() {
		return this.startupCommand;
	}

	public void setStartupCommand(String startupCommand) {
		this.startupCommand = startupCommand;
	}

	public String getShutdownCommand() {
		return this.shutdownCommand;
	}

	public void setShutdownCommand(String shutdownCommand) {
		this.shutdownCommand = shutdownCommand;
	}

	public boolean isStartup() {
		return this.startup;
	}

	public void setStartup(boolean startup) {
		this.startup = startup;
	}

	public long getWaitFor() {
		return this.waitFor;
	}

	public void setWaitFor(long waitFor) {
		this.waitFor = waitFor;
	}

	public String getShell() {
		return this.shell;
	}

	public void setShell(String shell) {
		this.shell = shell;
	}

}
