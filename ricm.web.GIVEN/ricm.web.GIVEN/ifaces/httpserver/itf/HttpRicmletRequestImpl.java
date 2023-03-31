package httpserver.itf;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import httpserver.itf.impl.HttpServer;

public class HttpRicmletRequestImpl extends HttpRicmletRequest{

	public HttpRicmletRequestImpl(HttpServer hs, String method, String ressname, BufferedReader br) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		super(hs, method, ressname, br);
		// TODO Auto-generated constructor stub
		String clsname = ressname.replace('/','.');
		
		HashMap<String, Integer> classes = hs.getHashMap();
		if(!classes.containsKey(clsname)) {
			Class<?> c = Class.forName(clsname); //Creates the class
			c.getDeclaredConstructor().newInstance();
			classes.put(clsname, classes.size() + 1);
		}
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getArg(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCookie(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void process(HttpResponse resp) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
