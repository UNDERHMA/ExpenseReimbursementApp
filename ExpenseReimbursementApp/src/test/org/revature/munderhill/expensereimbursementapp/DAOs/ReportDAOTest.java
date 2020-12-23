package org.revature.munderhill.expensereimbursementapp.DAOs;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.revature.munderhill.expensereimbursementapp.entities.ReimbursementRequest;
import org.revature.munderhill.expensereimbursementapp.hibernate.HibernateConnectionUtility;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertTrue;

@Log4j2
public class ReportDAOTest {

    ReportDAO reportDAO;

    @Before
    public void init() {
        reportDAO = new ReportDAO();
    }

    @After
    public void cleanup() {
        reportDAO = null;
    }

    @AfterClass
    public static void cleanupTestReport() {
        try(Session session = HibernateConnectionUtility.getInstance().
                getDatabaseSession()) {
            Query q1 = session.getNamedNativeQuery("deleteTestReport");
            Transaction txn = session.beginTransaction();
            q1.executeUpdate();
            txn.commit();
            session.close();
        }
        catch (Exception e) {
            log.error("Database Connection Error - cleanupTestReport - ReportDAOTest", e);
        }
    }

    @Test
    public void canGetEmployeePendingReports(){
        boolean test = reportDAO.getEmployeePendingReports(1) == null ||
                reportDAO.getEmployeePendingReports(1) instanceof List;
        assertTrue(test);
    }

    @Test
    public void canGetEmployeeResolvedReports(){
        boolean test = reportDAO.getEmployeeResolvedReports(1) == null ||
                reportDAO.getEmployeeResolvedReports(1) instanceof List;
        assertTrue(test);
    }

    @Test
    public void canGetAllPendingReports(){
        boolean test = reportDAO.getAllPendingReports() == null ||
                reportDAO.getAllPendingReports() instanceof List;
        assertTrue(test);
    }

    @Test
    public void canGetAllResolvedReports(){
        boolean test = reportDAO.getAllResolvedReports() == null ||
                reportDAO.getAllResolvedReports() instanceof List;
        assertTrue(test);
    }

    @Test
    public void canSubmitNewReport(){
        boolean test = reportDAO.submitNewReport(1,new BigDecimal(100), "testX4#321");
        assertTrue(test);
    }

    @Test
    public void canUpdateReport(){
        try(Session session = HibernateConnectionUtility.getInstance().
                getDatabaseSession()) {
            Query q1 = session.getNamedQuery("getTestReport");
            ReimbursementRequest reimbursementRequest = (ReimbursementRequest) q1.getSingleResult();
            boolean test = reportDAO.updateReport(2, reimbursementRequest.getReimbursementRequestId(), 2);
            assertTrue(test);
        }
        catch (Exception e) {
            log.error("Database Connection Error - canUpdateReport - ReportDAOTest", e);
        }
    }
}