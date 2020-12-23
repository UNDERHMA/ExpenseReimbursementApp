package org.revature.munderhill.expensereimbursementapp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name="ReimbursementRequestStatus")
@Table(name="ReimbursementRequestStatuses")
@Getter
@Setter
@ToString
public class ReimbursementRequestStatus {
    @Id
    @Column(name="reimbursement_request_status_id", columnDefinition="serial primary key")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int reimbursementRequestStatusId;

    @Column(name="reimbursement_request_status_desc")
    private String reimbursementRequestStatusDesc;
}
