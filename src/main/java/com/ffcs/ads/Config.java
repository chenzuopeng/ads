package com.ffcs.ads;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

/**
 *
 * 用于存放程序配置信息的类.
 *
 * @author dylan.chen Mar 11, 2011
 *
 */
public class Config {

	private static class ConfigLoader {

		private static Logger logger = LoggerFactory.getLogger(ConfigLoader.class);

		private final static String REFERENCE_LOCATION = "config.xml";

		private final static String FILE_CHARSET_NAME = "UTF-8";

		public static void load() {
			XStream xstream = new XStream();
			xstream.alias("config", Config.class);
			xstream.aliasField("workspacehome", Config.class, "workspaceHome");
			InputStreamReader input = null;
			String configPath = ClassLoader.getSystemResource(REFERENCE_LOCATION).getFile();
			try {
				input = new InputStreamReader(new FileInputStream(configPath), Charset.forName(FILE_CHARSET_NAME));
				Config.instance = (Config) xstream.fromXML(input);
				logger.debug("Loaded :{} from {}", instance, configPath);
			} catch (Exception e) {
				logger.error("Load config file error", e);
				throw new RuntimeException(e);
			} finally {
				IOUtils.closeQuietly(input);
			}
		}
	}

	static {
		ConfigLoader.load();
	}

	private static Config instance;

	public static Config getInstance() {
		return instance;
	}
	
	private String workspaceHome; 


	public String getWorkspaceHome() {
		return workspaceHome;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
