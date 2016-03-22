<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ include file="/common/tag.jsp"%>
<link href="${ctx}/css/style.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/css/font-awesome.min.css" type="text/css" rel="stylesheet" />
<div class="main-container" id="mainFrame">
<%-- <div class="main-content ng-scope" ng-view=""><div class="breadcrumbs ng-scope" id="breadcrumbs">
  <ul class="breadcrumb">
    <li>
      <i class=" fa fa-university home-icon" style="font-size: 18px;"></i>
      代理查询
    </li>
  </ul>
</div>--%>
  <div class="page-content ng-scope">
    <div class="panel panel-default">
      <div class="panel-heading">
        代理列表
      </div>
      <div class="panel-body">
        <div class="form-group row mb15">
          <div class="col-sm-4">
            <div class="input-group">
              <span class="input-group-addon input-group-onlytext">区域：</span>
              <input id="storeInput" type="text" class="form-control dropdown-toggle ng-pristine ng-valid" ng-model="searchFilter.store" placeholder="门店名称 / 门店编号" ng-change="filterSubstores()" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
              <ul class="dropdown-menu" aria-labelledby="storeInput" style="margin-left: 62px; min-width: 200px; min-height: 37px;">
                <!-- ngRepeat: substore in substoreList | orderBy: 'substore_no' | limitTo: 8 track by substore.id -->
              </ul>
            </div>
          </div>
          <div class="col-sm-2">
            <select class="form-control ng-pristine ng-valid" style="width: 90px;" ng-model="searchFilter.status" ng-options="status.id as status.name for status in storeStatus"><option value="" class="">全部状态</option><option value="0">启用</option><option value="1">停用</option></select>
          </div>
        </div>
        <div class="form-group row mb15">
          <div class="col-sm-4">
            <div class="input-group">
              <span class="input-group-addon input-group-onlytext">终端：</span>
              <input id="terminalInput" type="text" class="form-control dropdown-toggle ng-pristine ng-valid" ng-model="searchFilter.terminal" placeholder="终端名称 / 终端编号" ng-change="filterTerminals()" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
              <ul class="dropdown-menu" aria-labelledby="terminalInput" style="margin-left: 62px; min-width: 200px; min-height: 37px;">
                <!-- ngRepeat: terminal in terminalList | orderBy: 'terminal_no' | limitTo: 8 track by terminal.id -->
              </ul>
            </div>
          </div>
          <div>
            <div class="col-sm-2">
              <a class="btn btn-primary" style="width: 90px;" ng-click="getSubstoreRecords()">
                <i class="fa fa-search"></i>
                <span class="btn-text">查询</span>
              </a>
            </div>
          </div>
        </div>
        <!-- <div id="data-table"> -->
        <div class="table-responsive">
          <table class="table table-striped table-bordered table-hover">
            <thead>
            <tr>
              <th>代理名称</th>
              <th>代理描述</th>
              <th>所属区域</th>
              <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:choose>
              <c:when test="${not empty list}">
                <c:forEach var="entity" items="${list}" varStatus="status">
                  <tr>
                    <td>${entity.name}</td>
                    <td>${entity.desc}</td>
                    <td>${entity.areaId}</td>
                    <td>${entity.areaId}</td>
                  </tr>
                </c:forEach>
              </c:when>
              <c:otherwise>
                <tr>
                  <td style="text-align: center;" colspan="18">暂无记录</td>
                </tr>
              </c:otherwise>
            </c:choose>
            </tbody>
          </table>

          <!-- 分页 -->
          <div class="row table-footer">
            <div class="col-sm-2 col-md-2 col-lg-1">
              <select class="form-control ng-pristine ng-valid" ng-model="pageSize" ng-change="pageFlipped()" ng-options="item for item in pageSizes"><option value="0" selected="selected">10</option><option value="1">20</option><option value="2">30</option><option value="3">50</option></select>
            </div>
            <div class="col-sm-8 col-md-7 col-lg-5">
              <div class="wosai-pagination">
                <div class="wosai-control">
                  <div class="btn-group">
                    <a class="btn btn-link" ng-disabled="page == 1" ng-click="page = 1; pageFlipped();" disabled="disabled">
                      <i class="fa fa-fast-backward"></i>
                    </a>
                    <a class="btn btn-link" ng-disabled="page == 1" ng-click="page = (page - 1 > 0 ? page - 1 : 1); pageFlipped();" disabled="disabled">
                      <i class="fa fa-step-backward"></i>
                    </a>
                  </div>
                </div>
                <div class="wosai-page">
                  <div class="input-group" ng-init="targetPage = page">
                    <div class="input-group-addon transparent">
                      第
                    </div>
                    <input type="tel" class="form-control ng-pristine ng-valid" style="min-width: 50px;text-align: center;" ng-model="targetPage" ng-keyup="pageSpecified($event)" ng-value="page" value="1">
                    <div class="input-group-addon transparent ng-binding">
                      页，共0页
                    </div>
                  </div>
                </div>
                <div class="wosai-control">
                  <div class="btn-group">
                    <a class="btn btn-link" ng-disabled="page == maxPage" ng-click="page = (page + 1 < maxPage ? page + 1 : maxPage); pageFlipped();">
                      <i class="fa fa-step-forward"></i>
                    </a>
                    <a class="btn btn-link" ng-disabled="page == maxPage" ng-click="page = maxPage; pageFlipped();">
                      <i class="fa fa-fast-forward"></i>
                    </a>
                  </div>
                </div>
              </div>
            </div>

            <div class="col-sm-2 col-md-3 col-lg-6 text-right">
              <p class="ng-binding">显示0到0，共0记录</p>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
  <div id="loading" style="width: 729px; height: 933px; display: none;"></div>
</div>