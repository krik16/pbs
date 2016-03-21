var pkg = {
  "name": "wosaimg",
  "version": "0.1.0",
  "devDependencies": {
    "grunt": "~0.4.1",
    "grunt-contrib-jshint": "~0.6.3",
    "grunt-contrib-nodeunit": "~0.2.0",
    "grunt-contrib-uglify": "~0.2.2"
  },
  "iwosai": {
    "cms": {
      "version": "1.0.0"
    },
    "login": {
      "version": "20140408"
    },
    "register": {
      "version": "20140128"
    }
  }
};
/**
 * User: mark <mark@wosai-inc>
 * Created at: 9/27/13 5:02 PM
 */
var CDN = {
  domain: 'http://msp.wosaimg.com',
  jsPath: '/js',
  cssPath: '/css',
  imgPath: '/img'
};

var DOMAIN = 'shouqianba.com';
//var CGI_DOMAIN = 'http://api.isv.dev.wosai.cn/';
var CGI_DOMAIN = 'https://upay-web-backend.shouqianba.com/';
var CGI_PATH = CGI_DOMAIN + 'RS/';
var IMG_PATH = 'http://media.wosai.cn';
var RESPONDCODE = {
  SUCCESS : 10000,
  SERVER_ERROR: 1111,
  AUTH_FAILED: 11000
};

var JS_PATH = CDN.domain + CDN.jsPath;

var LIBS = {
  async: {
    path: 'async',
    version: '0.2.10',
    filename: 'async'
  },
  angular: {
    path: 'angular',
    version: '1.2.16',
    filename: 'angular'
  },
  'angular-resource': {
    path: 'angular',
    version: '1.2.16',
    filename: 'angular-resource'
  },
  'angular-route': {
    path: 'angular',
    version: '1.2.16',
    filename: 'angular-route'
  },
  'angular-cookies': {
    path: 'angular',
    version: '1.2.16',
    filename: 'angular-cookies'
  },
  'angular-animate': {
    path: 'angular',
    version: '1.2.16',
    filename: 'angular-animate'
  },
  'angular-sanitize': {
    path: 'angular',
    version: '1.2.16',
    filename: 'angular-sanitize'
  },
  'angular-file-uploader': {
    path: 'angular/plugin/file-uploader',
    version: '1.4.0',
    filename: 'angular-file-uploader'
  },
  'angular-ui': {
    path: 'angular/plugin/ui',
    version: '0.11.0',
    filename: 'angular-ui'
  },
  'ui-sortable': {
    path: 'angular/plugin/ui-sortable',
    version: '0.13.0',
    filename: 'sortable'
  },
  'ui-router': {
    path: 'angular/plugin/ui-router',
    version: '0.2.11',
    filename: 'router'
  },
  'angular-strap': {
    path: 'angular/plugin/angular-strap',
    version: '2.0.2',
    filename: 'angular-strap'
  },
  'ng-tags-input': {
    path: 'angular/plugin/ng-tags-input',
    version: '2.1.1',
    filename: 'ng-tags-input'
  },
  'md5': {
    path: 'md5',
    version: '1.0.1',
    filename: 'md5'
  },
  'bootstrap': {
    path: 'bootstrap',
    version: '3.0.3',
    filename: 'bootstrap'
  },
  'bootstrap-wysiwyg': {
    path: 'bootstrap/plugin/wysiwyg',
    version: '1.0.1',
    filename: 'wysiwyg'
  },
  'bootstrap-select': {
    path: 'bootstrap/plugin/select',
    version: '1.0.0',
    filename: 'bootstrap-select'
  },
  'bootstrap-datepicker': {
    path: 'bootstrap/plugin/datepicker',
    version: '1.0.0',
    filename: 'datepicker'
  },
  'bootstrap-switch': {
    path: 'bootstrap/plugin/switch',
    version: '2.0.1',
    filename: 'bootstrap-switch'
  },
  'bootstrap-tag': {
    path: 'bootstrap/plugin/tag',
    version: '2.3.0',
    filename: 'bootstrap-tag'
  },
  'chart': {
    path: 'chart',
    version: '1.0.0',
    filename: 'chart'
  },
  LABjs: {
    path: 'LABjs',
    version: '2.0.3',
    filename: 'LAB'
  },
  moment: {
    path: 'moment',
    version: '2.4.0',
    filename: 'moment'
  },
  jQuery: {
    path: 'jQuery',
    version: '1.9.1',
    filename: 'jquery'
  },
  'jquery-ui': {
    path: 'jQuery/plugin/jquery-ui',
    version: '1.11.2',
    filename: 'jquery-ui'
  },
  'cityData': {
    path: 'meta/city',
    version: '0.0.2',
    filename: 'city'
  },
  'jQuery.cookie': {
    path: 'jQuery/plugin/cookie',
    version: '1.3.1',
    filename: 'cookie'
  },
  'jQuery.form': {
    path: 'jQuery/plugin/form',
    version: '3.40.0',
    filename: 'form'
  },
  'jQuery.hotkeys': {
    path: 'jQuery/plugin/hotkey',
    version: '0.8.0',
    filename: 'hotkey'
  },
  'jQuery.loadmask': {
    path: 'jQuery/plugin/loadmask',
    version: '0.4.0',
    filename: 'loadmask'
  },
  'underscore': {
    path: 'underscore',
    version: '1.5.2',
    filename: 'underscore'
  },
  'utils.xls': {
    path: 'utils/xls',
    version: '1.0.1',
    filename: 'xls'
  },
  'utils.pinyin': {
    path: 'utils/pinyin',
    version: '2.1.1',
    filename: 'pinyin'
  },
  'summernote': {
    path: 'summernote',
    version: '0.5.9',
    filename: 'summernote'
  },
  'md5':{
    path: 'md5',
    version:'1.0.1',
    filename:'md5'
  }
};

var LIB_ROOT = JS_PATH + '/lib/build/{path}/{version}/{filename}.min.js';
var isDebug = (document.cookie.indexOf('debug=true') !== -1);
if (isDebug) {
  LIB_ROOT = JS_PATH + '/lib/src/{path}/{version}/{filename}.js';
}
for (var libName in LIBS) {
  var lib = LIBS[libName];
  libPath = LIB_ROOT.replace(/\{path\}/, lib.path)
  .replace(/\{version\}/, lib.version)
  .replace(/\{filename\}/, lib.filename);
  LIBS[libName] = libPath;
}

//pkg is build from package.json
var IWOSAI = {
  REGISTER: JS_PATH + '/iwosai/register/register.js?1456915558816',
  LOGIN: JS_PATH + '/iwosai/login/login.js?1456915558816',
  PASSWORD: JS_PATH + '/iwosai/password/password.js?1456915558816',
  CMS: JS_PATH + '/iwosai/cms/script.js?1456915558816'
};

var GUIDE = JS_PATH + '/iwosai/cms/guide.js?1456915558816';

var COMMON = {
  ERRORCODE: 'http://api.wosai.cn/static/code.js'
};

document.write('<script src="' + LIBS.LABjs + '"></script>');

function showErrorTips() {
  $('.error-tips').removeClass('hide-tips');
};
