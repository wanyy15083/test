package com.test.utils;

import java.io.*;

/**
 * 图片文件的路径
 * @author Cheng.Biao
 * 2015年10月22日 上午11:49:42
 */
public class ImagesPathUtil {
	
	private static String basePath;
	private static String ewmUrl;
	private static Integer size;
	private static final int DEFAULT_BUFFER_SIZE = 1024 * 4;
	public static void setBasePath(String basePath) {
		ImagesPathUtil.basePath = basePath;
	}
	
	public static String getBasePath() {
		return basePath;
	}

	public static String getEwmUrl() {
		return ewmUrl;
	}

	public static void setEwmUrl(String ewmUrl) {
		ImagesPathUtil.ewmUrl = ewmUrl;
	}
	
	public static Integer getSize() {
		return size;
	}

	public static void setSize(Integer size) {
		ImagesPathUtil.size = size;
	}

	/**
	 * 创建文件目录
	 * 返回文件相对目录
	 * @param folder
	 * @throws IOException 
	 */
	public static String makdirs(String folder,String fileName,InputStream is) throws IOException{
		File fold = new File(basePath + folder);
		if(!fold.exists()){
			fold.mkdirs();
		}
		File file = new File(basePath + folder,fileName);
		if(file.exists()){
			file.delete();
		}
		copyFile(is, file);
		return folder+"/"+fileName;
	}
	/**
	 * 创建文件目录
	 * 返回文件相对目录
	 * @param folder
	 * @throws IOException 
	 */
	public static File makdir(String folder) throws IOException{
		File fold = new File(basePath + folder);
		if(!fold.exists()){
			fold.mkdirs();
		}
		return fold;
	}
	/**
	 * 删除之前的文件
	 * @param folder
	 * @param fileName
	 */
	public static void deleteOldFile(String path){
		File oldFile = new File(basePath+path);
		if(oldFile.exists()){
			oldFile.delete();
		}
	}
	/**
	 * 文件复制
	 * @param input
	 * @param outFile
	 * @return
	 * @throws IOException 
	 */
	private static void copyFile(InputStream input, File outFile) throws IOException{
		OutputStream output = null;
		output = new FileOutputStream(outFile);
		byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
		}
		if(output != null){
			output.close();
			input.close();
		}
	}
	
}
