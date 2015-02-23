package com.ffcs.ads.operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 命令执行器.
 *
 * @author dylan.chen Mar 10, 2011
 *
 */
public class CommandExecuter {

	private static Logger logger=LoggerFactory.getLogger(CommandExecuter.class);

	public enum Result {

		SUCCESS, FAILURE;

		static Result resultOf(int resultCode) {
			if (resultCode == 0) {
				return SUCCESS;
			} else {
				return FAILURE;
			}
		}
	}

	private File workDir;

	private String[] cmds;

	private String output;

	private Result result;

	public CommandExecuter(String[] cmds, File workDir) {
		super();
		this.workDir = workDir;
		this.cmds = cmds;
	}

	public void execute() throws Exception {
		Runtime run = Runtime.getRuntime();
		Process proc = null;
		try {
			String[] _cmds = getCmds();
			logger.debug("cmds:{}",Arrays.toString(_cmds));
			proc = run.exec(_cmds, null, this.workDir);
			this.output = extractOutput(proc);
			logger.debug("output:\r\n{}",this.output);
			proc.waitFor();
			int resultCode = proc.exitValue();
			this.result = Result.resultOf(resultCode);
			logger.debug("result:{}[{}]",this.result,resultCode);
		} finally {
			if (proc != null) {
				proc.destroy();
			}
		}
	}

	private String[] getCmds() {
		/**
		 * 如果sh中含有awk,一定要按new String[]{"/bin/sh","-c",shStr}写,才可以获得流;
		 * 对于不包含awk的脚本,使用此参数可能导致无法执行此脚本。
		 */
		ArrayList<String> cmdList = new ArrayList<String>();
		cmdList.add("/bin/sh");
		cmdList.add("-c");
		cmdList.addAll(Arrays.asList(this.cmds));
		return cmdList.toArray(new String[0]);
	}

	private String extractOutput(Process proc) throws Exception {
		BufferedReader input = null;
		StringBuilder result = new StringBuilder();
		try {
			input = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			String lineStr;
			while ((lineStr = input.readLine()) != null) {
				result.append(lineStr);
				result.append("\r\n");
			}
		} finally {
			IOUtils.closeQuietly(input);
		}
		return result.toString();
	}

	public String getOutput() {
		return this.output;
	}

	public Result getResult() {
		return this.result;
	}

}
