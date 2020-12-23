package org.revature.munderhill.expensereimbursementapp.servlets;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.revature.munderhill.expensereimbursementapp.DAOs.ReportDAO;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.Cookie;

import java.net.URLEncoder;

import static org.junit.Assert.*;

public class ReportServletTest {

    ReportServlet reportServlet;
    MockHttpServletRequest req;
    MockHttpServletResponse resp;

    @Before
    public void init() {
        reportServlet = new ReportServlet();
        req = new MockHttpServletRequest();
        resp = new MockHttpServletResponse();
    }

    @After
    public void cleanup() {
        reportServlet = null;
        req = null;
        resp = null;
    }

    @Test
    public void testLoginServletEmployee() throws Exception{
        String cookieString = URLEncoder.encode(
                "{\"accountId\":1,\"userId\":1,\"permissionName\":\"Employee", "UTF-8");
        Cookie c = new Cookie("expenseapp_authenticationcookie",
                cookieString);
        req.setCookies(c);
        reportServlet.doGet(req, resp);
        assertEquals(200, resp.getStatus());
    }

    @Test
    public void testLoginServletManager() throws Exception{
        String cookieString = URLEncoder.encode(
                "{\"accountId\":2,\"userId\":2,\"permissionName\":\"Manager", "UTF-8");
        Cookie c = new Cookie("expenseapp_authenticationcookie",
                cookieString);
        req.setCookies(c);
        reportServlet.doGet(req, resp);
        assertEquals(200, resp.getStatus());
    }
}