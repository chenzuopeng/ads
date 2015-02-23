package com.ffcs.ads.operations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 此类表示一个原子的动作.
 * 
 * @author dylan.chen Mar 26, 2011
 * 
 */
public abstract class AtomicOperation extends AbstractOperation {

	private static final long serialVersionUID = -2118810556275100918L;
	
	private static Logger logger = LoggerFactory.getLogger(AtomicOperation.class);

	protected abstract void executeInternal() throws Exception;
	
	public void backup() throws Exception {
		//子类覆盖此方法提供备份逻辑
	}
	
	protected abstract boolean validateExecute() throws Exception ;
	
	public void execute() throws Exception {
		logger.info("执行 - {}", this);
		backup();
		executeInternal();
		setNeedToRollback(true);
		if(!validateExecute()){
			throw new Exception("校验执行失败");
		}
	}
	
	protected boolean validateRollback() throws Exception {
		//子类覆盖此方法提供具体的回退验证逻辑
		return true;
	}

	public void rollback() throws Exception {
		logger.info("回退 - {}", this);
		rollbackInternal();
		if(!validateRollback()){
			throw new Exception("校验回退失败");
		}
	}

	protected abstract void rollbackInternal() throws Exception;

}
