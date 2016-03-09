package com.shouyingbao.pbs.core.common.util.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * ClassName: DateUtil <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: 日期工具 <br/>
 * date: 2015年5月6日 下午8:40:42 <br/>
 * 
 * @author bin.zhang
 * @version
 * @since JDK 1.6
 */
public abstract class DateUtil {


  /****
   * 天数相加减
   * 
   * @param date
   * @param day
   * @return
   */
  public static Date addDay(Date date, int day) {
    if (date == null) {
      return null;
    }
    java.util.Calendar c = java.util.Calendar.getInstance();
    c.setTime(date);
    c.add(java.util.Calendar.DAY_OF_YEAR, day);
    return c.getTime();
  }

  /****
   * 秒相加减
   * 
   * @param date
   * @param seconds
   * @return
   */
  public static Date addSecond(Date date, int seconds) {
    if (date == null) {
      return null;
    }
    java.util.Calendar c = java.util.Calendar.getInstance();
    c.setTime(date);
    c.add(java.util.Calendar.SECOND, seconds);
    return c.getTime();
  }

  /****
   * 将时间转换为当天的0时0分0秒0毫秒
   * 
   * @param date
   * @return
   */
  public static Date convert2StartDate(Date date) {
    if (date == null) {
      return null;
    }
    java.util.Calendar c = java.util.Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.HOUR_OF_DAY, 0);
    c.set(Calendar.MINUTE, 0);
    c.set(Calendar.SECOND, 0);
    c.set(Calendar.MILLISECOND, 0);
    return c.getTime();
  }

  /****
   * 将时间转换为当天的23时59分59秒999毫秒
   * 
   * @param date
   * @return
   */
  public static Date convert2EndDate(Date date) {
    if (date == null) {
      return null;
    }
    java.util.Calendar c = java.util.Calendar.getInstance();
    c.setTime(date);
    c.set(Calendar.HOUR_OF_DAY, 23);
    c.set(Calendar.MINUTE, 59);
    c.set(Calendar.SECOND, 59);
    c.set(Calendar.MILLISECOND, 999);
    return c.getTime();
  }

  public static Date parseDate(String dateStr, String pattern) {
    try {
      return new SimpleDateFormat(pattern).parse(dateStr);
    } catch (ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return null;
  }

  public static String format(Date date, String pattern) {
    return new SimpleDateFormat(pattern).format(date);
  }


}
