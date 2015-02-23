package com.ffcs.ads;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ffcs.ads.operations.Assert;
import com.ffcs.ads.operations.AvailableHttpURLAssert;
import com.ffcs.ads.operations.CopyOperation;
import com.ffcs.ads.operations.ExecOperation;
import com.ffcs.ads.operations.ExistsProcessAssert;
import com.ffcs.ads.operations.MountOperation;
import com.ffcs.ads.operations.MoveOperation;
import com.ffcs.ads.operations.Operation;
import com.ffcs.ads.operations.ProcessOperation;
import com.ffcs.ads.operations.UMountOperation;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.CollectionConverter;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.mapper.Mapper;

/**
 * 
 * 部署文件解析器.
 * 
 * @author dylan.chen Mar 11, 2011
 * 
 */
public class DeployFileParser {

	private static Logger logger = LoggerFactory.getLogger(DeployFileParser.class);

	private static class DeployConverter extends CollectionConverter {

		public DeployConverter(Mapper mapper) {
			super(mapper);
		}

		@SuppressWarnings("rawtypes")
		@Override
		public boolean canConvert(Class type) {
			return Deploy.class.equals(type);
		}

		@Override
		public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
			Deploy deploy = (Deploy) context.currentObject();
			deploy.setBaseDir(reader.getAttribute("basedir"));
			populateCollection(reader, context, deploy);
			return deploy;
		}

	}

	private final static String FILE_CHARSET_NAME = "UTF-8";

	XStream xstream;

	public DeployFileParser() {
		this.xstream = new XStream(new PureJavaReflectionProvider());
		this.xstream.registerConverter(new DeployConverter(this.xstream.getMapper()));

		// 动作标签
		this.xstream.alias("deploy", Deploy.class);
		this.xstream.alias("copy", CopyOperation.class);
		this.xstream.useAttributeFor(CopyOperation.class, "src");
		this.xstream.useAttributeFor(CopyOperation.class, "dest");
		this.xstream.useAttributeFor(CopyOperation.class, "isBackup");
		this.xstream.aliasField("backup", CopyOperation.class, "isBackup");
		this.xstream.alias("move", MoveOperation.class);
		this.xstream.alias("exec", ExecOperation.class);
		this.xstream.useAttributeFor(ExecOperation.class, "workDir");
		this.xstream.aliasField("workdir", ExecOperation.class, "workDir");
		this.xstream.useAttributeFor(ExecOperation.class, "cmd");
		this.xstream.useAttributeFor(ExecOperation.class, "rollback");
		this.xstream.alias("mount", MountOperation.class);
		this.xstream.useAttributeFor(MountOperation.class, "src");
		this.xstream.useAttributeFor(MountOperation.class, "dest");
		this.xstream.alias("umount", UMountOperation.class);
		this.xstream.useAttributeFor(UMountOperation.class, "src");
		this.xstream.useAttributeFor(UMountOperation.class, "dest");
		this.xstream.alias("process", ProcessOperation.class);
		this.xstream.useAttributeFor(ProcessOperation.class, "startupCommand");
		this.xstream.aliasField("startupcommand", ProcessOperation.class, "startupCommand");
		this.xstream.useAttributeFor(ProcessOperation.class, "shutdownCommand");
		this.xstream.aliasField("shutdowncommand", ProcessOperation.class, "shutdownCommand");
		this.xstream.useAttributeFor(ProcessOperation.class, "startup");
		this.xstream.useAttributeFor(ProcessOperation.class, "processFlag");
		this.xstream.aliasField("processflag", ProcessOperation.class, "processFlag");
		this.xstream.useAttributeFor(ProcessOperation.class, "waitFor");
		this.xstream.aliasField("waitfor", ProcessOperation.class, "waitFor");
		this.xstream.useAttributeFor(ProcessOperation.class, "shell");
		// 如果需要转换继承来的字段,需要确保包含此字段的基类和此字段必须要有如下配置
		this.xstream.useAttributeFor(Assert.class, "expected");
		this.xstream.alias("existsprocess", ExistsProcessAssert.class);
		this.xstream.useAttributeFor(ExistsProcessAssert.class, "processFlag");
		this.xstream.aliasField("processflag", ExistsProcessAssert.class, "processFlag");
		this.xstream.alias("availablehttpurl", AvailableHttpURLAssert.class);
		this.xstream.useAttributeFor(AvailableHttpURLAssert.class, "url");

		// 数组元素标签
		this.xstream.alias("exclude", String.class);

	}

	public Deploy parse(File deployFile) throws Exception {
		Deploy deploy = new Deploy();
		InputStreamReader input = null;
		try {
			input = new InputStreamReader(new FileInputStream(deployFile), Charset.forName(FILE_CHARSET_NAME));
			this.xstream.fromXML(input, deploy);
			logger.debug("Loaded :{} from {}", deploy, deployFile);
		} catch (Exception e) {
			logger.error("Load deploy file error", e);
			throw new RuntimeException(e);
		} finally {
			IOUtils.closeQuietly(input);
		}
		return deploy;
	}

	public String ouput(List<Operation> operations) {
		return this.xstream.toXML(operations);
	}


}
