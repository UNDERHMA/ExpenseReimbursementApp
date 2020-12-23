package org.revature.munderhill.expensereimbursementapp.DAOs;

import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.revature.munderhill.expensereimbursementapp.entities.Account;
import org.revature.munderhill.expensereimbursementapp.entities.User;
import org.revature.munderhill.expensereimbursementapp.hibernate.HibernateConnectionUtility;
import org.revature.munderhill.expensereimbursementapp.models.JsonCredential;

@Log4j2
public class LoginDAO {

    /*
        This method attempts a login when the LoginServlet receives a POST
        request. This method uses try-with-resources to get a Hibernate Session
        object, and uses the loginUser named query to attempt a login. If successful,
        this method returns a JsonCredential (an object with variables that is
        ready to be converted to JSON for responses).Returns null if no
       result found in database.
     */
    public JsonCredential login(String userName, String password) {
        try(Session session = HibernateConnectionUtility.getInstance().
                getDatabaseSession()) {
            // query database for user
            Query q1 = session.getNamedQuery("loginUser");
            q1.setParameter("username", userName);
            q1.setParameter("password", password);
            User user = (User) q1.getSingleResult();
            // query database for account
            Query q2 = session.getNamedQuery("getAccountByUserId");
            q2.setParameter("userid", user.getUserId());
            Account account = (Account) q2.getSingleResult();
            session.close();
            // return JsonCredential class, an object that can be a Json cookie token
            if(user != null && account != null){
                return new JsonCredential(account.getAccountId(),
                        user.getUserId(),user.getPermission().getPermissionName());
            }
        }
        catch (Exception e) {
            log.error("Database Connection Error - Login DAO", e);
        }
        return null;
    }
}
