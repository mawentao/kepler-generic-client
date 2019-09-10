package app.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class KeplerGenericArgs {

	private static ObjectMapper mapper = new ObjectMapper();
	
	/**
	 * json串转成Kepler泛化调用参数
	 * @param json
	 * @return
	 */
	public static List<Object> parseJson(String json) {
		//1. 先按list格式读取
		List<Object> args = readList(json);
		//2. 再按map格式读取
		if (args==null) {
			Map jsonObject = readMap(json);
			if (jsonObject != null) {
				args = new LinkedList<Object>();
				args.add(jsonObject);
			}
		}
		return args;
	}
	
	/**
	 * Object转json串
	 * @param object
	 * @return
	 */
	public static String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			return object.toString();
		}  
	}
	
	
	/**
	 * 按list读取
	 * @param json
	 * @return
	 */
	private static List<Object> readList(String json) {
		try {
			List<Object> objs = mapper.readValue(json, List.class);
			return objs;
		} catch (Exception e) {
			return null;
		}
	}
	
	/**
	 * 按map读取
	 */
	private static Map readMap(String json) {
		try {
			return mapper.readValue(json, Map.class);
		} catch (Exception e) {
			return null;
		}
	}


	public static void main(String[] args) {
		String[] jsons = {
			"[1,2,3,\"aaa\"]",
			"{\"id\":1001,\"name\":\"aaaa\"}",
			"[1,{\"id\":1001,\"name\":\"aaaa\"}]"
		};
		
		for (String json : jsons) {
			List<Object> as = parseJson(json);
			System.out.println(as);
		}
		
	}
	
}
