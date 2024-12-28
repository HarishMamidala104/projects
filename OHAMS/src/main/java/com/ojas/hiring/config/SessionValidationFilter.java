package com.ojas.hiring.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ojas.hiring.dto.ErrorResponse1;
import com.ojas.hiring.serviceImpl.KeycloakServiceImpl;

@Component
public class SessionValidationFilter extends GenericFilterBean {

	private static final String AUTHORIZATION_HEADER = "authorization";
	private static final String AUTHORIZATION_BEARER = "Bearer ";
	private static final boolean supportUnsecureOperations = true;
	
	@Autowired
	KeycloakServiceImpl keycloakServiceImpl;

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String contextPath = request.getRequestURI();

		// System.out.println("Requested Resource : " + contextPath);
		// System.out.println("Requester : " + request.getRemoteAddr());

		// JSONObject jsonObj = new JSONObject(getBody(request));
		// String token = jsonObj.getString("token");

		String token = null;

		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				String headerName = (String) headerNames.nextElement();
				String headerValue = request.getHeader(headerName);
				//System.out.println("Header: " + headerName + "-->" + headerValue);
				if (headerName.equals(AUTHORIZATION_HEADER)) {
					token = headerValue.replace(AUTHORIZATION_BEARER, "");
				}
			}
		}
		

		if (token == null) {
			if(supportUnsecureOperations) {
				filterChain.doFilter(servletRequest, servletResponse);
			}else {
//				response.setHeader("Content-Type", "text/plain");
//				response.setStatus(401);
			    response.setContentType("application/json");
			    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				PrintWriter writer = response.getWriter();
				writer.write("Unauthorized Access");
				writer.close();
			}			
			
		} else {
			//String modToken = token.replaceAll(Cyp.genDelim(), "/");
			//System.out.println("Obtained & Modified Token:"+modToken);
			
			//System.out.println("decode token 667===================="+token);
			//String decrypt = Cyp.decrypt(modToken, null);
			if ( keycloakServiceImpl.isTokenExpired(token)) {
				//response.setHeader("Content-Type", "text/plain");
			    response.setContentType("application/json");
			    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 status code

				//response.setStatus(401);
				PrintWriter writer = response.getWriter();
				
				ObjectMapper objectMapper = new ObjectMapper();
		        
		        // Creating a response object with status and message
		        ErrorResponse1 errorResponse = new ErrorResponse1(401, "Token Expired");

		        // Convert the object to JSON string
		        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

		        // Write the JSON string to the response
		        writer.write(jsonResponse);
				
				writer.close();
			} else {
				// System.out.println("Decrypted Token : " + decrypt);
				//String parts[] = decrypt.split(Cyp.sessionValDelim);
				// System.out.println("Tokens Size : " + parts.length);

//				HttpSession newSession = request.getSession(true);
//				newSession.setAttribute("empId", parts[0]);
//				newSession.setAttribute("firstName", parts[1]);
//				newSession.setAttribute("lastName", parts[2]);
//				newSession.setAttribute("email", parts[3]);
//				newSession.setAttribute("phone", parts[4]);
//				newSession.setAttribute("profilePic", parts[5]);
//				newSession.setAttribute("callBackUrl", parts[6]);
//				newSession.setAttribute("token", token);

				
				if(!keycloakServiceImpl.isTokenExpired(token)) {
					
					System.out.println("Is Token Expired ===>"+keycloakServiceImpl.isTokenExpired(token));
			
				// Continue filter chain
				filterChain.doFilter(servletRequest, servletResponse);
			
			}
				else {
					System.out.println("Un authorized token=====>");
					response.setStatus(401, "Unauthorized");
					response.getWriter().write("Token Expired");
					response.getWriter().flush();
				}
			}

		}

	}

	private String getBody(HttpServletRequest request) {
		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			// throw ex;
			return "";
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException ex) {

				}
			}
		}

		body = stringBuilder.toString();
		return body;

	}
}
