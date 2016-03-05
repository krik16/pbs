package com.rongyi.core.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DateUtil {
	
	public static final long SECOND = 1000;
    public static final long MINUTE = SECOND * 60;
    public static final long HOUR = MINUTE * 60;
    public static final long DAY = HOUR * 24;
    public static final long WEEK = DAY * 7;
    public static final long MONTH = DAY * 30;
    public static final long YEAR = DAY * 365;
	public static final Log LOGGER=LogFactory.getLog(DateUtil.class);
    
    /**
     * 判断是否为合法的yyyy-MM-dd格式
     * @param str
     * @return
     */
    public static boolean valid(String str){
    	DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date date = (Date)formatter.parse(str);
            return str.equals(formatter.format(date));
        }catch(Exception e){
           return false;
        }
    } 
    
	/**
	 * 
	 * @Title: stringToDate
	 * @Description:字符串转化为Date
	 * @param @param str str格式要求:yyyy,m(mm),d(dd);
	 *        如：2002-01-02，2002-1-2，2002-2-15
	 * @param @return
	 * @return Date 成功返回日期，失败返回null;
	 * @throws
	 */
	public static Date stringToDate(String str) {
		String strFormat = "yyyy-MM-dd HH:mm";
		if (str != null && str.length() == 10) {
			strFormat = "yyyy-MM-dd";
		}
		SimpleDateFormat sdFormat = new SimpleDateFormat(strFormat);
		Date date = new Date();
		try {
			date = sdFormat.parse(str);
		} catch (Exception e) {
			// System.out.println("Error="+e);
			return null;
		}
		return date;
	}

	/**
	 * 
	 * @Title: stringToDate
	 * @Description: 字符串以指定格式转化为Date
	 * @param @param str
	 * @param @param strFormat
	 * @param @return
	 * @return Date
	 * @throws
	 */
	public static Date stringToDate(String str, String strFormat) {
		SimpleDateFormat sdFormat = new SimpleDateFormat(strFormat);
		Date date = new Date();
		try {
			date = sdFormat.parse(str);
		} catch (Exception e) {
			return null;
		}
		return date;
	}
	public static Date stringToMongoDate(String str, String strFormat) {
		SimpleDateFormat sdFormat = new SimpleDateFormat(strFormat);
		sdFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
		Date date = new Date();
		try {
			date = sdFormat.parse(str);
		} catch (Exception e) {
			return null;
		}
		return date;
	}
	public static Date getDaysInPast(int numDay) {
        return org.apache.commons.lang.time.DateUtils.truncate(new Date((new Date()).getTime() - DateUtil.DAY * numDay), Calendar.DATE);
    }

    public static Date getDaysInPast(Date date, int numDay) {
        return org.apache.commons.lang.time.DateUtils.truncate(new Date(date.getTime() - DateUtil.DAY * numDay), Calendar.DATE);
    }
    
	public static Date getDaysInAdd(int numDay) {
        return org.apache.commons.lang.time.DateUtils.truncate(new Date((new Date()).getTime() + DateUtil.DAY * numDay), Calendar.DATE);
    }

    public static Date getDaysInAdd(Date date, int numDay) {
        return org.apache.commons.lang.time.DateUtils.truncate(new Date(date.getTime() + DateUtil.DAY * numDay), Calendar.DATE);
    }
    
    public static Date getTomorrow(Date date) {
        if (date == null) return null;
        return org.apache.commons.lang.time.DateUtils.truncate(new Date(date.getTime() + DateUtil.DAY), Calendar.DATE);
    }
    
    public static Date getHoursInFuture(Date date,int numhour) {
        return  org.apache.commons.lang.time.DateUtils.truncate(new Date(date.getTime() + DateUtil.HOUR * numhour), Calendar.MINUTE);
    }
    
    public static Date getHoursInPast(Date date,int numhour) {
        return  org.apache.commons.lang.time.DateUtils.truncate(new Date(date.getTime() - DateUtil.HOUR * numhour), Calendar.MINUTE);
    }
    
	/**
	 * dateToTimestamp
	 * @param date
	 * @return
	 */
	public static Timestamp dateToTimestamp(Date date) {
		Calendar tCal = Calendar.getInstance();
		tCal.setTime(date);
		Timestamp ts = new Timestamp(tCal.getTime().getTime());
		return ts;
	}
	
	/**
	 * stringToTimestamp
	 * @param str
	 * @return
	 */
	public static Timestamp stringToTimestamp(String str) {
		String strFormat = "yyyy-MM-dd HH:mm:ss";
		Date date = stringToDate(str, strFormat);
		Calendar tCal = Calendar.getInstance();
		tCal.setTime(date);
		Timestamp ts = new Timestamp(tCal.getTime().getTime());
		return ts;
	}
	
	/**
	 * 
	 * @Title: dateToYMD
	 * @Description: Date转换为字符串 yyyy-MM-dd
	 * @param @param dt
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String dateToYMD(Date dt) {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd");
		String str = "";
		try {
			str = sdFormat.format(dt);
		} catch (Exception e) {
			return "";
		}
		if (str.equals("1900-01-01")) {
			str = "";
		}

		return str;
	}

	/**
	 * 
	 * @Title: dateToString
	 * @Description: Date转换为字符串 yyyy-MM-dd HH:mm
	 * @param @param dt
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String dateToString(Date dt) {
		SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String str = "";
		try {
			str = sdFormat.format(dt);
		} catch (Exception e) {
			return "";
		}
		if (str.equals("1900-01-01 00:00")) {
			str = "";
		}

		return str;
	}

	/**
	 * 
	 * @Title: dateToString
	 * @Description: Date按指定格式转换为字符串
	 * @param @param dt
	 * @param @param strFormat
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String dateToString(Date dt, String strFormat) {
		SimpleDateFormat sdFormat = new SimpleDateFormat(strFormat);
		String str = "";
		try {
			str = sdFormat.format(dt);
		} catch (Exception e) {
			return "";
		}
		if (str.equals("1900-01-01 00:00")) {
			str = "";
		}

		return str;
	}

	/**
	 * 根据格式获得当前日期字符串
	 * 
	 * @param sFormat
	 * @return
	 */
	public static String getDateStr(String sFormat) {
		Calendar tCal = Calendar.getInstance();
		Timestamp ts = new Timestamp(tCal.getTime().getTime());
		java.util.Date date = new java.util.Date(ts.getTime());
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
		String tmpStr = formatter.format(date);
		return (tmpStr);
	}

	/**
	 * 根据给定格式获取特定Timestamp时间的格式化显示
	 * 
	 * @param ts
	 * @param sFormat
	 * @return
	 */
	public static String getDateFormat(Timestamp ts, String sFormat) {
		Date date = new Date(ts.getTime());
		SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
		String tmpStr = formatter.format(date);
		return tmpStr;
	}


	/**
	 * 获得当前日期的下一天
	 * 
	 * @param date
	 * @return
	 */
	public static String getNextDay(String date) {
		if (date == null || date.trim().length() == 0) {
			return "";
		}
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		try {
			calendar.setTime(f.parse(date));
		} catch (ParseException ex) {
			return date;
		}
		calendar.add(5, 1);
		return f.format(calendar.getTime());
	}

	/**
	 * 计算Timestamp日期之间的分钟差值
	 * 
	 * @param dt1
	 * @param dt2
	 * @return
	 */
	public static int dateDiff(Timestamp dt1, Timestamp dt2) {
		long ldate1 = dt1.getTime();
		long ldate2 = dt2.getTime();
		return (int) ((ldate2 - ldate1) / (1000*60));
	}

	public static int dateDiff(Date dt1, Date dt2) {
		long ldate1 = dt1.getTime();
		//System.out.println(ldate1);
		long ldate2 = dt2.getTime();
		//System.out.println(ldate2);
		return (int) ((ldate2 - ldate1) / (1000*60));
	}
	
	/**
	 * 根据所给日期返回两Date日期相差的秒数
	 * 
	 * @param d1
	 * @param d2
	 * @return 返回两个日期间隔的毫秒数
	 */
	public static long getSecond(Date d1, Date d2) {
		long a1 = d1.getTime();
		long a2 = d2.getTime();
		long a3 = (a1 - a2) / 1000;
		return a3;
	}

	/**
	 * 计算两个日期之间相差的天数
	 * @param smdate 较小的时间
	 * @param bdate 较大的时间
	 * @return 相差天数
	 * @throws ParseException
	 */
	public static int daysBetween(Date smdate, Date bdate) {
		long between_days = 0;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			smdate = sdf.parse(sdf.format(smdate));
			bdate = sdf.parse(sdf.format(bdate));
			Calendar cal = Calendar.getInstance();
			cal.setTime(smdate);
			long time1 = cal.getTimeInMillis();
			cal.setTime(bdate);
			long time2 = cal.getTimeInMillis();
			between_days = (time2 - time1) / (1000 * 3600 * 24);

		} catch (ParseException e) {
		}
		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 *字符串的日期格式的计算
	 */
	public static int daysBetween(String smdate, String bdate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smdate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bdate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}
	
	/**
	 * 根据所给日期返回两日期相差的天数
	 * 
	 * @param d1
	 *            前面时间
	 * @param d2
	 *            后面时间
	 * @return 返回两个日期间隔的天数
	 */
	public static long getDayNum(Date d1, Date d2) {
		
		long a1 = d1.getTime();
		long a2 = d2.getTime();
		long a3 = (a2 - a1) / (24 * 60 * 60 * 1000);
		return a3;
	}

	/**
	 * 根据所秒数,计算相差的时间并以**时**分**秒返回
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static String getBeapartDate(long m) {
		String beapartdate = "";
		int nDay = (int) m / (24 * 60 * 60);
		int nHour = (int) (m - nDay * 24 * 60 * 60) / (60 * 60);
		int nMinute = (int) (m - nDay * 24 * 60 * 60 - nHour * 60 * 60) / 60;
		beapartdate = java.lang.Math.abs(nDay) + "天"
				+ java.lang.Math.abs(nHour) + "小时"
				+ java.lang.Math.abs(nMinute) + "分";
		return beapartdate;
	}

	/**
	 * 
	 * @Title: checkDate
	 * @Description: 检查日期格式
	 * @param @param date
	 * @param @return
	 * @return boolean
	 * @throws
	 */
	public static boolean checkDate(String date) {
		boolean ret = true;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			df.setLenient(false);
			ret = df.format(df.parse(date)).equals(date);
		} catch (ParseException e) {
		    LOGGER.info("日期格式错误");
			ret = false;
			return ret;
		}
		String eL = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1][0-9])|([2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(date);
		boolean b = m.matches();
		if (b) {

//			LOGGER.info(date + "格式正确");
		} else {
		    LOGGER.info(date + "格式错误");
		}
		return b;

	}
	/**
	 * 格式化当前时间  返回格式HHmmssSSS
	 * @return
	 */
	public static String getCurrentHMSSSS() {
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmssSSS");
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}
	/**
	 * 格式化当前时间  返回格式HHmmss
	 * @return
	 */
	public static String getCurrentHMSS() {
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}
	/**
	 * 格式化当前时间  返回格式yyMMdd
	 * @return
	 */
	public static String getCurrentDateYYMMDD() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMdd");
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}
	
	/**
	 * 格式化当前时间  返回格式yyyyMMddHHmmss
	 * @return
	 */
	public static String getCurrentDateYYYYMMDD() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}
	
	/**	
	 * @Description: 格式化当前时间  返回格式yyyy-MM-dd HH:mm:ss:SSS
	 * @return	
	 * @Author:  柯军
	 * @datetime:2015年9月6日下午3:26:07
	 **/
	public static String getCurrentDateYYYYMMDDHHMMSSsss(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
		Date currentTime = new Date();
		return formatter.format(currentTime);
	}
	public static boolean isYesterday(Date date){
		
		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(new Date());
		Calendar dateCal = Calendar.getInstance();
		dateCal.setTime(date);
		dateCal.add(Calendar.DAY_OF_YEAR, 1);
		
		return dateCal.get(Calendar.DAY_OF_YEAR)==todayCal.get(Calendar.DAY_OF_YEAR);
		
	}
	public static String getDilei(Date arriveTime){
		if(!dateToYMD(arriveTime).equals(dateToYMD(new Date())))
			return "1";
		else
			return "0";
	}
	
	// 将传入时间与当前时间进行对比，是否今天\昨天\前天\同一年
	public static String getDescTime(Timestamp date_) {
		Date date = new Date(date_.getTime());
		String tody = "今天 ";
		String todySDF = "HH:mm";
		String yesterDaySDF = "昨天 ";
		String beforYesterDaySDF = "前天 ";
		String otherYearSDF = "yyyy/MM/dd";
		SimpleDateFormat sfd = null;
		String time = "";
		Date now = new Date();
		if(daysBetween(date, now) == 0) {
			//今天
			sfd = new SimpleDateFormat(todySDF);
			time = sfd.format(date);
			return tody + time;
		}else if(daysBetween(date, now) == 1) {
			//昨天
			sfd = new SimpleDateFormat(todySDF);
			time = sfd.format(date);
			time = yesterDaySDF + time;
			return time;
		}else if(daysBetween(date, now) == 2) {
			//前天
			sfd = new SimpleDateFormat(todySDF);
			time = sfd.format(date);
			time = beforYesterDaySDF + time;
			return time;
		}else {
			sfd = new SimpleDateFormat(otherYearSDF);
			time = sfd.format(date);
			return time;
		}
	}
	
	// 将传入时间与当前时间进行对比，是否今天\昨天\前天\同一年
	public static String getDescTime_(Timestamp date_) {
		Date date = new Date(date_.getTime());
		String tody = "今天";
		String todySDF = "HH:mm";
		String yesterDaySDF = "昨天";
		String beforYesterDaySDF = "前天";
		String otherYearSDF = "yyyy/MM/dd";
		SimpleDateFormat sfd = null;
		String time = "";
		Date now = new Date();
		if(daysBetween(date, now) == 0) {
			//今天
			return tody;
		}else if(daysBetween(date, now) == 1) {
			//昨天
			return yesterDaySDF;
		}else if(daysBetween(date, now) == 2) {
			//前天
			return beforYesterDaySDF;
		}else {
			sfd = new SimpleDateFormat(otherYearSDF);
			time = sfd.format(date);
			return time;
		}
		
	}
	
	/**
	 * 获取开业时间
	 * @param opentime
	 * @return
	 */
	public static String getOpenTime(String opentime){
		String retTime = "09:30";
		if (opentime == null || opentime.length()<5) {
			retTime = "09:30";
		}
		else if(opentime.length()==5){
			int a = Integer.parseInt(opentime.substring(0,2));
			if(a<9){
				retTime="09:30";
			}
			else if(a==9)
			{
				int b = Integer.parseInt(opentime.substring(3,5));
				if(b<30)
				{
					retTime="09:30";
				}	
			}
			else{
				retTime = opentime;
			}
		}
		return retTime;
	}
	
	/**
	 * 获取停业时间
	 * @param closetime
	 * @return
	 */
	public static String getCloseTime(String closetime){
		String retTime = "21:30";
		if (closetime == null || closetime.length()<5) {
			retTime = "21:30";
		}
		else if(closetime.length()==5){
			int a = Integer.parseInt(closetime.substring(0,2));
			if(a>=22){
				retTime="21:30";
			}
			else if(a==21)
			{
				int b = Integer.parseInt(closetime.substring(3,5));
				if(b>30)
				{
					retTime="21:30";
				}
				else{
					retTime = closetime;
				}
			}
			else{
				retTime = closetime;
			}
		}
		return retTime;
	}
	
	/**
	 * 判断当前时间是否迟于给定时间 参数格式如10:00
	 * @param ctime
	 * @param time
	 * @return
	 */
	public static boolean isLaterThenTime(String ctime, String time){
		int th1 = Integer.parseInt(ctime.split(":")[0]);
		int tm1 = Integer.parseInt(ctime.split(":")[1]);
		
		int th2 = Integer.parseInt(time.split(":")[0]);
		int tm2 = Integer.parseInt(time.split(":")[1]);
		
		if(th1>th2){
			return true;
		}
		else if(th1==th2 && tm1>tm2){
			return true;
		}
		else{
			return false;
		}
	}
	
	
	/**
     * 判断当前时间是否处于给定时间区间  参数格式如10:00
     * @param time1
     * @param time2
     * @return
     */
	public static boolean nowInTimearea(String time1,String time2){
		Calendar c = Calendar.getInstance();
		int ch = c.get(Calendar.HOUR_OF_DAY);
		int cm = c.get(Calendar.MINUTE);
		
		String ctime = ch+":"+cm;
		//System.out.println("ctime="+ctime);
		if(isLaterThenTime(ctime,time1) && isLaterThenTime(time2,ctime)){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * 取得当前日期时间
	 * 
	 * @return
	 */
	public static Timestamp getCurrDateTime() {
		Calendar tCal = Calendar.getInstance();
		Timestamp createDate = new Timestamp(tCal.getTime().getTime());
		return createDate;
	}
	
	/**
	 * 获取当天的日期
	 * 
	 * @return
	 */
	public static String getToday() {
		java.sql.Timestamp ts = new java.sql.Timestamp(System
				.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(ts);
	}
	
	/**
	 * 比较两个String日期之间的大小
	 * @param DATE1
	 * @param DATE2
	 * @return
	 */
	public static int compareDate(String DATE1, String DATE2) {
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    try {
	        Date dt1 = df.parse(DATE1);
	        Date dt2 = df.parse(DATE2);
	        if (dt1.getTime() > dt2.getTime()) {
	            return 1;
	        } else if (dt1.getTime() < dt2.getTime()) {
	            return -1;
	        } else {
	            return 0;
	        }
	    } catch (Exception exception) {
	        exception.printStackTrace();
	    }
	    return 0;
	}
	
	/**
	 * 获取明天的日期
	 * 
	 * @return
	 */
	public static String getTomorrow() {
		return getNextDay(getToday());
	}
	
	/**
	 * 
	 * @Title: dateToHasWeekString
	 * @Description: Date转换为字符串 yyyy-MM-dd 星期一 HH:mm
	 * @param @param dt
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String dateToHasWeekString(Date dt) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");//EEEE代表星期，如“星期四”；
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(dateFormat.format(dt)).append(" ");
			sb.append(dateFm.format(dt)).append(" ");
			sb.append(timeFormat.format(dt));
		} catch (Exception e) {
			return "";
		}
		return sb.toString();
	}
	
	/**
	 * 
	 * @Title: dateToHasWeekString
	 * @Description: Date转换为字符串 yyyy-MM-dd 星期一 HH:mm
	 * @param @param dt
	 * @param @return
	 * @return String
	 * @throws
	 */
	public static String dateTimeToHasWeekString(Date dt1, Date dt2) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFm = new SimpleDateFormat("EEEE", Locale.CHINA);//EEEE代表星期，如“星期四”；
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(dateFormat.format(dt1)).append(" ");
			sb.append(dateFm.format(dt1)).append(" ");
			sb.append(timeFormat.format(dt1)).append(" - ");
			sb.append(timeFormat.format(dt2));
		} catch (Exception e) {
			return "";
		}
		return sb.toString();
	}
	
	/**
	 * 添加小时
	 *
	 * @param date
	 *            日期
	 * @param num
	 *            添加的小时
	 * @return 添加后的小时
	 */
	public static java.util.Date addHours(java.util.Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, num);
		return cal.getTime();
	}

	/**
	 * 时间加减
	 *
	 * @param date
	 *            日期
	 * @param num
	 *            添加的时间
	 * @param field
	 *            添加类型
	 * @return 添加后的日期
	 */
	public static java.util.Date addTime(java.util.Date date, int num,int field) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, num);
		return cal.getTime();
	}


	public static String getConfigurationDate(long m) {
		String beapartdate = "";
		int nDay = java.lang.Math.abs((int) m / (24 * 60 * 60));
		int nHour = java.lang.Math.abs((int) (m - nDay * 24 * 60 * 60) / (60 * 60));
		int nMinute = java.lang.Math.abs((int) (m - nDay * 24 * 60 * 60 - nHour * 60 * 60) / 60);
		String hour = nHour + "";
		String minute = nMinute + "";
		if (nDay > 0) {
			beapartdate = beapartdate + nDay + "天";
		}
		if(nHour > 0){
			beapartdate = beapartdate + hour + "小时";
		}
		if(nMinute > 0){
			beapartdate = beapartdate + minute + "分钟";
		}
		return beapartdate;
	}

	/**
	 * 增加天数（排除双休日）
	 * @param date
	 * @param num
	 * @return
	 */
	public static Date addWorkDay(Date date, int num) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int mod = num % 5;
		int other = num / 5 * 7;
		for (int i = 0; i < mod;) {
			cal.add(Calendar.DATE, 1);
			switch (cal.get(Calendar.DAY_OF_WEEK)) {
				case Calendar.SUNDAY:
				case Calendar.SATURDAY:
					break;
				default:
					i++;
					break;
			}
		}
		if (other > 0)
			cal.add(Calendar.DATE, other);
		return cal.getTime();
	}

}
