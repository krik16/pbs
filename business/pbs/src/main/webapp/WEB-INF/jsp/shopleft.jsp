<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>容易移动商品管理系统</title>
<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery-ui.custom.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.fancytree.js" type="text/javascript"></script>
<link href="${ctx}/css/ui.fancytree.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/index.css" type="text/css" rel="stylesheet" />
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
				$("span.fancytree-node").click(function(){
					$("span.fancytree-title").attr("style","color:black;");
					$(this).find(".fancytree-title").attr("style","color:#1ca822;");
				})
				if(data.node.getLevel() == 2){
					$("span.fancytree-title").attr("style","color:black;");
					var obj = event.srcElement ? event.srcElement : data.node.li;
					var clickObj = $(obj).find(".fancytree-title");
					if(clickObj.length == 0){
						obj = $(obj).parent();
					}
					if($(obj).find(".fancytree-title").html() == null){
						obj.style.color = "#1ca822";
					}else{
						$(obj).find(".fancytree-title").attr("style","color:#1ca822;");
					}
				}
			}
		});
		$("span.fancytree-node").click(function(){
			$("span.fancytree-title").attr("style","color:black;");
			$(this).find(".fancytree-title").attr("style","color:#1ca822;");
		})
	});
</script>
</head>

<body>
<div class="left">
	<div id="tree">
		<ul>
			<li class="folder expanded"><a href="${ctx}/main/home" target="mainFrame">首页</a></li>
			<li class="folder">商品管理
				<ul>
					<li><a href="${ctx}/commodity/shopindex?currpage=1" target="mainFrame">商品信息</a></li>
				</ul>
			</li>
			<ul>
				<li></li>
			</ul>
		</ul>
	</div>
</div>
</body>
</html>
