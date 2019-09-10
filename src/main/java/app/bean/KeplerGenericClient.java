package app.bean;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import app.pojo.KeplerReq;
import app.util.KeplerGenericArgs;
import app.util.TimeTool;

import com.kepler.generic.reflect.GenericService;
import com.kepler.host.Host;
import com.kepler.host.HostState;
import com.kepler.host.Hosts;
import com.kepler.host.impl.DefaultHostContext;
import com.kepler.service.Service;

/**
 * Kepler泛化调用客户端
 * @author mawentao
 *
 */
@Component
public class KeplerGenericClient {
	
	@Autowired
	GenericService genericService;
	
	@Autowired
	DefaultHostContext context;

	/**
	 * kepler泛化调用
	 * @param req
	 */
	public void rpc(KeplerReq req) {
		if (this.genericService == null) {
			System.err.println("this.genericService is null");
		}
		Service srv = new Service(req.getServiceName(), req.getServiceVersion(), req.getServiceCatalog());
		String method = req.getMethod();
		List<Object> args = req.getArgs();
		try {
			long startTime = System.currentTimeMillis();
			Object ret = genericService.invoke(srv, method, null, args.toArray());
			String timeSpan = TimeTool.getTimeDiff(startTime);
			String json = KeplerGenericArgs.toJson(ret);
			
			String hostinfo = getHostInfo(srv);
			
			System.err.println(">>>>> "+req+"\nRes: "+json+"\n<<<<< time: "+timeSpan+" "+hostinfo);
        } catch (Throwable e) {
        	System.err.println(e);
        }
	}

	// 获取Kepler服务的主机信息
	private String getHostInfo(Service srv)
	{
		String res = "[Host: ";
		String sp = "";
		Hosts hosts = context.getOrCreate(srv);
		Collection<Host> hs = hosts.select(HostState.ACTIVE);
		for (Host host : hs) {
			res += sp+host.host()+":"+host.port()+"(pid-"+host.pid()+")";
			sp = ";";
		}
		res+="]";
		return res;
	}
	
}
