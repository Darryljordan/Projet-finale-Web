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
		String clsname = this.getRessname().replace('/','.');
		clsname = clsname.substring(10, clsname.indexOf('?'));
		System.out.println(clsname);
		HttpRicmlet ricmlet = this.m_hs.getInstance(clsname);
		ricmlet.doGet(this, (HttpRicmletResponse)resp);
	}

}
