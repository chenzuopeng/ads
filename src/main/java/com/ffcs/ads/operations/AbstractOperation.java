package com.ffcs.ads.operations;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 *
 * 动作的基类.
 * 
 * @author dylan.chen Mar 18, 2011
 * 
 */
public abstract class AbstractOperation implements Operation {

	private static final long serialVersionUID = -9052627857450035783L;
	
	private boolean neededToRollback=false;
	
	public boolean needToRollback() {
		return neededToRollback;
	}

	public void setNeedToRollback(boolean needToRollback) {
		this.neededToRollback = needToRollback;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
