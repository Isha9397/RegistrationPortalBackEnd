package com.example.demo.JwtFilter;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.filter.GenericFilterBean;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.ServletRequest;

public class SecurityFilter extends GenericFilterBean
{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest httpReq = (HttpServletRequest) request;
		
		HttpServletResponse  httpRes = (HttpServletResponse) response;
		System.out.println(httpReq.getMethod());
		
		if(!httpReq.getMethod().equals("OPTIONS"))
		{
			String authHeader = httpReq.getHeader("authorization");
			
			System.out.println(httpReq);
			
			if(authHeader == null || !authHeader.startsWith("Bearer"))
			{
				throw new ServletException("Missing or invalid Authentication header");
			}
			
			String jwtToken = authHeader.substring(7);  // Bearer e748fhksot89ldnfdo947.du3j890nsk
			
			System.out.println(authHeader);
			
			Claims claims = Jwts.parser().setSigningKey("secret key").parseClaimsJws(jwtToken).getBody();
			System.out.println("claims is --"+claims);
			Object getfromClaimId=claims.get("id");
			System.out.println("stringId from claims is --"+getfromClaimId);
			httpReq.setAttribute("username", claims);
//			httpReq.setAttribute("id", claims);		
		}
		
		chain.doFilter(request, response);
	}
	
}
