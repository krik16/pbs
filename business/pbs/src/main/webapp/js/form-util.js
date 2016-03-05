;
(function ($) {
    "use strict";
    /** core ******************************************************************** */
    $.jForm = $.jForm || {};
    // 默认配置
    var defaults = {
        //回调
        callback: {
            afterGetValue: null,
        }
    };
    $.fn.jqForm = function (config) {
        if (typeof config == 'string') {
            var fn = $.jForm.getAccessor($.jForm, config);
            if (!fn) {
                alert("$.jForm - 未找到匹配的方法 : " + config);
                return;
            }
            var args = $.makeArray(arguments).slice(1);
            return fn.apply(this, args);
        }
        return this.each(function () {
            /**
             * 防止重复渲染
             */
            if (this.renderCompleted) {
                return;
            }
            var _formElm = this;
            /**
             * 判定非form 控件的渲染
             */
            if ($(_formElm).is("form") !== true) {
                alert("非form 控件不能初始化!");
                return;
            }
            /**
             * 配置继承
             */
            _formElm.p = $.extend({}, {
                id: _formElm.id
            }, $.jForm);
            // 继承
            _formElm.p = $.extend(true, {}, defaults, config);
            // 渲染完成
            _formElm.renderCompleted = true;
        });
    };
    /** base begin ******************************************************************** */
    $.extend($.jForm, {
        /**
         * 对象属性访问器 如： obj = {"a":1,"b":2} expr = "a" 即返回 1
         */
        getAccessor: function (obj, expr) {
            var ret, p, prm = [], i;
            if (typeof expr === 'function') {
                return expr(obj);
            }
            ret = obj[expr];
            if (ret === undefined) {
                try {
                    if (typeof expr === 'string') {
                        prm = expr.split('.');
                    }
                    i = prm.length;
                    if (i) {
                        ret = obj;
                        while (ret && i--) {
                            p = prm.shift();
                            ret = ret[p];
                        }
                    }
                } catch (e) {
                }
            }
            return ret;
        }
    });

    /** 接口方法 begin ******************************************************************** */
    $.extend($.jForm, {
        /**
         * 获取表单值
         * @param callback （可空）回调function对象
         * @returns {{}}
         */
        getValue: function (callback) {
            var formData = {};
            this.each(function () {
                var _formElm = this, p = _formElm.p, value = null, name = null, $elm = null, $multiEle = null;
                //对表单控件，逐个取值
                $("[name]", _formElm).each(function () {
                    $elm = $(this);
                    name = $elm.attr("name");
                    $multiEle = $("[name=" + $elm.attr("name") + "]", _formElm);
                    //
                    if ($elm.is("input[type=radio]")) {
                        value = $("input[name=" + name + "]:checked", _formElm).val();
                    } else if ($elm.is("input[type=checkbox]") && $multiEle.length == 1) {
                        value = $("input[name=" + name + "]:checked", _formElm).val();
                    } else if ($multiEle.length > 1) {
                        //checkbox
                        value = [];
                        $multiEle.each(function () {
                            var $checkboxItemElm = $(this);
                            if ($checkboxItemElm.is("input[type=checkbox]")) {
                                if ($checkboxItemElm.prop("checked")) {
                                    value.push($.trim($checkboxItemElm.val()) === "" ? null : $checkboxItemElm.val());
                                }
                            }
                        });
                    }
                    else if ($elm.is("input") || $elm.is("textarea") || $elm.is("select")) {
                        value = $elm.val();
                    } else {
                        value = $elm.text();
                    }
                    formData[name] = $.trim(value) === "" ? null : value;
                    $multiEle = null;
                });
                //回调
                if ($.isFunction(p.callback.afterGetValue)) {
                    p.callback.afterGetValue.call(_formElm, formData);
                }
                //回调
                if (callback && $.isFunction(callback)) {
                    callback.apply(_formElm, [formData]);
                }
            });
            return formData;
        },
        
        /**
         * 焦点定位到表单元素
         * @param name
         * @returns {*}
         */
        focusToElement: function (name) {
            return this.each(function () {
                var _formElm = this, $form = $(_formElm);
                $("[name='" + name + "']", $form).focus();
            });
        },
    });
})(jQuery);