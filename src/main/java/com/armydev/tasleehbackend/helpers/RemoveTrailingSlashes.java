package com.armydev.tasleehbackend.helpers;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class RemoveTrailingSlashes implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		System.out.println("Filter Applied");
		if (request instanceof HttpServletRequest) {
			HttpServletRequest httpRequest = (HttpServletRequest) request;
			String requestURI = httpRequest.getRequestURI();
			if (requestURI.length() > 1 && requestURI.endsWith("/")) {
				String newURI = requestURI.substring(0, requestURI.length() - 1);
				request.getRequestDispatcher(newURI).forward(request, response);
				return;
			}
		}
		chain.doFilter(request, response);
	}

}
