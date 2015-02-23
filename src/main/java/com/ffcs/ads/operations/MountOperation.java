package com.ffcs.ads.operations;

import java.io.File;

import com.ffcs.ads.utils.CommonHelper;

/**
 * 
 * 挂载操作.
 * 
 * @author dylan.chen Mar 11, 2011
 * 
 */
public class MountOperation extends ExecOperation {

	private static final long serialVersionUID = -488125049301339139L;

	private String src;

	private String dest;

	/**
	 * 目标目录是否新建的
	 */
	private boolean isCreateDest = false;

	@Override
	public void executeInternal() throws Exception {
		File destFile = new File(this.dest);
		if (!destFile.exists()) {
			destFile.mkdirs();
			this.isCreateDest = true;
		}
		String cmd = String.format("mount --bind %s %s", this.src, this.dest);
		super.setCmd(cmd);
		super.executeInternal();
	}

	@Override
	public boolean validateExecute() throws Exception {
		return CommonHelper.hasMounted(this.dest);
	}

	@Override
	public void rollbackInternal() throws Exception {
		UMountOperation operation = new UMountOperation();
		operation.setSrc(this.src);
		operation.setDest(this.dest);
		operation.execute();
		if (this.isCreateDest) {
			new File(this.dest).delete();
		}
	}

	@Override
	public String getMessage() {
		return String.format("挂载 %s 到 %s", this.getSrc(), this.getDest());
	}

	public String getSrc() {
		return this.src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public String getDest() {
		return this.dest;
	}

	public void setDest(String dest) {
		this.dest = dest;
	}

}
