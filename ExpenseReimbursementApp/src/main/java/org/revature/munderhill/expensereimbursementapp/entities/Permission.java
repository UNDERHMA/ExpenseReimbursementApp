package org.revature.munderhill.expensereimbursementapp.entities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name="Permission")
@Table(name="Permissions")
@Getter
@Setter
@ToString
public class Permission {

    @Id
    @Column(name="permission_id", columnDefinition="serial primary key")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int permissionId;

    @Column(name="permission_name")
    private String permissionName;

}
