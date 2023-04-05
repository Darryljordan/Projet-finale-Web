package httpserver.itf;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.StringTokenizer;

import httpserver.itf.impl.HttpServer;

public class HttpRicmletRequestImpl extends HttpRicmletRequest{
	
	
	protected HashMap<String, String> cookies = new HashMap<String, String>();
	protected Session session;
	
	public HttpRicmletRequestImpl(HttpServer hs, String method, String ressname, BufferedReader br) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		super(hs, method, ressname, br);
		// TODO Auto-generated constructor stub
		String nextLine = null;
		while(!(nextLine = br.readLine()).equals("")) {
			if(nextLine.contains("Cookie:")) {
				//System.out.println("Problem in constructor of RICMLET request");
				String[] draftLine = nextLine.split(" ", 2);
				String cookie = draftLine[1];
				String[] cookieTable = cookie.split("; ");
				for(int i = 0; i<cookieTable.length; i++) {
					draftLine = cookieTable[i].split("=");
					cookies.put(draftLine[0], draftLine[1]);
				}
			}
		}
		if(getCookie("session-id") == null) {
			session = new Session(String.valueOf(m_hs.sessionCount), m_hs);
			m_hs.sessionCount++;
			m_hs.sessions.add(session);
		} else {
			boolean find = false;
			Iterator<Session> list = m_hs.sessions.iterator();
			while(list.hasNext() && find == false) {
				Session currentSession = list.next();
				if(currentSession.getId().equals(getCookie("session-id"))) {
					//currentSession.newTimer();
					session = currentSession;
					find = true;
				}
			}
			if(find == false) {
				session = new Session(String.valueOf(m_hs.sessionCount), m_hs);
				m_hs.sessionCount++;
				m_hs.sessions.add(session);
			}
		}
	}

	@Override
	public HttpSession getSession() {
		// TODO Auto-generated method stub
		return session;
	}

	@Override
	public String getArg(String name) {
		// TODO Auto-generated method stub
		if(this.getRessname().contains("?")) {
			String argument = this.getRessname();
			argument = argument.substring(argument.indexOf('?')+1);
			String[] arguments = argument.split("&");
			String value = null;
			for(int i = 0; i < arguments.length; i++) {
				String test = arguments[i].substring(0, arguments[i].indexOf("="));
				if(test.equals(name)) {
					value = arguments[i].substring(arguments[i].indexOf('=')+1);
				}
			}
			return value;
		}
		return null;
	}

	@Override
	public String getCookie(String name) {
		// TODO Auto-generated method stub
		return cookies.get(name);
	}

	@Override
	public void process(HttpResponse resp) throws Exception {
		// TODO Auto-generated method stub
		String clsname = this.getRessname().replace('/','.');
		File file = null;
		if(clsname.contains("?")) {
			clsname = clsname.substring(10, clsname.indexOf('?'));
			System.out.println("Bob marley isn't working");
			file = new File(this.m_hs.getFolder() + this.getRessname().substring(0, this.getRessname().indexOf('?')) + ".java");
			System.out.println(file.getAbsolutePath());
		} else {
			clsname = clsname.substring(10);
			System.out.println(clsname);
			file = new File(this.m_hs.getFolder() + this.getRessname() + ".java");
		}
		try {
			System.out.println(clsname);
			HttpRicmlet ricmlet = this.m_hs.getInstance(clsname);
			System.out.println(clsname + " After getting the instance");
			System.out.println(this);
			ricmlet.doGet(this, (HttpRicmletResponse)resp);
		} catch (ClassNotFoundException e) {
			System.out.println("Error : Launching the class : " + e.getMessage() + " " + e.getStackTrace());
			resp.setReplyError(404, "Error : Launching the class");
		}
	}
}
