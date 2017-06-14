package com.java.servlets.filters;

import com.java.servlets.controller.PageURL;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by proton2 on 29.10.2016.
 */
public class AuthenticationFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String uri = req.getRequestURI();
        HttpSession session = req.getSession(false);
        if((session == null || session.getAttribute("role") == null) && !(uri.endsWith("jsp") || uri.endsWith("LoginServlet"))){
            RequestDispatcher view = request.getRequestDispatcher(PageURL.LOGIN_ACTION);
            view.forward(request, response);
        }else{
            chain.doFilter(req, resp);
        }
    }

    @Override
    public void destroy() {}
}
