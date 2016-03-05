var eventActionUtil = eventActionUtil || {};

/**
 * 对有有自定义属性（clickAction）的元素绑定点击事件
 * 例：<a clickAction="save">保存</a> 将被调用save方法
 *    <button clickAction="delete">删除</button>
 *
 * 并且给点击事件确定命名空间是click.action
 *
 * @param pageObj 当前页面js对象（不为空）
 */
eventActionUtil.clickAction = function (pageObj) {
    $(document).on("click.action", "[clickAction]", function (event) {
        var $this = $(this), methodName = $this.attr("clickAction"), method = pageObj[methodName];
        if (method) {
            method.apply(pageObj, [event]);
        } else {
            alert(" 未找到定义的 " + methodName + " 方法！");
            throw new Error(0, " 未找到定义的 " + methodName + " 方法！");
        }
    });
};

eventActionUtil.changeAction = function (pageObj) {
    $(document).on("change.action", "[changeAction]", function (event) {
        var $this = $(this), methodName = $this.attr("changeAction"), method = pageObj[methodName];
        if (method) {
            method.apply(pageObj, [event]);
        } else {
            alert(" 未找到定义的 " + methodName + " 方法！");
            throw new Error(0, " 未找到定义的 " + methodName + " 方法！");
        }
    });
};
