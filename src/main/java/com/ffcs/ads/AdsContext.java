package com.ffcs.ads;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * 部署上下文.
 * 
 * @author dylan.chen Mar 28, 2011
 * 
 */
public class AdsContext {
	
	private String targetDir;

	private String workDir;

	private String backupsDir;

	private String patchsDir;

	public String getTargetDir() {
		return targetDir;
	}

	void setTargetDir(String targetDir) {
		this.targetDir = targetDir;
	}

	public String getWorkDir() {
		return workDir;
	}

	void setWorkDir(String workDir) {
		this.workDir = workDir;
	}

	public String getBackupsDir() {
		return backupsDir;
	}

	void setBackupsDir(String backupsDir) {
		this.backupsDir = backupsDir;
	}

	public String getPatchsDir() {
		return patchsDir;
	}

	void setPatchsDir(String patchsDir) {
		this.patchsDir = patchsDir;
	}
    
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
