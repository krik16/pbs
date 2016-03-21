/**
 * User: mark <mark@wosai-inc>
 * Created at: 10/30/13 4:24 PM
 */
document.domain = DOMAIN;
angular.element(document).ready(function() {
    $(function() {
        $('body').height($(document).height());
        $('.login-wrap').removeClass('login-loading');
    });
    angular.module('login', [])
        .config(['$httpProvider', function($httpProvider) {
            $httpProvider.defaults.transformRequest = function(data) {
                if (data === undefined) {
                    return data;
                }
                return $.param(data);
            }
            $httpProvider.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded; charset=UTF-8';
            $httpProvider.defaults.withCredentials = true;
        }]);
    var login = angular.module('login');
    angular.bootstrap(document, ['login']);
    showErrorTips();
});

function loginController($scope, $http) {
    $scope.needCaptcha = false;
    $scope.captchaCodeError = false;
    $scope.type = "login";

    function loginAct (path, data, successCallback){
        $http.post(path, data).success(successCallback).error(function(data, status, headers, config) {
            window.location.href = '/error/login.html';
            //alert('服务器网络从架子上掉了下来，网管正在捡起来，如果长时间无法恢复，请在工作时间联系021-61451590或关注微信公众号Wosai-Inc反馈问题。');
        });
    }
    //换二维码
    $scope.changeAuthcode = function() {
        $http.get(CGI_PATH + 'GetCaptcha').success(function(json) {
            if (json.code == RESPONDCODE.SUCCESS) {
                $scope.needCaptcha = true;
                $scope.captchaImg = json.data.src;
            }
        });
    };


    $scope.login = function() {

        var path = CGI_PATH + 'Login';
        var data = {
            username: $scope.username,
            password: $scope.password
        };
        if($.trim($scope.username) == ""){
            $scope.usernameError = true;
            return false;
        }else{
            $scope.usernameError = false;
        }
        if($.trim($scope.password) == ""){
            $scope.pwdError = true;
            return false;
        }else{
            $scope.pwdError = false;
        }
        var successCallback = function(json) {
            var code = json.code;
            if (code == RESPONDCODE.SUCCESS) {
                window.location.href = '/cms';
                return;
            } else if (code == 20017) {
                $http.get(CGI_PATH + 'GetCaptcha').success(function(json) {
                    if (json.code == RESPONDCODE.SUCCESS) {
                        $scope.pwdError = false;
                        $scope.password = null;
                        $scope.needCaptcha = true;
                        $scope.captchaImg = json.data.src;
                    }
                });
                $('.form-tips').show();
                $scope.loginForm.password.$error.accountError = true;
                $scope.captchaCodeError = false;
                return;
            } else {
                window.location.href = '/error/login.html';
                //alert('服务器网络从架子上掉了下来，网管正在捡起来，如果长时间无法恢复，请联系186-6666-9551');
            }
        }

        if(!$scope.needCaptcha){
            loginAct(path, data, successCallback);
        }else{
            var code_path = CGI_PATH + 'VerifyCaptcha';
            var code_data = {
                code: $scope.captchaCode
            };
            $http.post(code_path, code_data).success(function(json){
                if (json.code == RESPONDCODE.SUCCESS){
                    loginAct(path, data, successCallback);
                }else{
                    //验证失败换张图片
                    $http.get(CGI_PATH + 'GetCaptcha').success(function(json) {
                        if (json.code == RESPONDCODE.SUCCESS) {
                            $scope.captchaImg = json.data.src;
                        }
                    });
                    $scope.loginForm.password.$error.accountError = false;
                    $scope.captchaCodeError = true;
                }
            });
        }

    };
};
