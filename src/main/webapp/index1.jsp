<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set var="baseurl" value="${pageContext.request.getContextPath()}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>index</title>
<link href="${baseurl}/js/uploadify/uploadify.css" rel="stylesheet"
	type="text/css" />
<link href="${baseurl}/js/eayui/themes/metro/easyui.css"
	rel="stylesheet" type="text/css" />
<link href="${baseurl}/js/eayui/themes/icon.css" rel="stylesheet"
	type="text/css" />

<script type="text/javascript" src="${baseurl}/js/jquery-1.8.3.js"></script>
<script type="text/javascript"
	src="${baseurl}/js/eayui/jquery.easyui.min.js"></script>
<script type="text/javascript"
	src="${baseurl}/js/eayui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript"
	src="${baseurl}/js/uploadify/jquery.uploadify.js"></script>
</head>
<body>
	Hello World!

	<div>
		<a href="${baseurl}/testExcel/download">下载模板</a>
	</div>
	<div>
		<a href="${baseurl}/testExcel/exportSku">导出数据</a>
	</div>
	<div>
		<a href="${baseurl}/testExcel/exportAll">导出数据map</a>
	</div>
	<div id="fileQueue"></div>
	<input type="file" name="uploadify" id="uploadify" />
	<p>
		<a href="javascript:$('#uploadify').uploadify('upload', '*')">导入数据</a>
		| <a href="javascript:$('#fuploadify').uploadify('stop')">停止导入</a>
	</p>

	<div>
		<a href="${baseurl}/user/insert">添加用户</a>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$('#uploadify').uploadify({
			//flash
			'swf' : '${baseurl}/js/uploadify/uploadify.swf',
			//上传服务端程序
			'uploader' : 'testExcel/import',
			//按钮高度
			'height' : 30,
			//按钮宽度
			'whith' : 120,
			//文件选择后的容器ID
			'queueID' : 'fileQueue',
			'buttonText' : '选择文件...',
			//服务器端脚本使用的文件对象的名称 $_FILES个['upload']
			'fileObjName' : 'file',
			//附带值
			/* 'formData' : {
				'userid' : '用户id',
				'username' : '用户名',
				'rnd' : '加密密文'
			}, */
			//允许上传的文件后缀
			//'fileTypeExts' : '*.jpg;*.jpge;*.gif;*.png',
			//在浏览窗口底部的文件类型下拉菜单中显示的文本
			//'fileTypeDesc': "请选择图片文件",
			//浏览按钮的背景图片路径
			//'buttonImage':'upbutton.gif',
			//上传文件的大小限制
			//'fileSizeLimit':'100KB',
			//上传数量
			//'queueSizeLimit' : 25,
			//超时时间
			//'successTimeout':99999,
			//是否自动上传
			'auto' : false,
			//多选
			'multi' : true,
			//请求方式
			'method' : 'post',
			//'debug' : true,
			//上传到服务器，服务器返回相应信息到data里
			'onUploadSuccess' : function(file, data, response) {
				$('#' + file.id).find('.data').html(' 上传完毕');
				alert('The file ' + file.name + ' was successfully uploaded with a response of ' + response + ':' + data);
				window.location.href=data;
			},
			'onUploadError' : function(file, errorCode, errorMsg, errorString) {
				alert('The file ' + file.name + ' could not be uploaded: ' + errorString);
			}

		});
	});

	function downloadTemplate() {

	}
</script>
</html>