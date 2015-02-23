package com.ffcs.ads;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 入口类.
 * 
 * @author dylan.chen Mar 12, 2011
 * 
 */
public class Main {

	static {
		LogHelper.initializeConsoleLogger();
	}

	/**
	 * 运行时参数
	 */
	static class RuntimeArgs {
		
		private boolean isRollback=false;

		private String rollbackFilePath;

		private String deployFilePath;

		public String getDeployFilePath() {
			return this.deployFilePath;
		}

		public void setDeployFilePath(String deployFilePath) {
			this.deployFilePath = deployFilePath;
		}

		public String getRollbackFilePath() {
			return this.rollbackFilePath;
		}

		public void setRollbackFilePath(String rollbackFilePath) {
			this.rollbackFilePath = rollbackFilePath;
		}
		
		public boolean isRollback() {
			return isRollback;
		}

		public void setRollback(boolean isRollback) {
			this.isRollback = isRollback;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
		}
	}

	@SuppressWarnings("static-access")
	private static RuntimeArgs parseCommandLine(String[] args) {
		RuntimeArgs result = new RuntimeArgs();
		CommandLineParser parser = new GnuParser();
		OptionGroup optionGroup = new OptionGroup();
		optionGroup.addOption(OptionBuilder.withArgName("deploy").withLongOpt("deploy")
				.withDescription("制定部署文件的路径,包含文件名.当不指定此参数时,默认读取当前目录下,名为deploy.xml的文件.").hasArg().create("d"));
		optionGroup.addOption(OptionBuilder.withArgName("rollback").withLongOpt("rollback").withDescription("执行回退.可以以参数的方式指定工作目录路径;当不指定参数时,默认当前目录为工作目录.")
				.hasOptionalArg().create("r"));
		Options options = new Options();
		options.addOptionGroup(optionGroup);
		options.addOption(OptionBuilder.withArgName("help").withLongOpt("help").withDescription("输出帮助信息.").create("h"));
		CommandLine commandLine;
		try {
			commandLine = parser.parse(options, args);
			if (commandLine.hasOption('h')) {
				printUsage(options);
				System.exit(0);
			}
			if (commandLine.hasOption('d')) {
				result.setDeployFilePath(commandLine.getOptionValue('d'));
			}
			if (commandLine.hasOption('r')) {
				result.setRollback(true);
				result.setRollbackFilePath(commandLine.getOptionValue('r'));
			}
		} catch (ParseException e) {
			printUsage(options);
			System.exit(1);
		}
		return result;
	}

	private static void printUsage(Options options) {
		HelpFormatter helpFormatter = new HelpFormatter();
		helpFormatter.printHelp("ads [-h] [-d <deploy>]|[-r <rollback>]", options);
	}

	public static void main(String[] args) {
		RuntimeArgs runtimeArgs = parseCommandLine(args);
		Ads ads = new Ads(runtimeArgs);
		ads.execute();
	}

}
