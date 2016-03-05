package com.shouyingbao.core.common.util.date;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Pattern;

import com.shouyingbao.core.common.util.string.SuperString;

public class SuperDate {
  private Date myDate;
  public static final String patternmonthday = "M月d日";
  public static final String patternDate = "yyyy-MM-dd";
  public static final String patternyyyyMMdd = "yyyyMMdd";
  public static final String patternShortYearMonthDay = "yy/MM/dd";
  public static final String patternInclineDateString = "yyyy/MM/dd";
  public static final String patternYearMonth = "yyyy-MM";
  public static final String patternYearMonthText = "yyyy年MM月";
  public static final String patternYearMonthValue = "yyyyMM";
  public static final String patternDateTime = "yyyy-MM-dd HH:mm:ss";
  public static final String patternDateMinutes = "yyyy-MM-dd HH:mm";
  public static final String patternTextDate = "第w周 EEE yyyy年M月d日";
  public static final String patternCnDateTimeShort = "yyyy年M月d日";
  public static final String patternCnDateTime = "yyyy年M月d日 H点m分s秒";
  public static final String[] arrWeek = new String[] {"一", "二", "三", "四", "五", "六", "日"};
  private SimpleDateFormat f;
  private GregorianCalendar cal;

  /* 初始化方法 */
  private void initialize() {
    cal = new GregorianCalendar();
    cal.setTime(this.myDate);
  }

  /* 构造方式 */
  public SuperDate() {
    this.myDate = new Date();
    initialize();
  }

  public SuperDate(long dlong) {
    this.myDate = new Date();
    this.myDate.setTime(dlong);
    initialize();
  }

  public SuperDate(Date d) {
    this.myDate = d;
    initialize();
  }

  public SuperDate(String dateTimeString) {
    if (dateTimeString == null || dateTimeString.length() == 0) {
      this.myDate = new Date();
    } else {
      f = new SimpleDateFormat(patternDateTime);
      this.myDate = f.parse(dateTimeString, new ParsePosition(0));
    }
    initialize();
  }

  public SuperDate(String dateTimeString, String pattern) {
    if (SuperString.isBlank(dateTimeString)) {
      this.myDate = new Date();
    } else {
      f = new SimpleDateFormat(pattern);
      this.myDate = f.parse(dateTimeString, new ParsePosition(0));
    }
    initialize();
  }

  public SuperDate(String Year, String Month, String Day) {
    f = new SimpleDateFormat(patternDate);
    this.myDate = f.parse(Year + "-" + Month + "-" + Day, new ParsePosition(0));
    initialize();
  }

  public SuperDate(int Year, int Month, int Day) {
    f = new SimpleDateFormat(patternDate);
    this.myDate =
        f.parse(
            Integer.toString(Year) + "-" + Integer.toString(Month) + "-" + Integer.toString(Day),
            new ParsePosition(0));
    initialize();
  }

  public SuperDate(String Year, String Month, String Day, String Hour, String Minute, String Second) {
    f = new SimpleDateFormat(patternDateTime);
    this.myDate =
        f.parse(Year + "-" + Month + "-" + Day + " " + Hour + ":" + Month + ":" + Second,
            new ParsePosition(0));
    initialize();
  }

  public SuperDate(int Year, int Month, int Day, int Hour, int Minute, int Second) {
    f = new SimpleDateFormat(patternDateTime);
    this.myDate =
        f.parse(
            Integer.toString(Year) + "-" + Integer.toString(Month) + "-" + Integer.toString(Day)
                + " " + Integer.toString(Hour) + ":" + Integer.toString(Minute) + ":"
                + Integer.toString(Second), new ParsePosition(0));
    initialize();
  }


  /* 取得日期的各个部分 */
  public int getYear() {
    return cal.get(Calendar.YEAR);
  }

  public int getMonth() {
    return cal.get(Calendar.MONTH) + 1;
  }

  public int getDay() {
    return cal.get(Calendar.DAY_OF_MONTH);
  }

  public int getHour() {
    return cal.get(Calendar.HOUR_OF_DAY);
  }

  public int getMinute() {
    return cal.get(Calendar.MINUTE);
  }

  public int getSecond() {
    return cal.get(Calendar.SECOND);
  }

  public long getMilliSecond() {
    return cal.getTime().getTime();
  }

  /**
   * 获取1970-01-01至今的秒
   * 
   * @return
   */
  public int getTimestampSecond() {
    return ((Long) (getDate().getTime() / 1000)).intValue();
  }

  /* 三种常用的比较方法 */
  public boolean after(SuperDate when) {
    return this.myDate.after(when.myDate);
  }

  public boolean before(SuperDate when) {
    return this.myDate.before(when.myDate);
  }

  public boolean compareTo(SuperDate when) {
    return this.myDate.equals(when.myDate);
  }

  public int compareToByDate(SuperDate when) {
    if (this.getYear() > when.getYear())
      return 1;
    else if (this.getYear() < when.getYear())
      return -1;
    else {
      if (this.getMonth() > when.getMonth())
        return 1;
      else if (this.getMonth() < when.getMonth())
        return -1;
      else {
        if (this.getDay() > when.getDay())
          return 1;
        else if (this.getDay() < when.getDay())
          return -1;
        else
          return 0;
      }
    }
  }

  /* 输入格式化的日期字符串 */
  public String getDateString() {
    f = new SimpleDateFormat(patternDate);
    return (f.format(this.myDate));
  }

  public String getDateTimeString() {
    f = new SimpleDateFormat(patternDateTime);
    return (f.format(this.myDate));
  }

  public String getTextDateString() {
    f = new SimpleDateFormat(patternTextDate, Locale.CHINA);
    return (f.format(this.myDate));
  }

  public String getCnDateTimeString() {
    f = new SimpleDateFormat(patternCnDateTime, Locale.CHINA);
    return (f.format(this.myDate));
  }

  public String getCnDateTimeShortString() {
    f = new SimpleDateFormat(patternCnDateTimeShort, Locale.CHINA);
    return (f.format(this.myDate));
  }

  public String getDateMinutesString() {
    f = new SimpleDateFormat(patternDateMinutes);
    return (f.format(this.myDate));
  }

  public String getMonthDay() {
    f = new SimpleDateFormat(patternmonthday, Locale.CHINA);
    return (f.format(this.myDate));
  }

  public String getYearMonthString() {
    f = new SimpleDateFormat(patternYearMonth);
    return (f.format(this.myDate));
  }

  public String getShortDateString() {
    f = new SimpleDateFormat(patternShortYearMonthDay);
    return (f.format(this.myDate));
  }

  public String getInclineDateString() {
    f = new SimpleDateFormat(patternInclineDateString);
    return (f.format(this.myDate));
  }

  public String getYearMonthStringText() {
    f = new SimpleDateFormat(patternYearMonthText);
    return (f.format(this.myDate));
  }

  public String getYearMonthValue() {
    f = new SimpleDateFormat(patternYearMonthValue);
    return (f.format(this.myDate));
  }

  /* 格式话日期为标准形式 */
  public static String formatDateTime(Date d, String pattern) {
    SimpleDateFormat fd = new SimpleDateFormat(pattern, Locale.CHINA);
    return (fd.format(d));
  }

  /* 返回指定的格式 */
  public String getFormatDateTime(String pattern) {
    return new SimpleDateFormat(pattern).format(myDate);
  }

  public Date getDate() {
    return this.myDate;
  }

  public Calendar getCalendar() {
    return this.cal;
  }

  /* 将当前的SuperDate加上某个时间，改变当前SuperDate的值 */
  public void add(int field, int amount) {
    this.cal.add(field, amount);
    this.myDate = cal.getTime();
  }

  /* 返回一个新的加过时间的SuperDate，不该变原来的值 */
  public SuperDate getAddedDate(int field, int amount) {
    SuperDate addedSuperDate = new SuperDate(this.myDate);
    addedSuperDate.add(field, amount);
    return addedSuperDate;
  }

  public static SuperDate getFirstDayInMonthDate(SuperDate thesd) {
    return new SuperDate(thesd.getYear() + "-" + thesd.getMonth() + "-01 00:00:00");
  }

  public static boolean isToday(SuperDate sd1, SuperDate sd2) {
    return !sd1.before(new SuperDate(sd2.getDateString() + " 00:00:00"))
        && !sd1.after(new SuperDate(sd2.getDateString() + " 23:59:59"));
  }

  /* 计算日期之际的间隔 */
  public static long getTimeInteval(SuperDate sd1, SuperDate sd2, int intUnit) {
    long intevalms;
    long returnValue = 0;
    intevalms = sd2.myDate.getTime() - sd1.myDate.getTime();
    switch (intUnit) {
      case Calendar.DAY_OF_MONTH:
        returnValue = intevalms / (1000 * 3600 * 24);
        break;
      case Calendar.DAY_OF_YEAR:
        returnValue = intevalms / (1000 * 3600 * 24);
        break;
      case Calendar.HOUR:
        returnValue = intevalms / (1000 * 3600);
        break;
      case Calendar.MINUTE:
        returnValue = intevalms / (1000 * 60);
        break;
      case Calendar.SECOND:
        returnValue = intevalms / 1000;
        break;
    }
    return (returnValue);
  }

  /* 计算日期之间的月份间隔 */
  public static long getTimeMonthInterval(SuperDate sd1, SuperDate sd2) {
    sd1 = getFirstDayInMonthDate(sd1);
    sd2 = getFirstDayInMonthDate(sd2);
    boolean bNegative = sd1.after(sd2);
    if (sd1.after(sd2)) {
      SuperDate sdTemp = sd1;
      sd1 = sd2;
      sd2 = sdTemp;
    }
    long l = 0l;
    while (sd1.before(sd2)) {
      sd1.add(Calendar.MONTH, 1);
      l++;
    }
    return bNegative ? 0 - l : l;
  }

  // 注意：月份的设置。西历中的月份比中历少1，此处是中历月份
  public int getActualMaximumDaysInMonth() {
    return getCalendar().getActualMaximum(Calendar.DAY_OF_MONTH);
  }

  // 注意：星期的设置。1是星期天，7是星期六,一个月的第一天是星期几
  public int getActualFirstWeekDayInMonth() {
    Calendar calendar = new SuperDate(getDate()).getCalendar();
    calendar.set(Calendar.DAY_OF_MONTH, 1);
    return calendar.get(Calendar.DAY_OF_WEEK);
  }

  // 根据日期得到前一周的周日
  public String getPrevSunday(SuperDate sDate) {
    int week = sDate.getCalendar().get(Calendar.DAY_OF_WEEK);
    return sDate.getAddedDate(Calendar.DAY_OF_YEAR, -1 * (7 + week - 1)).getDateString();
  }

  // 根据日期得到后一周的周日
  public String getNextSunday(SuperDate sDate) {
    int week = sDate.getCalendar().get(Calendar.DAY_OF_WEEK);
    return sDate.getAddedDate(Calendar.DAY_OF_YEAR, (7 - week + 1)).getDateString();
  }

  // 根据日期得到后一周的周日
  public String getNextThirdSunday(SuperDate sDate) {
    int week = sDate.getCalendar().get(Calendar.DAY_OF_WEEK);
    return sDate.getAddedDate(Calendar.DAY_OF_YEAR, (14 - week + 1)).getDateString();
  }

  // 得到本周的周日
  public String getCurrSunday(SuperDate sDate) {
    int week = sDate.getCalendar().get(Calendar.DAY_OF_WEEK);
    return sDate.getAddedDate(Calendar.DAY_OF_YEAR, (1 - week)).getDateString();
  }

  // 得到本周的周六
  public String getCurrSaturday(SuperDate sDate) {
    int week = sDate.getCalendar().get(Calendar.DAY_OF_WEEK);
    return sDate.getAddedDate(Calendar.DAY_OF_YEAR, (7 - week)).getDateString();
  }

  public String getWeekText() {
    return arrWeek[getWeekValue() - 1];
  }

  public int getWeekValue() {
    int iweek = this.cal.get(Calendar.DAY_OF_WEEK);
    return iweek == 1 ? 7 : iweek - 1;
  }

  public Date getTodayStartTime() {
    this.cal.set(this.getYear(), this.getMonth() - 1, this.getDay(), 0, 0, 0);
    return this.cal.getTime();
  }

  public Date getTodayNextDayTime() {
    this.cal.set(this.getYear(), this.getMonth() - 1, this.getDay(), 0, 0, 0);
    this.cal.add(GregorianCalendar.DAY_OF_YEAR, 1);
    return this.cal.getTime();
  }

  public String getEventDateString() {
    String strYear = String.valueOf(getYear()).substring(2);
    String strMonth = String.valueOf(getMonth());
    if (getMonth() < 10)
      strMonth = "0" + strMonth;
    String strDay = String.valueOf(getDay());
    if (getDay() < 10)
      strDay = "0" + strDay;
    return strYear + "." + strMonth + "." + strDay;
  }

  public static SuperDate getSuperDateNotNull(Date ld, String def_date) {
    SuperDate sdtemp = null;
    try {
      sdtemp = new SuperDate(ld);
    } catch (Exception e) {
      if (!isDateString(def_date))
        sdtemp = new SuperDate();
      else
        sdtemp = new SuperDate(def_date);
    }
    return sdtemp;
  }

  public static boolean isDateTimeString(String s) {
    return match("(\\d{3})-(\\d{1})-(\\d{1}) (\\d{1}):(\\d{1}):(\\d{1})", s);
  }

  public static boolean isDateString(String s) {
    return match("(\\d{3})-(\\d{1})-(\\d{1})", s);
  }

  private static boolean match(String regex, String str) {
    return Pattern.matches(regex, str);
  }
}
