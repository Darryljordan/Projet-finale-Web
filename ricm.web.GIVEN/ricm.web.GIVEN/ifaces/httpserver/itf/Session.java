package httpserver.itf;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import httpserver.itf.impl.HttpServer;

public class Session implements HttpSession {
	
	private String id;
	private HttpServer m_hs;
	protected HashMap<String, Object> sessions = new HashMap<String, Object>();
	private Timer timer;
	private TimerTask tt;
	
	public Session(String id, HttpServer m_hs) {
		this.m_hs = m_hs;
		this.id = id;
		tt = new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				m_hs.sessions.remove(this);
			}
		};
		timer = new Timer(true);
		timer.schedule(tt, 5000);
	}
	
	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public Object getValue(String key) {
		// TODO Auto-generated method stub
		return sessions.get(key);
	}

	@Override
	public void setValue(String key, Object value) {
		// TODO Auto-generated method stub
		sessions.put(key, value);
	}
	
	public void newTimer() {
		tt.cancel();
		timer.cancel();
		timer.purge();
		tt = new TimerTask() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				m_hs.sessions.remove(this);
			}
		};
		timer = new Timer(true);
		timer.schedule(tt, (5000));
	}

}
