package com.ffcs.ads.operations;

import java.io.File;

import org.apache.commons.lang.StringUtils;

import com.ffcs.ads.operations.CommandExecuter.Result;

/**
 * 
 * 执行指定命令.
 * 
 *    注:此动作在校验执行或回退时,总是默认成功,子类需要覆盖相应验证方法提供具体的验证逻辑.
 * 
 * @author dylan.chen Mar 10, 2011
 * 
 */
public class ExecOperation extends AtomicOperation {

	private static final long serialVersionUID = -5280168314353939433L;

	private String workDir;

	private String cmd;

	private String rollback;

	@Override
	public void executeInternal() throws Exception {
		if (Result.SUCCESS != executeCommand(this.cmd)) {
			throw new Exception("执行失败");
		}
	}

	@Override
	public boolean validateExecute() throws Exception {
		return true;
	}

	@Override
	public void rollbackInternal() throws Exception {
		if (StringUtils.isNotEmpty(this.rollback)) {
			if (Result.SUCCESS != executeCommand(this.rollback)) {
				throw new Exception("回退失败");
			}
		}
	}

	private Result executeCommand(String cmd) throws Exception {
		CommandExecuter commandExecuter = new CommandExecuter(new String[] { cmd }, getFile(this.workDir));
		commandExecuter.execute();
		return commandExecuter.getResult();
	}

	private File getFile(String filePath) {
		return StringUtils.isNotBlank(filePath) ? new File(this.workDir) : null;
	}

	public String getMessage() {
		return String.format("执行 %s", this.getCmd());
	}

	public String getWorkDir() {
		return this.workDir;
	}

	public void setWorkDir(String workDir) {
		this.workDir = workDir;
	}

	public String getCmd() {
		return this.cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getRollback() {
		return this.rollback;
	}

	public void setRollback(String rollback) {
		this.rollback = rollback;
	}

}
