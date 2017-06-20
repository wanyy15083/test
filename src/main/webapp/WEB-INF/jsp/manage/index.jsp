<%@ page contentType="text/html; charset=utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<c:set var="basePath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE HTML>
<html lang="zh-cn">
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>后台管理系统</title>

	<link href="${basePath}/resources/static/plugins/bootstrap-3.3.0/css/bootstrap.min.css" rel="stylesheet"/>
	<link href="${basePath}/resources/static/plugins/material-design-iconic-font-2.2.0/css/material-design-iconic-font.min.css" rel="stylesheet"/>
	<link href="${basePath}/resources/static/plugins/waves-0.7.5/waves.min.css" rel="stylesheet"/>
	<link href="${basePath}/resources/static/plugins/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css" rel="stylesheet"/>
	<link href="${basePath}/resources/static/css/admin.css" rel="stylesheet"/>
	<style>
		/** skins **/
		#test #header {background: #29A176;}
		#test .content_tab{background: #29A176;}
		#test .s-profile>a{background: url(images/zheng-upms.png) left top no-repeat;}
	</style>
</head>
<body>
<header id="header">
	<ul id="menu">
		<li id="guide" class="line-trigger">
			<div class="line-wrap">
				<div class="line top"></div>
				<div class="line center"></div>
				<div class="line bottom"></div>
			</div>
		</li>
		<li id="logo" class="hidden-xs">
			<a href="${basePath}/manage/index">
				<img src="${basePath}/resources/static/images/logo.png"/>
			</a>
			<span id="system_title">后台管理系统</span>
		</li>
		<li class="pull-right">
			<ul class="hi-menu">
				<!-- 搜索 -->
				<li class="dropdown">
					<a class="waves-effect waves-light" data-toggle="dropdown" href="javascript:;">
						<i class="him-icon zmdi zmdi-search"></i>
					</a>
					<ul class="dropdown-menu dm-icon pull-right">
						<form id="search-form" class="form-inline">
							<div class="input-group">
								<input id="keywords" type="text" name="keywords" class="form-control" placeholder="搜索"/>
								<div class="input-group-btn">
									<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span></button>
								</div>
							</div>
						</form>
					</ul>
				</li>
				<li class="dropdown">
					<a class="waves-effect waves-light" data-toggle="dropdown" href="javascript:;">
						<i class="him-icon zmdi zmdi-dropbox"></i>
					</a>
					<ul class="dropdown-menu dm-icon pull-right">
						<li class="skin-switch hidden-xs">
							请选择系统切换
						</li>
						<li class="divider hidden-xs"></li>
						<li class="divider"></li>
						<li>
							<a class="waves-effect switch-systems" href="javascript:;" systemid="1" systemname="test" systemtitle="后台管理系统"><i class="zmdi zmdi-shield-security"></i> 后台管理系统</a>
						</li>

						<%--<li>--%>
							<%--<a class="waves-effect switch-systems" href="javascript:;" systemid="2" systemname="zheng-cms-admin" systemtitle="内容管理系统"><i class="zmdi zmdi-wikipedia"></i> 内容管理系统</a>--%>
						<%--</li>--%>
					</ul>
				</li>
				<li class="dropdown">
					<a class="waves-effect waves-light" data-toggle="dropdown" href="javascript:;">
						<i class="him-icon zmdi zmdi-more-vert"></i>
					</a>
					<ul class="dropdown-menu dm-icon pull-right">
						<li class="hidden-xs">
							<a class="waves-effect" data-ma-action="fullscreen" href="javascript:fullPage();"><i class="zmdi zmdi-fullscreen"></i> 全屏模式</a>
						</li>
						<li>
							<a class="waves-effect" data-ma-action="clear-localstorage" href="javascript:;"><i class="zmdi zmdi-delete"></i> 清除缓存</a>
						</li>
						<li>
							<a class="waves-effect" href="javascript:;"><i class="zmdi zmdi-face"></i> 隐私管理</a>
						</li>
						<li>
							<a class="waves-effect" href="javascript:;"><i class="zmdi zmdi-settings"></i> 系统设置</a>
						</li>
						<li>
							<a class="waves-effect" href="${basePath}/sso/logout"><i class="zmdi zmdi-run"></i> 退出登录</a>
						</li>
					</ul>
				</li>
			</ul>
		</li>
	</ul>
</header>
<section id="main">
	<!-- 左侧导航区 -->
	<aside id="sidebar">
		<!-- 个人资料区 -->
		<div class="s-profile">
			<a class="waves-effect waves-light" href="javascript:;">
				<div class="sp-pic">
					<img src="${basePath}/resources/static/images/avatar.jpg"/>
				</div>
				<div class="sp-info">
					管理员，您好！
					<i class="zmdi zmdi-caret-down"></i>
				</div>
			</a>
			<ul class="main-menu">
				<li>
					<a class="waves-effect" href="javascript:;"><i class="zmdi zmdi-account"></i> 个人资料</a>
				</li>
				<li>
					<a class="waves-effect" href="javascript:;"><i class="zmdi zmdi-face"></i> 隐私管理</a>
				</li>
				<li>
					<a class="waves-effect" href="javascript:;"><i class="zmdi zmdi-settings"></i> 系统设置</a>
				</li>
				<li>
					<a class="waves-effect" href="${basePath}/sso/logout"><i class="zmdi zmdi-run"></i> 退出登录</a>
				</li>
			</ul>
		</div>
		<!-- /个人资料区 -->
		<!-- 菜单区 -->
		<ul class="main-menu">
			<li>
				<a class="waves-effect" href="javascript:Tab.addTab('首页', 'home');"><i class="zmdi zmdi-home"></i> 首页</a>
			</li>
            <li class="sub-menu system_menus system_1 0">
                <a class="waves-effect" href="javascript:;"><i class="zmdi zmdi-accounts-list"></i> 系统管理</a>
                <ul>
                    <li><a class="waves-effect" href="javascript:Tab.addTab('1', '1');">1</a></li>
                    <li><a class="waves-effect" href="javascript:Tab.addTab('2', '${basePath}/user/find');">2</a></li>
                </ul>
            </li>
            <li class="sub-menu system_menus system_1 1">
                <a class="waves-effect" href="javascript:;"><i class="zmdi zmdi-accounts"></i>用户管理</a>
                <ul>
                    <li><a class="waves-effect" href="javascript:Tab.addTab('角色管理', '/manage/role/index');">角色管理</a></li>
                    <li><a class="waves-effect" href="javascript:Tab.addTab('用户管理', '/manage/user/index');">用户管理</a></li>
                </ul>
            </li>
            <li class="sub-menu system_menus system_1 2">
                <a class="waves-effect" href="javascript:;"><i class="zmdi zmdi-more"></i> 其他数据管理</a>
                <ul>
                    <li><a class="waves-effect" href="javascript:Tab.addTab('会话管理', '/manage/session/index');">会话管理</a></li>
                    <li><a class="waves-effect" href="javascript:Tab.addTab('日志记录', '/manage/log/index');">日志记录</a></li>
                </ul>
            </li>
			<li>
				<div class="upms-version">&copy; TEST-SYSTEM V1.0.0</div>
			</li>
		</ul>
		<!-- /菜单区 -->
	</aside>
	<!-- /左侧导航区 -->
	<section id="content">
		<div class="content_tab">
			<div class="tab_left">
				<a class="waves-effect waves-light" href="javascript:;"><i class="zmdi zmdi-chevron-left"></i></a>
			</div>
			<div class="tab_right">
				<a class="waves-effect waves-light" href="javascript:;"><i class="zmdi zmdi-chevron-right"></i></a>
			</div>
			<ul id="tabs" class="tabs">
				<li id="tab_home" data-index="home" data-closeable="false" class="cur">
					<a class="waves-effect waves-light" href="javascript:;">首页</a>
				</li>
			</ul>
		</div>
		<div class="content_main">
			<div id="iframe_home" class="iframe cur">
				<p><h4>通用后台管理系统</h4></p>
			</div>
		</div>
	</section>
</section>
<footer id="footer"></footer>
<script>var BASE_PATH = '${basePath}';</script>
<script src="${basePath}/resources/static/plugins/jquery.1.12.4.min.js"></script>
<script src="${basePath}/resources/static/plugins/bootstrap-3.3.0/js/bootstrap.min.js"></script>
<script src="${basePath}/resources/static/plugins/waves-0.7.5/waves.min.js"></script>
<script src="${basePath}/resources/static/plugins/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"></script>
<script src="${basePath}/resources/static/plugins/BootstrapMenu.min.js"></script>
<script src="${basePath}/resources/static/plugins/device.min.js"></script>
<script src="${basePath}/resources/static/plugins/jquery.cookie.js"></script>
<script src="${basePath}/resources/static/js/admin.js"></script>
<script src="${basePath}/resources/static/plugins/fullPage/jquery.fullPage.min.js"></script>
<script src="${basePath}/resources/static/plugins/fullPage/jquery.jdirk.min.js"></script>
</body>
</html>