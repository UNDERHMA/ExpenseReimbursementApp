package org.revature.munderhill.expensereimbursementapp.DAOs;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.revature.munderhill.expensereimbursementapp.entities.ReimbursementRequest;
import org.revature.munderhill.expensereimbursementapp.hibernate.HibernateConnectionUtility;

import java.math.BigDecimal;
import java.util.List;

@Log4j2
public class ReportDAO {

    /*
       This method is called when an the permissionName in the expenseapp_authenticationcookie
       is set to Employee. This method returns all pending reports for 1 employee. It uses
       a try-with-resources to get a Hibernate Session object and runs the named query
       getEmployeePendingReports with the accountId input parameter. Returns null if no
       result found in database.
     */
    public List<ReimbursementRequest> getEmployeePendingReports(int accountId) {
        try(Session session = HibernateConnectionUtility.getInstance().
                getDatabaseSession()) {
            // query database for user
            Query q1 = session.getNamedQuery("getEmployeePendingReports");
            q1.setParameter("accountId", accountId);
            List<ReimbursementRequest> requests =  q1.getResultList();
            session.close();
            return requests;
        }
        catch (Exception e) {
            log.error("Database Connection Error - getEmployeePendingReports - Reports DAO", e);
        }
        return null;
    }

    /*
       This method is called when an the permissionName in the expenseapp_authenticationcookie
       is set to Employee. This method returns all resolved reports for 1 employee. It uses
       a try-with-resources to get a Hibernate Session object and runs the named query
       getEmployeeResolvedReports with the accountId input parameter. Returns null if no
       result found in database.
     */
    public List<ReimbursementRequest> getEmployeeResolvedReports(int accountId) {
        try(Session session = HibernateConnectionUtility.getInstance().
                getDatabaseSession()) {
            // query database for user
            Query q1 = session.getNamedQuery("getEmployeeResolvedReports");
            q1.setParameter("accountId", accountId);
            List<ReimbursementRequest> requests =  q1.getResultList();
            session.close();
            return requests;
        }
        catch (Exception e) {
            log.error("Database Connection Error - getEmployeeResolvedReports - Reports DAO", e);
        }
        return null;
    }

    /*
       This method is called when an the permissionName in the expenseapp_authenticationcookie
       is set to Manager. This method returns all pending reports. It uses
       a try-with-resources to get a Hibernate Session object and runs the named query
       getAllPendingReports. Returns null if no results found in database.
     */
    public List<ReimbursementRequest> getAllPendingReports() {
        try(Session session = HibernateConnectionUtility.getInstance().
                getDatabaseSession()) {
            // query database for user
            Query q1 = session.getNamedQuery("getAllPendingReports");
            List<ReimbursementRequest> requests =  q1.getResultList();
            session.close();
            return requests;
        }
        catch (Exception e) {
            log.error("Database Connection Error - getAllPendingReports - Reports DAO", e);
        }
        return null;
    }

    /*
       This method is called when an the permissionName in the expenseapp_authenticationcookie
       is set to Manager. This method returns all resolved reports. It uses
       a try-with-resources to get a Hibernate Session object and runs the named query
       getAllResolvedReports. Returns null if no results found in database.
     */
    public List<ReimbursementRequest> getAllResolvedReports() {
        try(Session session = HibernateConnectionUtility.getInstance().
                getDatabaseSession()) {
            // query database for user
            Query q1 = session.getNamedQuery("getAllResolvedReports");
            List<ReimbursementRequest> requests =  q1.getResultList();
            session.close();
            return requests;
        }
        catch (Exception e) {
            log.error("Database Connection Error - getAllResolvedReports - Reports DAO", e);
        }
        return null;
    }

    /*
       This method is used to submit a new reimbursement request. It uses
       a try-with-resources to get a Hibernate Session object and runs the named query
       submitNewReport. A Hibernate Transaction manages the commit/rollback steps.
       Returns false if the insert failed, true if it was successful.
     */
    public boolean submitNewReport(int accountId, BigDecimal amount, String requestDescription) {
        try(Session session = HibernateConnectionUtility.getInstance().
                getDatabaseSession()) {
            // query database for user
            Transaction txn = session.beginTransaction();
            Query q1 = session.createNamedQuery("submitNewReport");
            q1.setParameter(1,accountId);
            q1.setParameter(2,amount);
            q1.setParameter(3,requestDescription);
            int rowsAffected = q1.executeUpdate();
            txn.commit();
            session.close();
            if(rowsAffected == 1) return true;
        }
        catch (Exception e) {
            log.error("Database Connection Error - getAllResolvedReports - Reports DAO", e);
        }
        return false;
    }

    /*
       This method is used to update a reimbursement request's status and approvedBy. It uses
       a try-with-resources to get a Hibernate Session object and runs the named query
       updateReportById. A Hibernate Transaction manages the commit/rollback steps.
       Returns false if the update failed, true if it was successful.
     */
    public boolean updateReport(int accountId, int requestId, int status) {
        try(Session session = HibernateConnectionUtility.getInstance().
                getDatabaseSession()) {
            // query database for reimbursement report
            Query q1 = session.getNamedNativeQuery("updateReportById");
            q1.setParameter(1, status);
            q1.setParameter(2, accountId);
            q1.setParameter(3, requestId);
            Transaction txn = session.beginTransaction();
            int rowsAffected = q1.executeUpdate();
            txn.commit();
            session.close();
            if(rowsAffected == 1) return true;
        }
        catch (Exception e) {
            log.error("Database Connection Error - updateReport - Reports DAO", e);
        }
        return false;
    }

}
