package app.pojo;

import java.util.List;

/**
 * kepler服务泛化请求包
 * @author mawentao
 */
public class KeplerReq {

	/** 服务类名 */
	private String serviceName;
	
	/** 服务版本号 */
	private String serviceVersion;
	
	/** 服务catalog */
	private String serviceCatalog;
	
	/** 调用的方法名 */
	private String method;
	
	/** 请求参数 */
	private List<Object> args;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public String getServiceVersion() {
		return serviceVersion;
	}

	public void setServiceVersion(String serviceVersion) {
		this.serviceVersion = serviceVersion;
	}

	public String getServiceCatalog() {
		return serviceCatalog;
	}

	public void setServiceCatalog(String serviceCatalog) {
		this.serviceCatalog = serviceCatalog;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public List<Object> getArgs() {
		return args;
	}

	public void setArgs(List<Object> args) {
		this.args = args;
	}

	@Override
	public String toString() {
		return "KeplerReq [service=" + serviceName + ", version="
				+ serviceVersion + ", catalog=" + serviceCatalog
				+ ", method=" + method + ", args=" + args + "]";
	}
	
	
}
