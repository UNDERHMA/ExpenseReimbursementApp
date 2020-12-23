package org.revature.munderhill.expensereimbursementapp.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LogoutServlet", urlPatterns = "/logout")
public class LogoutServlet extends HttpServlet {

    /*
      Processes http get requests, using the invalidateCookie helper method to set
      the expenseapp_authenticationcookie's maxAge to 0, which invalidates the cookie.
      This means the user cannot be granted access to .html pages protected by the
      Authentication filter until they login again and obtain a valid
      expenseapp_authenticationcookie. Once the cookie is invalidated, the response
      object redirects the user to login.html.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        invalidateCookie(request,response);
        String loginUrl = String.format("%s/login.html", request.getContextPath());
        response.sendRedirect(loginUrl);
    }
    /*
       Helper method for doGet. Invalidates expenseapp_authenticationcookie by setting
       maxAge to 0.
     */
    private void invalidateCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for(Cookie c : cookies){
            if(c.getName().equals("expenseapp_authenticationcookie")) {
                c.setMaxAge(0);
                response.addCookie(c);
            }
        }
    }
}
