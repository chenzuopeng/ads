package com.ffcs.ads.operations;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 验证一个Http的URL是否有效的断言.
 * 
 * @author dylan.chen Mar 13, 2011
 * 
 */
public class AvailableHttpURLAssert extends Assert {

	private static final long serialVersionUID = -2986859002227220768L;

	private static Logger logger = LoggerFactory.getLogger(AvailableHttpURLAssert.class);

	private String url;

	@Override
	protected boolean doAssert() throws Exception {
		try {
			URL target = new URL(this.url);
			HttpURLConnection connection = (HttpURLConnection) target.openConnection();
			int responseCode = connection.getResponseCode();
			logger.debug("请求URL:{}", this.url);
			logger.debug("响应码:{},响应消息:{}", responseCode, connection.getResponseMessage());
			return HttpURLConnection.HTTP_OK == responseCode;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
