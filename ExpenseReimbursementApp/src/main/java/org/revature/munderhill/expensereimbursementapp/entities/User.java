package org.revature.munderhill.expensereimbursementapp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;

@NamedQuery(name="loginUser", query="from User where username = :username AND " +
        "password = :password")
@Entity(name="User")
@Table(name="Users")
@Getter
@Setter
@ToString
public class User {

    @Id
    @Column(name="user_id", columnDefinition="serial primary key")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int userId;

    @ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    @JoinColumn(name="permission_id", referencedColumnName="permission_id", columnDefinition = "INT")
    private Permission permission;

    @Column(name="last_login")
    private Timestamp lastLogin;

}
