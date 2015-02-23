package com.ffcs.ads;

import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ffcs.ads.operations.Assert;
import com.ffcs.ads.operations.Operation;

/**
 * 
 * 执行部署操作.
 * 
 * @author dylan.chen Mar 12, 2011
 * 
 */
public class DeployExecuter {

	private static Logger logger = LoggerFactory.getLogger(DeployExecuter.class);

	private ExecutedOperationsPersister executedOperationsPersister = new ExecutedOperationsPersister();

	public boolean deploy(Operation[] operations) {
		logger.info("开始部署");
		Stack<Operation> successfuls = new Stack<Operation>();
		try {
			for (Operation operation : operations) { 
				successfuls.push(operation);
				operation.execute();
				LogHelper.outputToDeployLogger(operation.getMessage());
			}
			logger.info("部署完成");
			return true;
		} catch (Exception e) {
			logger.error("执行部署失败", e);
		} finally {
			try {
				this.executedOperationsPersister.persist(successfuls);
			} catch (Exception e2) {
				logger.error(e2.getMessage(), e2);
			}
		}
		return rollback(successfuls);
	}

	public boolean rollback(Stack<Operation> operations) {
		logger.info("开始回退");
		if(operations.peek() instanceof Assert){
			/**
			 * 如果当前栈顶元素是一个断言,那么表明当前回退是由此断言引起的,因此无需在回退时执行此断言,将此断言从栈顶移除
			 */
			operations.pop();
		}
		while (!operations.isEmpty()) {
			Operation operation = operations.pop();
			if(!operation.needToRollback()){
				continue;
			}
			try {
				operation.rollback();
			} catch (Exception e) {
				logger.error("执行回退失败", e);
				return false;
			}
		}
		logger.info("回退完成");
		return true;
	}

    public boolean rollback() {
    	try {
    		return rollback(executedOperationsPersister.unpersist());
		}  catch (Exception e){
			logger.error("执行回退失败", e);
			return false;
		}
    }
}
