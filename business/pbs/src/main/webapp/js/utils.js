/**
 * User: mark <mark@wosai-inc>
 * Created at: 11/1/13 9:30 AM
 */
/**
 * User: mark <mark@wosai-inc.com>
 * Date: 9/13/13 10:02 AM
 */
// if the module has no dependencies, the above pattern can be simplified to
(function(root, factory) {
  if (typeof define === 'function') {
    // AMD. Register as an anonymous module.
    define(factory);
  } else if (typeof exports === 'object') {
    // Node. Does not work with strict CommonJS, but
    // only CommonJS-like enviroments that support module.exports,
    // like Node.
    module.exports = factory();
  } else {
    // Browser globals (root is window)
    root.UTILS = factory();
  }
}(this, function() {

  // Just return a value to define the module export.
  // This example returns an object, but the module
  // can return a function as the exported value.
  /**
   * 将客户端的元转换为服务器的Money
   */

  function yuanToServerMoney(yuan) {}

  /*
   * 将客户端的Money转化为元
   */
  function serverMoneyToYuan(serverMoney) {
    if (isNaN(serverMoney)) {
      return null;
    } else {
      var str = String(serverMoney);
      var yuan = str.substr(0, str.length - 2);
      var fen = str.substr(-2);
      var value = Number((yuan + "." + fen));
      return value;
    }
  }
  function moneyFormat(money) {
    if(isNaN(money)) {
      return null;
    } else return money.toFixed(2);
  }

  function getLatestWeekStartDate() {
    /*这是周日为第一天的算法
      var today = new Date();
      var day = today.getDay();
      var tempDate = new Date(today-1000*60*60*24*day);
      var weekStartDate = new Date(tempDate.getFullYear(),tempDate.getMonth(),tempDate.getDate());
      */
    //这是周一为第一天的算法
    var today = new Date();
    var day = today.getDay();
    var tempDate = new Date();
    if (day == 0) {
      tempDate = new Date(today - 1000 * 60 * 60 * 24 * 6);
    } else {
      tempDate = new Date(today - 1000 * 60 * 60 * 24 * (day - 1));
    }
    var weekStartDate = new Date(tempDate.getFullYear(), tempDate.getMonth(), tempDate.getDate());
    return weekStartDate;
  }

  function getLatestMonthStartDate() {
    var today = new Date();
    var month = today.getMonth();
    var monthStartDate = new Date(today.getFullYear(), month, '1');
    return monthStartDate;
  }

  function getLatestQuarterStartDate(day) {
    if (!day) {
      day = new Date();
    }
    var month = day.getMonth() - day.getMonth() % 3;
    var quarterStartDate = new Date(day.getFullYear(), month, 1);
    return quarterStartDate;
  }

  function getParamsByFilter(filterType, scope) {
    switch (filterType) {
      case 'cond':
        break;
      case 'all':
        scope.startDate = null;
      scope.endDate = null;
      break;
      case 'today':
        scope.startDate = new Date();
      scope.endDate = null;
      break;
      case 'week':
        scope.startDate = getLatestWeekStartDate();
      scope.endDate = new Date();
      break;
      case 'month':
        scope.startDate = getLatestMonthStartDate();
      scope.endDate = new Date();
      break;
      case 'lastMonth':
        scope.startDate = moment(new Date()).subtract(1,'months').startOf('month').toDate();
      scope.endDate = moment(new Date()).subtract(1,'months').endOf('month').toDate();
      break;
      case 'nextMonth':
        scope.startDate = moment(new Date()).add(1,'months').startOf('month').toDate();
      scope.endDate = moment(new Date()).add(1,'months').endOf('month').toDate();
      break;
      case 'quarter':
        scope.startDate = getLatestQuarterStartDate();
      scope.endDate = new Date();
      break;
      default:
    }
  }

  function setSearchFilterDates(filter) {
    if (!filter.customizedDate) {
      filter.endDate = null;
      switch (filter.dateRange) {
        case 'day':
          filter.startDate = moment().startOf('day').toDate();
        break;
        case 'week':
          filter.startDate = moment().startOf('isoWeek').toDate();
        break;
        case 'month':
          filter.startDate = moment().startOf('month').toDate();
        break;
        case 'quarter':
          filter.startDate = getLatestQuarterStartDate(); // moment().startOf('quarter');
        break;
        default:
          filter.startDate = null;
        filter.endDate = null;
      }
    } else {
      filter.startDate = filter.customStartDate;
      filter.endDate = filter.customEndDate;
    }
  }

  return {
    yuanToServerMoney: yuanToServerMoney,
    serverMoneyToYuan: serverMoneyToYuan,
    getLatestWeekStartDate: getLatestWeekStartDate,
    getLatestMonthStartDate: getLatestMonthStartDate,
    getLatestQuarterStartDate: getLatestQuarterStartDate,
    getParamsByFilter: getParamsByFilter,
    setSearchFilterDates: setSearchFilterDates
  };
}));
