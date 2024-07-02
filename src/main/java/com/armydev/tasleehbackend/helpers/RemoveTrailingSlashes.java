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
		if (request instanceof HttpServletRequest httpRequest) {
			String requestURI = httpRequest.getRequestURI();
			if (requestURI.length() > 1 && requestURI.endsWith("/")) {
				// Remove the trailing slash and redirect or forward as per requirement
				String newURI = requestURI.substring(0, requestURI.length() - 1);
				// Use either forward or sendRedirect based on your requirement
				// Forwarding
				request.getRequestDispatcher(newURI).forward(request, response);
				// OR Redirecting (uncomment the below line if redirection is preferred)
				// ((HttpServletResponse) response).sendRedirect(newURI);
				return;
			}
		}
		chain.doFilter(request, response); // Proceed with the chain if no trailing slash
	}
}
