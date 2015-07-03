package com.e104;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class ServStat
 */
@WebServlet("/ServStat")
public class ServStat extends HttpServlet {
	private static final long serialVersionUID = 1L;
	// Server State: 0 = Online, 1 = Offline
	private int sStat;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServStat() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		sStat = 0;
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(sStat == 0){
			response.setStatus(200);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><head>");
			out.println("<title>Server Status</title>");
			out.println("</head><body>");
			out.println("<h1>Server is alive.</h1>");
			Map<String, ? extends ServletRegistration> servletRegistrations = request.getServletContext().getServletRegistrations();
			for (Object key : servletRegistrations.keySet()){
				out.println("<bold>Key: " + key.toString() + " Value: " + servletRegistrations.get(key) +
						"<br> Mapping: " + servletRegistrations.get(key).getMappings() + "</bold><br><br>");
				
			}
			out.println("</body></html>");
		}else if(sStat == 1){
			response.setStatus(423);
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			out.println("<html><head>");
			out.println("<title>423 - Locked</title>");
			out.println("</head><body>");
			out.println("<h1>Server is temporarily closed</h1>");
			out.println("</body></html>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		StringBuffer strBuf = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				strBuf.append(line);
		} catch (Exception e) { /*report an error*/ }

		
		//JSONObject jsonObject = JSONObject.fromObject(strBuf.toString());
		JSONObject jsonObjectJackyFromString = new JSONObject(strBuf.toString());
		sStat = jsonObjectJackyFromString.getInt("status");
		response.setStatus(200);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println("<html><head>");
		out.println("<title>Server Status</title>");
		out.println("</head><body>");
		out.println("<h1>Receive status code =" + sStat + ".</h1>");
		if (sStat == 0)
			out.println("<h1>Server would be online .</h1>");
		else if(sStat == 1)
			out.println("<h1>Server would be offline .</h1>");
		out.println("</body></html>");
	}

}
