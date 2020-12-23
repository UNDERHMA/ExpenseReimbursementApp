package org.revature.munderhill.expensereimbursementapp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@NamedQuery(name="getAccountByUserId", query="from Account where user_id = :userid")
@Entity(name="Account")
@Table(name="Accounts")
@Getter
@Setter
@ToString
public class Account {

    @Id
    @Column(name="account_id", columnDefinition="serial primary key")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int accountId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="middle_name")
    private String middleName;

    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="phone_number")
    private String phoneNumber;

    @OneToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="user_id", referencedColumnName="user_id", columnDefinition="INT")
    private User user;
}
