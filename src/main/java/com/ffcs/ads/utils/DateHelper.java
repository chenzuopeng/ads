package com.ffcs.ads.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 提供日期相关操作的助手类.
 * 
 * @author dylan.chen 2009-9-23
 *
 */
public final class DateHelper {

	private static final Logger logger = LoggerFactory.getLogger(DateHelper.class);

	/**
	 * 默认的日期格式字符串
	 * */
	public final static String DEFAULT_DATE_FORMAT_PATTERN="yyyy-MM-dd HH:mm:ss";

	private DateHelper(){
	}

	/**
	 * 验证是否过期
	 * @param exirationTime 过期时间
	 * @return boolean true 过期,false 未过期
	 * */
	public static boolean isExpired(Date exirationTime){
		Date currentDate=new Date();
		if(logger.isDebugEnabled()){
		    logger.debug("当前时间:"+DateHelper.getTimeStamp(currentDate));
		    logger.debug("过期时间:"+DateHelper.getTimeStamp(exirationTime));
		}
		return currentDate.after(exirationTime);
	}

	/**
	 * 验证是否过期
	 *   注:如果不指定格式表达式,则使用默认的格式表达式("yyyy-MM-dd HH:mm:ss")
	 *
	 * @param exirationTime 过期时间字符串
	 * @param parsePatterns 用于解析日期的格式表达式
	 * @return boolean true 过期,false 未过期或过期时间字符串解析错误
	 * */
	public static boolean isExpired(String exirationTime,String... parsePatterns){
		boolean result=false;
		if(ArrayUtils.isEmpty(parsePatterns)){
			parsePatterns=new String[]{DEFAULT_DATE_FORMAT_PATTERN};
		}
		try {
			result=isExpired(DateUtils.parseDate(exirationTime,parsePatterns));
		} catch (ParseException e) {
			logger.error(String.format("日期字符串[%s]格式非法", exirationTime), e);
		}
		return result;
	}

	/**
	 * 获取当前的时间戳的字符串表示形式
	 *   注:如果不指定格式表达式,则使用默认的格式表达式("yyyy-MM-dd HH:mm:ss")
	 *
	 * @param parsePatterns 用于解析日期的格式表达式
	 * @return String
	 * */
	public static String getCurrentTimeStamp(String... parsePatterns){
		return getTimeStamp(new Date(),parsePatterns);
	}

	/**
	 * 获取指定时间戳的字符串表示形式
	 *   注:如果不指定格式表达式,则使用默认的格式表达式("yyyy-MM-dd HH:mm:ss")
	 *
	 * @param date 指定的时间
	 * @param parsePatterns 用于解析日期的格式表达式
	 * @return String
	 * */
	public static String getTimeStamp(Date date,String... parsePatterns){
		return DateFormatUtils.format(date,ArrayUtils.isEmpty(parsePatterns)?DEFAULT_DATE_FORMAT_PATTERN:parsePatterns[0]);
	}

	/**
	 * 判断date1和date2表示的日期是否在同一个月份
	 * @param date1
	 * @param date2
	 * @return boolean true 是同一个月份,false 不是同一个月份
	 * */
	public static boolean isSameMonth(Date date1,Date date2){
		return DateUtils.isSameInstant(DateUtils.truncate(date1,Calendar.MONTH),DateUtils.truncate(date2,Calendar.MONTH));
	}


	/**
	 * 到当前日期的前或后若干个年
	 *
	 * @param years
	 * @return date
	 */
	public static Date getNextYear(String years) {
		return DateUtils.addYears(new Date(),Integer.parseInt(years));
	}

	/**
	 * 获取下个月的第一天
	 * @return date
	 * */
	public static Date getFirstDayOfNextMonth(Date date){
		return DateUtils.truncate(DateUtils.addMonths(date,1),Calendar.MONTH);
	}

	/**
	 * 获取本月的第一天
	 * @return date
	 * */
	public static Date getFirstDayOfCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return calendar.getTime();
	}

	/**
	 * 获取次日
	 * @return date
	 * */
	public static Date getTomorrow(Date date){
		return DateUtils.truncate(DateUtils.addDays(date,1),Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取一个未来的时间点
	 * @return 未来的时间点
	 * */
	public static Date getFutureTime(){
		Date result=null;
		try {
			result=DateUtils.parseDate("2099-12-31 23:59:59", new String[]{"yyyy-MM-dd HH:mm:ss"});
		} catch (ParseException e) {
			//忽略这个异常
		}
		return result;
	}

	/**
	 * 获取当前时间的前一个小时
	 * @return 当前时间的前一个小时
	 * */
	public static Date getPreviousHour(){
		return DateUtils.truncate(DateUtils.addHours(new java.util.Date(),-1),Calendar.HOUR);
	}
}
