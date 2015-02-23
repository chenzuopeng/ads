package com.ffcs.ads;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Stack;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.ffcs.ads.operations.Operation;
import com.thoughtworks.xstream.XStream;

/**
 * 
 * 
 * @author dylan.chen Mar 18, 2011
 * 
 */
public class ExecutedOperationsPersister {

	private XStream xstream=new XStream();

	public void persist(Stack<Operation> operations) throws Exception {
		if (operations.isEmpty()) {
			return;
		}
		File rollbackFile = getRollbackFile();
		if (!rollbackFile.exists()) {
			rollbackFile.createNewFile();
		}
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(rollbackFile);
			this.xstream.toXML(operations, out);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	@SuppressWarnings("unchecked")
	public Stack<Operation> unpersist() throws Exception {
		InputStream input = null;
		try {
			input = new FileInputStream(getRollbackFile());
			return (Stack<Operation>) this.xstream.fromXML(input);
		} finally {
			IOUtils.closeQuietly(input);
		}
	}

	private File getRollbackFile() {
		String rollbackFilePath = FilenameUtils.concat(ContextFactory.getContext().getWorkDir(), "rollback");
		return new File(rollbackFilePath);
	}
}
