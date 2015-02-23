package com.ffcs.ads.operations;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 断言,表示一个用于执行验证的操作.
 *
 * @author dylan.chen Mar 12, 2011
 * 
 */
public abstract class Assert implements Operation{
	
	private static final long serialVersionUID = 7348267496339893250L;

	private static Logger logger = LoggerFactory.getLogger(Assert.class);
	
	private boolean expected;

	public void execute() throws Exception {
		 logger.info("执行 - {}", this);
		 boolean actual=doAssert();
		 logger.info("期望:{},实际:{}", expected, actual);
         if(expected!=actual){
        	 throw new Exception();
         }	
	}
	
	public boolean needToRollback() {
		return true;
	}

	protected abstract boolean doAssert() throws Exception;

	public void rollback() throws Exception {
		this.execute();
	}

	public String getMessage() {
		return null;
	}
	
	public boolean isExpected() {
		return expected;
	}

	public void setExpected(boolean expected) {
		this.expected = expected;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
