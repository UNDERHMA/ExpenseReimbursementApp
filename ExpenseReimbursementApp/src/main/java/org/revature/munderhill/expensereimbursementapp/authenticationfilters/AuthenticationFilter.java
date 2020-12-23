package org.revature.munderhill.expensereimbursementapp.authenticationfilters;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Log4j2
@WebFilter(urlPatterns = "*.html", filterName = "AuthenticationFilter")
public class AuthenticationFilter implements Filter {


    /*
        This method performs the filter function for the application. If a user does
        not have a valid expenseapp_authenticationcookie in their request, this
        filter routes them back to login.html. This filter only applies to urlPatterns
        ending with .html. If the user does have a valie
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletResponse resp = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;
        boolean activeCookie = false;

        // see if user has a valid cookie to be logged in
        Cookie[] cookies = req.getCookies();
        if(cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("expenseapp_authenticationcookie")) {
                    activeCookie = true;
                }
            }
        }

        String uri = req.getRequestURI();
        // if cookie is active or get request is for login.html, allow the request to proceed
        if(activeCookie || uri.equals("/Expense_Reimbursements_App_war/login.html")) {
            chain.doFilter(request,response);
        }
        // reroute requests without valid auth cookie to login.html
        else{
            String loginUrl = String.format("%s/login.html", req.getContextPath());
            try {
                resp.sendRedirect(loginUrl);
            } catch (IOException e) {
                resp.setStatus(500);
                log.error("IOException on Redirect - AuthenticationFilter\n", e);
                String errorUrl = String.format("%s/500Error.html", req.getContextPath());
                req.getRequestDispatcher(errorUrl).forward(request,response);
            }
        }

    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
