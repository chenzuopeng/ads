package com.ffcs.ads;

import java.util.ArrayList;
import java.util.Arrays;

import com.ffcs.ads.operations.Operation;

/**
 * 
 * 部署信息.
 * 
 * @author dylan.chen Mar 14, 2011
 * 
 */
public class Deploy extends ArrayList<Operation> {

	private static final long serialVersionUID = 5935183763238246223L;

	private String baseDir;

	public Operation[] getOperations() {
		return this.toArray(new Operation[0]);
	}

	public String getBaseDir() {
		return baseDir;
	}

	public void setBaseDir(String baseDir) {
		this.baseDir = baseDir;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Deploy [baseDir=");
		builder.append(this.baseDir);
		builder.append(", operations=");
		builder.append(Arrays.toString(toArray()));
		builder.append("]");
		return builder.toString();
	}

}
