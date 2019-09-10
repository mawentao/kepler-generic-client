package app.util;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import app.main.Main;

/**
 * 文件处理工具类
 * @author mawentao
 */

public class FileTool {

	/**
	 * 获取classpath下的文件
	 * @param fileName
	 * @return
	 */
	public static String getClassPathFile(String fileName) throws Exception {
		ClassLoader classLoader = Main.class.getClassLoader();
		URL url = classLoader.getResource(fileName);
		if (url==null) {
			throw new Exception("can't find file in classpath: "+fileName);
		}
		return url.getFile();
	}
	
	/**
	 * 加载classpath下的配置文件
	 */
	public static Properties loadProperties(String classPathFileName) throws Exception {
		Properties pps = new Properties();
		String filename = getClassPathFile(classPathFileName);
		pps.load(new FileInputStream(filename));
		return pps;
	}
	
}
