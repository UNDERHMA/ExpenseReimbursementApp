package org.revature.munderhill.expensereimbursementapp.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.revature.munderhill.expensereimbursementapp.DAOs.ReportDAO;
import org.revature.munderhill.expensereimbursementapp.entities.ReimbursementRequest;
import org.revature.munderhill.expensereimbursementapp.models.JsonCredential;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.util.List;

@Log4j2
@WebServlet(name = "ReportServlet", urlPatterns = "/Reports")
public class ReportServlet extends HttpServlet {

    /*
        Process http get requests for the pendingreimbursementrequests.html and
        resolvedreimbursementrequests.html pages. First, the helper method
        getJsonCredential searches the request cookies for the expenseapp_authenticationcookie.
        If the cookie is not null, the permissionName() variable of the JsonCredential
        is checked, to see if the user is a "Manager" or an "Employee." After that
        the origin query parameter of the get request is checked to see whether the
        request came from pendingreimbursementrequests.html or resolvedreimbursementrequests.html.
        Depending on the origin, a different reportDAO method is called to retrieve the
        appropriate report list. The report list is then converted to json and added to
        the response body, and the response status is set to 200.If the expenseapp_authenticationcookie
        is null, the response returns without any data added.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        JsonCredential jsonCredential = getJsonCredential(request);
        List<ReimbursementRequest> reimbursementRequestList = null;

        if(jsonCredential != null) {
            ReportDAO reportDAO = new ReportDAO();
            if(jsonCredential.getPermissionName().equals("Employee")) {
                if(request.getParameter("origin").equals
                        ("/Expense_Reimbursements_App_war/pendingreimbursementrequests.html")) {
                    reimbursementRequestList = reportDAO.getEmployeePendingReports(jsonCredential.getAccountId());
                }
                else if(request.getParameter("origin").equals
                        ("/Expense_Reimbursements_App_war/resolvedreimbursementrequests.html")) {
                    reimbursementRequestList = reportDAO.getEmployeeResolvedReports(jsonCredential.getAccountId());
                }
            }
            else if(jsonCredential.getPermissionName().equals("Manager")) {
                if(request.getParameter("origin").equals
                        ("/Expense_Reimbursements_App_war/pendingreimbursementrequests.html")) {
                    reimbursementRequestList = reportDAO.getAllPendingReports();
                }
                else if(request.getParameter("origin").equals
                        ("/Expense_Reimbursements_App_war/resolvedreimbursementrequests.html")) {
                    reimbursementRequestList = reportDAO.getAllResolvedReports();
                }
            }
        }

        if(reimbursementRequestList != null) {
            ObjectMapper om = new ObjectMapper();
            String jsonResponse = om.writeValueAsString(reimbursementRequestList);
            response.setStatus(200);
            response.setContentType("application/json");
            response.getWriter().write(jsonResponse);
        }
    }

    /*
       Processes http post requests, that submit reimbursement requests.
       First, the helper method getJsonCredential searches the request cookies for
       the expenseapp_authenticationcookie. If the credential is valid, the accoundId
       from the credential is used, in combination with the requestBody's amount
       and description values (parsed from requestBody and decoded), to call the
       ReportDAO's submitNewReport method. If the method returns true, the
       response status is set to 201, otherwise, the response status is set to 500. If
       the JsonCredential is null, response status is set to 500.
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // get AccountID from Cookie
        JsonCredential jsonCredential = getJsonCredential(request);
        if(jsonCredential != null) {
            int accountId = jsonCredential.getAccountId();
            // process POST request body to get amount and requestDescription
            String requestBody = "";
            if ("POST".equalsIgnoreCase(request.getMethod())) {
                requestBody = IOUtils.toString(request.getReader());
                requestBody = URLDecoder.decode(requestBody, "UTF-8");
            }
            String[] amountAndDescription = requestBody.split("&");
            BigDecimal amount = new BigDecimal(amountAndDescription[0].substring(7));
            String requestDescription = amountAndDescription[1].substring(19);
            // persist data and return successful or internal service error
            ReportDAO reportDAO = new ReportDAO();
            boolean successful = reportDAO.submitNewReport(accountId, amount, requestDescription);
            if(successful) response.setStatus(201);
            else response.setStatus(500);

        }
        else{
            response.setStatus(500);
        }
    }

    /*
       Processes http put requests, that either approve or deny reimbursement requests.
       First, the helper method getJsonCredential searches the request cookies for
       the expenseapp_authenticationcookie. If the credential is valid, the accoundId
       from the credential is used, in combination with the requestBody's requestId
       and status values (parsed from requestBody and decoded), to call the
       ReportDAO's updateReport method. If the method returns true, the
       response status is set to 201, otherwise, the response status is set to 500. If
       the JsonCredential is null, response status is set to 500.
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // get AccountID from Cookie
        JsonCredential jsonCredential = getJsonCredential(request);
        if(jsonCredential != null) {
            int accountId = jsonCredential.getAccountId();
            // process PUT request body to get requestID and status
            String requestBody = "";
            if ("PUT".equalsIgnoreCase(request.getMethod())) {
                requestBody = IOUtils.toString(request.getReader());
                requestBody = URLDecoder.decode(requestBody, "UTF-8");
            }
            String[] requestIdAndStatus = requestBody.split("&");
            int requestId = Integer.parseInt(requestIdAndStatus[0].substring(10));
            int status = Integer.parseInt(requestIdAndStatus[1].substring(7));
            // persist data and return successful or internal service error
            ReportDAO reportDAO = new ReportDAO();
            boolean successful = reportDAO.updateReport(accountId,requestId,status);
            if(successful) response.setStatus(201);
            else response.setStatus(500);

        }
        else{
            response.setStatus(500);
        }
    }

    /*
      Helper method for doGet, doPost and doPut methods. Searches the request's
      cookies for an expenseapp_authenticationcookie, decodes the cookie if found
      and converts the decoded cookie String into a JsonCredential which is returned.
      This method returns null if any error occurs with the cookie decoding and object
      conversion, or if a expenseapp_authenticationcookie does not exist in the request.
     */
    public JsonCredential getJsonCredential(HttpServletRequest request) {
        JsonCredential jsonCredential = null;
        Cookie[] cookies = request.getCookies();
        ObjectMapper om = new ObjectMapper();
        for(Cookie c : cookies) {
            if (c.getName().equals("expenseapp_authenticationcookie")) {
                Cookie authCookie = c;
                try {
                    String decodedValue = URLDecoder.decode(authCookie.getValue(), "UTF-8");
                    jsonCredential = om.readValue(decodedValue, JsonCredential.class);
                } catch (Exception e) {
                    log.error("URL Decoding Error - getJsonCredential - ReportServlet", e);
                }
            }
        }
        return jsonCredential;
    }

}
