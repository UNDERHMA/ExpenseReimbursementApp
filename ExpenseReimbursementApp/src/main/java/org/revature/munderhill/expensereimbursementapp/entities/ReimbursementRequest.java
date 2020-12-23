package org.revature.munderhill.expensereimbursementapp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.math.BigDecimal;


@NamedQueries(
        value={
                @NamedQuery(name="getEmployeePendingReports", query="from ReimbursementRequest " +
                        "where account_submitted = :accountId AND reimbursement_request_status = 1" +
                        "ORDER BY reimbursement_request_id"),
                @NamedQuery(name="getEmployeeResolvedReports", query="from ReimbursementRequest " +
                        "where account_submitted = :accountId AND reimbursement_request_status IN (2,3)" +
                        "ORDER BY reimbursement_request_id"),
                @NamedQuery(name="getAllPendingReports", query="from ReimbursementRequest " +
                        "where reimbursement_request_status = 1" +
                        "ORDER BY reimbursement_request_id"),
                @NamedQuery(name="getAllResolvedReports", query="from ReimbursementRequest " +
                        "where reimbursement_request_status IN (2,3)" +
                        "ORDER BY reimbursement_request_id"),
                @NamedQuery(name="getTestReport", query="from ReimbursementRequest " +
                        "where request_description = 'testX4#321'")
        }
)
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "submitNewReport",
                query = "Insert INTO ReimbursementRequests (account_submitted, account_reviewed, " +
                        "request_amount, request_description, reimbursement_request_status)" +
                        "VALUES (?, NULL, ?, ?, 1) "
        ),
        @NamedNativeQuery(
                name = "updateReportById",
                query = "UPDATE ReimbursementRequests SET reimbursement_request_status = ?, " +
                        "account_reviewed = ? WHERE reimbursement_request_id = ?"
        ) ,
        @NamedNativeQuery(
                name="deleteTestReport",
                query="DELETE FROM ReimbursementRequests " +
                "WHERE request_description = 'testX4#321'")
})
@Entity(name="ReimbursementRequest")
@Table(name="ReimbursementRequests")
@Getter
@Setter
@ToString
public class ReimbursementRequest {

    @Id
    @Column(name="reimbursement_request_id", columnDefinition="serial primary key")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int reimbursementRequestId;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="account_submitted",
            referencedColumnName="account_id",
            columnDefinition = "INT")
    private Account accountSubmitted;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="account_reviewed",
            referencedColumnName="account_id",
            columnDefinition = "INT")
    private Account accountReviewed;

    @Column(name="request_amount")
    private BigDecimal amount;

    @Column(name="request_description")
    private String requestDescription = "";

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="reimbursement_request_status",
            referencedColumnName="reimbursement_request_status_id", columnDefinition = "INT")
    private ReimbursementRequestStatus reimbursementRequestStatus;
}
