package org.revature.munderhill.expensereimbursementapp.servlets;

import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;


import static org.junit.Assert.*;

@Log4j2
public class LoginServletTest {

    LoginServlet loginServlet;
    MockHttpServletRequest req;
    MockHttpServletResponse resp;

    @Before
    public void init() {
        loginServlet = new LoginServlet();
        req = new MockHttpServletRequest();
        resp = new MockHttpServletResponse();
    }

    @After
    public void cleanup() {
        loginServlet = null;
        req = null;
        resp = null;
    }

    @Test
    public void testLoginServletSuccess() throws Exception{
        req.setCharacterEncoding("UTF-8");
        req.setContent("username=Edward&password=password".getBytes("UTF-8"));

        loginServlet.doPost(req, resp);
        assertEquals(302, resp.getStatus());
    }

    @Test
    public void testLoginServletEmployee() throws Exception{
        req.setCharacterEncoding("UTF-8");
        req.setContent("username=Edward&password=password".getBytes("UTF-8"));

        loginServlet.doPost(req, resp);
        assertEquals(302, resp.getStatus());
    }

    @Test
    public void testLoginServletManager() throws Exception{
        req.setCharacterEncoding("UTF-8");
        req.setContent("username=Michael&password=password".getBytes("UTF-8"));

        loginServlet.doPost(req, resp);
        assertEquals(302, resp.getStatus());
    }

    @Test
    public void testLoginServletIncorrect() throws Exception{
        req.setCharacterEncoding("UTF-8");
        req.setContent("username=Wrong&password=password".getBytes("UTF-8"));

        loginServlet.doPost(req, resp);
        assertEquals(401, resp.getStatus());
    }
}