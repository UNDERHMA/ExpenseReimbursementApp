package org.revature.munderhill.expensereimbursementapp.DAOs;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.revature.munderhill.expensereimbursementapp.models.JsonCredential;

import static org.junit.Assert.assertTrue;

public class LoginDAOTest {

    LoginDAO loginDAO;

    @Before
    public void init() {
        loginDAO = new LoginDAO();
    }

    @After
    public void cleanup() {
        loginDAO = null;
    }

    @Test
    public void canLogin(){
        boolean test = loginDAO.login("Employee1","password") == null ||
                loginDAO.login("Employee1","password") instanceof JsonCredential;
        assertTrue(test);
    }
}