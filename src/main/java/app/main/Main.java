package app.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.LogManager;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.bean.KeplerGenericClient;
import app.pojo.KeplerReq;
import app.util.FileTool;
import app.util.KeplerGenericArgs;

/**
 * 程序启动入口
 * @author mawentao
 * @date 2017-10-13 13:39:34
 */

public class Main {
	
	private static String VERSION = "1.2";
	
	private static String author = "mawentao";

    private static Logger logger = Logger.getLogger(Main.class);

    private static void printVersion() {
    	System.out.println("Kepler-Generic-Client(v"+VERSION+") powered by "+author);
    }
    
    /**
     * 使用说明
     */
    private static void printUsage() {
    	printVersion();
    	System.out.println("[usage]: java -cp properties:kepler-generic-client.jar app.main.Main <packs-file>");
    	System.out.println("\npacks-file's format:serviceClassName\tserviceVersion\tserviceCatalog\tmethodName\tjsonRequest");
    	System.out.println("e.g.");
    	System.out.println("com.kepler.demo.service.BaseService\t0.0.1\t\tgetNowTime\t[]");
    	System.out.println("com.kepler.demo.service.BaseService\t0.0.1\t\tget\t[100]");
    	System.out.println("com.kepler.demo.service.BaseService\t0.0.1\t\tfindItem\t[{\"id\":1001,\"name\":\"conan\"}]");
    	System.out.println("com.kepler.demo.service.BaseService\t0.0.1\t\tbuild\t[9527,\"mawt\"]");
    	System.exit(0);
    }
    
    /** 解析行 */
    private static KeplerReq parseLine(int lineNo, String line) {
    	KeplerReq req = null;
    	//0. 空行，注释行过滤
    	line = line.trim();
    	if (line.isEmpty() || line.startsWith("#")) return req;
    	
    	//1. split line
//    	System.out.println("line " + lineNo + ": " + line);
    	String[] segs = line.split("\t");
    	if (segs.length!=5) {
    		System.err.println("[Error Format Line "+lineNo+"]: "+line);
    		return req;
    	}
    	//2. 转成KeplerReq结构
    	req = new KeplerReq();
    	req.setServiceName(segs[0]);
    	req.setServiceVersion(segs[1]);
    	req.setServiceCatalog(segs[2]);
    	req.setMethod(segs[3]);
    	List<Object> args = KeplerGenericArgs.parseJson(segs[4]);
    	if (args==null) {
    		System.err.println("ParseJsonFail [line:"+lineNo+"]: "+line);
    		return null;
    	}
    	req.setArgs(args);
    	
    	return req;
    }
    
    /** 读取文件，解析行 */
    private static List<KeplerReq> readFile(String fileName) {
    	List<KeplerReq> res = new LinkedList<KeplerReq>();
    	//1. 读取文件
		File file = new File(fileName);
		if (!file.canRead()) {
			System.err.println("file can not find or read: "+fileName);
			System.exit(0);
		}
		//2. 逐行读取文件
		BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempString = null;
            int line = 1;
            // 一次读入一行，直到读入null为文件结束
            while ((tempString = reader.readLine()) != null) {
                KeplerReq req = parseLine(line, tempString);
                if (req!=null) {
                	res.add(req);
                }
                line++;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
    	return res;
    }
    
	public static void main(String[] args) {
		LogManager.getLogManager().reset();	//!< 关闭Java原生log
		printVersion();
		
		//0. 显示一些主要的配置信息
		try {
			Properties pps = FileTool.loadProperties("kepler.conf");
			String zkhost = (String)pps.get("com.kepler.zookeeper.zkfactory.host");
			if (zkhost==null) {
				throw new Exception("com.kepler.zookeeper.zkfactory.host is not defined");
			}
			pps.list(System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//1. get req-pack-file
		if (args.length==0) {
			printUsage();
		}
		List<KeplerReq> reqs = readFile(args[0]);
		int size = reqs.size();
		if (size==0) {
			System.out.println("no effective kepler request pack defined");
			System.exit(0);
		}
		
		//2. 逐个请求发包
		System.out.println("===== read kepler request packs from file [count:"+reqs.size()+"] =====");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring-main.xml");
		try {
			KeplerGenericClient client = context.getBean(KeplerGenericClient.class);
			for (KeplerReq req : reqs) {
				client.rpc(req);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//9. exit
		context.close();
		System.exit(0);
	}

}
