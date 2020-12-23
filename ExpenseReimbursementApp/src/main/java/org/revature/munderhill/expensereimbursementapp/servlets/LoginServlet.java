package org.revature.munderhill.expensereimbursementapp.servlets;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.revature.munderhill.expensereimbursementapp.DAOs.LoginDAO;
import org.revature.munderhill.expensereimbursementapp.models.JsonCredential;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Log4j2
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    /*
        Processes http post request generated when the user hits the submit button on
        login.html. Parses the http post request, extracting the username and password
        variables. Username and password are then used by the loginDAO to attempt a
        login. If the login method returns a JsonCredential, the user is routed to
        either employeehomescreen.html or managerhomescreen.html depending on whether
        the permissionName in the JsonCredential is Manager or Employee. If the login
        method returns null, a 401 response is sent back to the login.html page,
        which Javascript code processes to prompt the user that their username and
        password was invalid.
     */
    @Override
    protected void doPost(HttpServletRequest req,
                            HttpServletResponse resp)
            throws ServletException, IOException {

        String requestBody = "";
        requestBody = IOUtils.toString(req.getReader());
        String[] usernameAndPassword = requestBody.split("&");
        String username = usernameAndPassword[0].substring(9);
        String password = usernameAndPassword[1].substring(9);
        LoginDAO loginDAO = new LoginDAO();
        JsonCredential credential = loginDAO.login(username,password);
        if(credential == null) {
            // send invalid login response
            log.info("LoginServlet, Invalid Username/Password");
            String invalidLoginURL = String.format("%s/", req.getContextPath());
            resp.setStatus(401);
        }
        else{
            // login user, maybe with a redirect
            String credentialAsString = credential.makeJsonString();
            if(!credentialAsString.equals("")) {
                // encoding prevents issue where Tomcat sees Cookie as invalid
                credentialAsString = URLEncoder.encode(credentialAsString,"UTF-8");
                Cookie cookie = new Cookie("expenseapp_authenticationcookie",credentialAsString);
                cookie.setMaxAge(1200000); //roughly 2 weeks before expiration
                if(credential.getPermissionName().equals("Employee")) {
                    resp.addCookie(cookie);
                    String employeeHomeScreen = String.format("%s/employeehomescreen.html", req.getContextPath());
                    resp.sendRedirect(employeeHomeScreen);
                }
                else if(credential.getPermissionName().equals("Manager")) {
                    resp.addCookie(cookie);
                    String managerHomeScreen = String.format("%s/managerhomescreen.html", req.getContextPath());
                    resp.sendRedirect(managerHomeScreen);
                }
                else{
                    log.error("LoginServlet, Invalid Permission Name - 500 error");
                    resp.setStatus(500);
                    String errorUrl = String.format("%s/500Error.html", req.getContextPath());
                    req.getRequestDispatcher(errorUrl).forward(req,resp);
                }
            }
        }

    }
}
