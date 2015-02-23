package com.ffcs.ads.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang.ArrayUtils;

/**
*
* 排除文件过滤器.
* 
* @author dylan.chen Mar 10, 2011
*
*/
public class ExcludesFileFilter implements IOFileFilter {

	private List<Pattern> patterns = new ArrayList<Pattern>();

	public ExcludesFileFilter(String[] excludes) {
		if (ArrayUtils.isNotEmpty(excludes)) {
			for (int i = 0; i < excludes.length; i++) {
				this.patterns.add(Pattern.compile(excludes[i]));
			}
		}
	}

	public boolean accept(File pathname) {
		if (this.patterns.size() != 0) {
			String filepath = pathname.getAbsolutePath();
			for (Pattern pattern : this.patterns) {
				if (pattern.matcher(filepath).find()) {
					return false;
				}
			}
		}
		return true;
	}

	public boolean accept(File dir, String name) {
		return accept(dir);
	}
}