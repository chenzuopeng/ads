package com.ffcs.ads.operations;

import com.ffcs.ads.utils.CommonHelper;

/**
 * 
 * 解除挂载操作.
 * 
 * @author dylan.chen Mar 11, 2011
 * 
 */
public class UMountOperation extends ExecOperation {

	private static final long serialVersionUID = -8352312029579998628L;

	private String src;

	private String dest;

	@Override
	public void executeInternal() throws Exception {
		String cmd = String.format("umount %s", this.dest);
		super.setCmd(cmd);
		super.executeInternal();
	}

	@Override
	public boolean validateExecute() throws Exception {
		return !CommonHelper.hasMounted(this.dest);
	}

	@Override
	public void rollbackInternal() throws Exception {
		MountOperation operation = new MountOperation();
		operation.setSrc(this.src);
		operation.setDest(this.dest);
		operation.execute();
	}

	@Override
	public String getMessage() {
		return String.format("卸载 %s 与 %s 之间的绑定", this.getSrc(), this.getDest());
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
