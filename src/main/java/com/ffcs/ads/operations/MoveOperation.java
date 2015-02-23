package com.ffcs.ads.operations;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 移动文件或目录的操作.
 * 
 * @author dylan.chen Mar 10, 2011
 * 
 */
public class MoveOperation extends CopyOperation {

	private static final long serialVersionUID = 5999623897522095068L;

	private static Logger logger = LoggerFactory.getLogger(MoveOperation.class);

	@Override
	public void execute() throws Exception {
		logger.info("执行 - {}", this);
		super.execute();
		FileUtils.forceDelete(new File(this.getSrc()));
	}

	@Override
	public void rollback() throws Exception {
		logger.info("回退 - {}", this);
		String originalSrc = this.getSrc();
		super.setSrc(this.getDest());
		super.setDest(originalSrc);
		super.setExcludes(null);
		super.execute();
	}

	@Override
	public String getMessage() {
		return String.format("移动 %s 到 %s", getSrc(), getDest());
	}

}
