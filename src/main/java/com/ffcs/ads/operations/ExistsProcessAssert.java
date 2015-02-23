package com.ffcs.ads.operations;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ffcs.ads.utils.CommonHelper;

/**
 * 
 * 进程是否存在.
 * 
 * @author dylan.chen Mar 13, 2011
 * 
 */
public class ExistsProcessAssert extends Assert {

	private static final long serialVersionUID = 5109598008308718394L;

	private String processFlag;

	@Override
	protected boolean doAssert() throws Exception {
		return CommonHelper.existsProcess(this.processFlag);
	}

	public String getProcessFlag() {
		return this.processFlag;
	}

	public void setProcessFlag(String processFlag) {
		this.processFlag = processFlag;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
