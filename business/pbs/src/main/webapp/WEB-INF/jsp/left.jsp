<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%><html lang="zh-CN">
<%@ include file="/common/tag.jsp"%>
<head>
  <title>Wosai 智慧的商业</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>
  <meta name="description" content="wosai,喔噻,智慧的商业" />
  <meta name="renderer" content="webkit">
  <link href="${ctx}/css/style.css" type="text/css" rel="stylesheet" />
  <link href="${ctx}/css/font-awesome.min.css" type="text/css" rel="stylesheet" />
  <link href="${ctx}/css/fileuploader.css" type="text/css" rel="stylesheet" />
  <link href="${ctx}/css/ng-tags-input.bootstrap.min.css" type="text/css" rel="stylesheet" />
  <link href="${ctx}/css/ng-tags-input.min.css" type="text/css" rel="stylesheet" />
  <link href="${ctx}/css/jquery-ui.min.css" type="text/css" rel="stylesheet" />
  <link href="${ctx}/css/summernote.css" type="text/css" rel="stylesheet" />
  <link href="${ctx}/css/ui.fancytree.css" type="text/css" rel="stylesheet" />     <!-- 树状插件css-->
  <link href="${ctx}/css/index.css" type="text/css" rel="stylesheet" />

  <script src="${ctx}/js/jquery/jquery.js" type="text/javascript"></script>
  <script src="${ctx}/js/jquery/jquery-ui.js" type="text/javascript"></script>
  <!-- 树状插件js-->
  <script src="${ctx}/js/jquery/jquery-ui.custom.js" type="text/javascript"></script>
  <script src="${ctx}/js/jquery/jquery.fancytree.js" type="text/javascript"></script>
  <script src="${ctx}/js/jquery/jquery.nicescroll.js" type="text/javascript"></script>
  <style type="text/css">
    li ul li span.fancytree-title{
      margin-left: 16px;
    }
  </style>
  <script type="text/javascript">
    $(function(){



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
        focus: function(event, data) {
          var node = data.node;
          // Auto-activate focused node after 1 second
          if(node.data.href){
            node.scheduleAction("activate", 1000);
          }
        },
        blur: function(event, data) {
          data.node.scheduleAction("cancel");
        },
        activate: function(event, data){
          var node = data.node,
                  orgEvent = data.originalEvent;

          if(node.data.href){
            window.open(node.data.href, (orgEvent.ctrlKey || orgEvent.metaKey) ? "_blank" : node.data.target);
          }
        },
        click: function(event, data){ // allow re-loads
          var node = data.node,
                  orgEvent = data.originalEvent;

          if(node.isActive() && node.data.href){
            // data.tree.reactivate();
            window.open(node.data.href, (orgEvent.ctrlKey || orgEvent.metaKey) ? "_blank" : node.data.target);
          }
        }
      });

      /* $(".fancytree-title").eq(1).addClass("titletop"); */
      $("span.fancytree-node").click(function(){
        $("span.fancytree-title").attr("style","color:black;");
        $(this).find(".fancytree-title").attr("style","color:#1ca822;");
      });
    });
  </script>

</head>
<body>
  <div id="tree" class="left">
    <ul class="nav nav-list">
      <li class="folder expanded"><span class="menu-text">订单管理</span>
        <ul>
          <li><a href="${ctx}/main/common" target="mainFrame">商品母订单</a></li>
          <li><a href="${ctx}/orderManager/search?module=order" target="mainFrame">商品子订单</a></li>
          <li><a href="${ctx}/couponOrder/search?currpage=1" target="mainFrame">优惠券订单</a></li>
        </ul>
      </li>
      <li class="folder expanded">商家服务
        <ul>
          <li><a href="${ctx}/bs/search?module=merchant" target="mainFrame">提现审核</a></li>
          <li><a href="${ctx}/sc/search?module=merchant" target="mainFrame">返佣审核</a></li>
        </ul>
      </li>
      <sec:authorize ifAnyGranted="TMS_TRADE_VIEW,TMS_DRAW_VIEW" >
        <li class="folder expanded">对账管理
          <ul>
            <sec:authorize ifAnyGranted="TMS_TRADE_VIEW" >
              <li><a href="${ctx}/tradeDetail/search?currpage=1" target="mainFrame">交易明细</a></li>
            </sec:authorize>
            <sec:authorize ifAnyGranted="TMS_DRAW_VIEW" >
              <li><a href="${ctx}/drawDetail/search" target="mainFrame">提现明细</a></li>
            </sec:authorize>
            <sec:authorize ifAnyGranted="TMS_TRADE_VIEW" >
              <li><a href="${ctx}/statementDetail/search?currpage=1" target="mainFrame">结算明细</a></li>
            </sec:authorize>
          </ul>
        </li>
      </sec:authorize>
      <sec:authorize ifAnyGranted="TMS_F_BS_VIEW,TMS_F_SC_VIEW,TMS_F_EXM_VIEW,TMS_F_AB_VIEW,TMS_F_PAY_VIEW" >
        <li class="folder expanded">财务管理
          <ul>
            <sec:authorize ifAnyGranted="TMS_F_BS_VIEW" ><li><a href="${ctx}/bs/search?module=finance" target="mainFrame">提现审核</a></li></sec:authorize>
            <sec:authorize ifAnyGranted="TMS_F_SC_VIEW" ><li><a href="${ctx}/sc/search?module=finance" target="mainFrame">返佣审核</a></li></sec:authorize>
            <sec:authorize ifAnyGranted="TMS_F_EXM_VIEW" ><li><a href="${ctx}/bonus/search" target="mainFrame">考核奖金</a></li></sec:authorize>
            <sec:authorize ifAnyGranted="TMS_F_AB_VIEW" ><li><a href="${ctx}/ab/search" target="mainFrame">异常账务</a></li></sec:authorize>
            <sec:authorize ifAnyGranted="TMS_F_PAY_VIEW" ><li><a href="${ctx}/pay/search?currpage=1" target="mainFrame">付款</a></li></sec:authorize>
              <%-- 	<sec:authorize ifAnyGranted="TMS_F_PAY_VIEW" ><li><a href="${ctx}/tradeDetail/queryOrder?currpage=1" target="mainFrame">订单查询</a></li></sec:authorize> --%>
          </ul>
        </li>
      </sec:authorize>

      <sec:authorize ifAnyGranted="TMS_F_BS_VIEW,TMS_F_SC_VIEW,TMS_F_EXM_VIEW,TMS_F_AB_VIEW,TMS_F_PAY_VIEW" >
        <li class="folder expanded">防作弊管理
          <ul>
            <sec:authorize ifAnyGranted="TMS_F_BS_VIEW" ><li><a href="${ctx}/accountBlack/search" target="mainFrame">黑名单</a></li></sec:authorize>
          </ul>
        </li>
      </sec:authorize>
    </ul>
  </div>
<div class="main-content"  ng-view>

</div>
<div id="loading"></div>
</div>

<!-- 全局弹窗 -->
<div class="cms-modal modal" ng-controller="modalController" data-backdrop="static">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" ng-click="close()"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title">{{title || '　'}}</h4>
      </div>
      <div class="modal-body" ng-bind-html="body"></div>
      <div class="modal-footer" ng-hide = "notNeeded">
        <button type="button" class="btn btn-default" data-dismiss="modal" ng-show="closeText" ng-click="close()">{{closeText}}</button>
        <button type="button" class="btn btn-primary" data-dismiss="modal" ng-click="apply()">{{applyText || '确定'}}</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<!-- /全局弹窗 -->
<!-- alert提示 -->
<div class="js-alertpop alertpop">
  <div class="alert">
    <button type="button" class="close" aria-label="Close"><span aria-hidden="true">&times;</span></button>
    <strong></strong>
  </div>
</div>
</body>
