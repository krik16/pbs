<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="common/tag.jsp"%>
<!-- 分页 -->
<div class="row table-footer">
<%--	<div class="col-sm-2 col-md-2 col-lg-1">
		<select class="form-control ng-pristine ng-valid" ng-model="pageSize" ng-change="pageFlipped()" ng-options="item for item in pageSizes"><option value="0" selected="selected">10</option><option value="1">20</option><option value="2">30</option><option value="3">50</option></select>
	</div>--%>
	<div class="col-sm-8 col-md-7 col-lg-5">
		<div class="wosai-pagination">
			<div class="wosai-control">
				<div class="btn-group">
					<a class="btn btn-link" id="fastPage" clickAction="fastPage">
						<i class="fa fa-fast-backward"></i>
					</a>
					<a class="btn btn-link" id="downPage" clickAction="downPage">
						<i class="fa fa-step-backward"></i>
					</a>
				</div>
			</div>
			<div class="wosai-page">
				<div class="input-group" ng-init="targetPage = page">
					<div class="input-group-addon transparent">
						第
					</div>
					<input type="tel" class="form-control ng-pristine ng-valid" style="min-width: 50px;text-align: center;"  value="${currpage}">
					<div class="input-group-addon transparent ng-binding">
						页，共<c:out value="${rowCount}"/> 页
					</div>
				</div>
			</div>
			<div class="wosai-control">
				<div class="btn-group">
					<a class="btn btn-link" id="upPage">
						<i class="fa fa-step-forward"></i>
					</a>
					<a class="btn btn-link" id="lastPage" clickAction="lastPage">
						<i class="fa fa-fast-forward"></i>
					</a>
				</div>
			</div>
		</div>
	</div>
	<div class="col-sm-2 col-md-5 col-lg-7 text-right">
		<p class="ng-binding">共<c:out value="${totalCount}"/>记录</p>
	</div>
</div>