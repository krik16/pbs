<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<html lang="zh-CN">
<%@ include file="/common/tag.jsp" %>
<head>
    <link href="${ctx}/css/style.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/css/font-awesome.min.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/css/jquery-ui.min.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/css/summernote.css" type="text/css" rel="stylesheet"/>
    <link href="${ctx}/css/ui.fancytree.css" type="text/css" rel="stylesheet"/>
    <!-- 树状插件css-->
    <link href="${ctx}/css/index.css" type="text/css" rel="stylesheet"/>

    <script src="${ctx}/js/jquery/jquery.js" type="text/javascript"></script>
    <script src="${ctx}/js/jquery/jquery-ui.js" type="text/javascript"></script>
    <!-- 树状插件js-->
    <script src="${ctx}/js/jquery/jquery-ui.custom.js" type="text/javascript"></script>
    <script src="${ctx}/js/jquery/jquery.fancytree.js" type="text/javascript"></script>
    <script src="${ctx}/js/jquery/jquery.nicescroll.js" type="text/javascript"></script>
    <style type="text/css">
        li ul li span.fancytree-title {
            margin-left: 16px;
        }
    </style>
    <script type="text/javascript">
        $(function () {



            // --- Initialize sample trees
            $("#tree").fancytree({
                autoActivate: false, // we use scheduleAction()
                autoCollapse: true,
                //autoFocus: true,
                autoScroll: true,
                clickFolderMode: 3, // expand with single click
                minExpandLevel: 1,
                tabbable: false, // we don't want the focus frame
                // scrollParent: null, // use $container
                focus: function (event, data) {
                    var node = data.node;
                    // Auto-activate focused node after 1 second
                    if (node.data.href) {
                        node.scheduleAction("activate", 1000);
                    }
                },
                blur: function (event, data) {
                    data.node.scheduleAction("cancel");
                },
                activate: function (event, data) {
                    var node = data.node,
                            orgEvent = data.originalEvent;

                    if (node.data.href) {
                        window.open(node.data.href, (orgEvent.ctrlKey || orgEvent.metaKey) ? "_blank" : node.data.target);
                    }
                },
                click: function (event, data) { // allow re-loads
                    var node = data.node,
                            orgEvent = data.originalEvent;

                    if (node.isActive() && node.data.href) {
                        // data.tree.reactivate();
                        window.open(node.data.href, (orgEvent.ctrlKey || orgEvent.metaKey) ? "_blank" : node.data.target);
                    }
                }
            });

            /* $(".fancytree-title").eq(1).addClass("titletop"); */
            $("span.fancytree-node").click(function () {
                $("span.fancytree-title").attr("style", "color:black;");
                $(this).find(".fancytree-title").attr("style", "color:#1ca822;");
            });
        });
    </script>

</head>
<body>
<div id="tree" class="left">
    <ul class="nav nav-list">
        <sec:authorize ifAnyGranted="COMPANY_SHAREHOLDER,AREA_AGENT,DISTRIBUTION_AGENT">
            <li class="folder"><span class="menu-text">公司管理</span>
                <ul>
                    <sec:authorize ifAnyGranted="COMPANY_SHAREHOLDER">
                        <li><a href="${ctx}/area/search" target="mainFrame">区域管理</a></li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="COMPANY_SHAREHOLDER,AREA_AGENT">
                        <li><a href="${ctx}/agent/search" target="mainFrame">代理管理</a></li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="COMPANY_SHAREHOLDER,AREA_AGENT,DISTRIBUTION_AGENT">
                        <li><a href="${ctx}/user/search" target="mainFrame">账号管理</a></li>
                    </sec:authorize>
                    <sec:authorize ifAnyGranted="COMPANY_SHAREHOLDER,AREA_AGENT,DISTRIBUTION_AGENT">
                        <li><a href="${ctx}/bill/search" target="mainFrame">交易查询</a></li>
                    </sec:authorize>
                </ul>
            </li>
        </sec:authorize>
        <sec:authorize
                ifAnyGranted="COMPANY_SHAREHOLDER,DISTRIBUTION_AGENT,MCH_COMPANY,MCH_SUB_COMPANY,MCH_SHOPKEEPER">
        <li class="folder expanded">商家服务
            <ul>
                <sec:authorize ifAnyGranted="COMPANY_SHAREHOLDER,DISTRIBUTION_AGENT,MCH_COMPANY">
                    <li><a href="${ctx}/mchCompany/search" target="mainFrame">公司管理</a></li>
                </sec:authorize>
                <sec:authorize ifAnyGranted="COMPANY_SHAREHOLDER,MCH_COMPANY,MCH_SUB_COMPANY">
                <li><a href="${ctx}/mchSubCompany/search" target="mainFrame">分公司管理</a></li>
                </sec:authorize>
                <sec:authorize ifAnyGranted="COMPANY_SHAREHOLDER,DISTRIBUTION_AGENT,MCH_COMPANY,MCH_SUB_COMPANY,MCH_SHOPKEEPER">
                <li><a href="${ctx}/mchShop/search" target="mainFrame">门店管理</a></li>
                </sec:authorize>
                <sec:authorize ifAnyGranted="COMPANY_SHAREHOLDER,DISTRIBUTION_AGENT,MCH_COMPANY,MCH_SUB_COMPANY,MCH_SHOPKEEPER">
                    <li><a href="${ctx}/mchUser/search" target="mainFrame">用户管理</a></li>
                </sec:authorize>
                <sec:authorize ifAnyGranted="COMPANY_SHAREHOLDER,DISTRIBUTION_AGENT,MCH_COMPANY,MCH_SUB_COMPANY,MCH_SHOPKEEPER,MCH_CASHIER">
                    <li><a href="${ctx}/mchBill/search" target="mainFrame">交易查询</a></li>
                </sec:authorize>
            </ul>
            </sec:authorize>
        </li>
    </ul>
</div>
<div class="main-content" ng-view>

</div>
<div id="loading"></div>
</div>
</body>
