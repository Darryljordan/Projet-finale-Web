package httpserver.itf.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;

import httpserver.itf.HttpRequest;
import httpserver.itf.HttpResponse;

/*
 * This class allows to build an object representing an HTTP static request
 */
public class HttpStaticRequest extends HttpRequest {
	static final String DEFAULT_FILE = "index.html";
	
	public HttpStaticRequest(HttpServer hs, String method, String ressname) throws IOException {
		super(hs, method, ressname);
	}
	
	public void process(HttpResponse resp) throws Exception {
	// TO COMPLETE
		File file = new File(this.m_hs.getFolder(), m_ressname);
		//System.out.println(file.getPath());
		HttpResponseImpl res = (HttpResponseImpl) resp;
		if(file.exists()) {
			res.setReplyOk();
			res.setContentLength((int)file.length());
			res.setContentType(getContentType(m_ressname));
			FileInputStream fis = new FileInputStream(file);
			PrintStream ps = res.beginBody();
			ps.write(fis.readAllBytes());
			fis.close();
			//res.beginBody(fis.readAllBytes());
		} else {
			String msg = "File not found";
			res.setReplyError(404, msg);
			
			//System.exit(1);
		}
	}

}
