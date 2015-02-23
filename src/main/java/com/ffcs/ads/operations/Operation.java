package com.ffcs.ads.operations;

import java.io.Serializable;

/**
 *
 * 操作,表示部署过程中的一个动作.
 * 
 * @author dylan.chen Mar 10, 2011
 *
 */
public interface Operation extends Serializable{

	 /**
	  * 执行操作.
	  * @throws Exception
	  */
	 public void execute() throws Exception;

	 /**
	  * 执行回退.
	  * @throws Exception
	  */
	 public void rollback() throws Exception;
	 
	 /**
	  * 是否需要回退.
	  * @return true 需要;false 不需要
	  */
	 public boolean needToRollback();

	 /**
	  * 获取消息.
	  * @return 消息
	  */
	 public String getMessage();
}
