package com.rashed.testcaseproject.model;

import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity(name = "UseEntity")
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@Accessors(chain = true)
public class UserEntity extends AbstractEntity<UserEntity> implements Serializable {
	private static final long serialVersionUID = 2480720999368774405L;

	/** Employee id of the user from HRM (organogram) */
    @Id
    @NonNull
    @Column(columnDefinition = "varchar(255)")
    private String id;

    /** Full name of the employee (user) who are using the PMM system */
    @NonNull
    private String name;

    @Column(name = "emp_office_id")
    private String employeeOfficeId;

    private String userId;
    /** Login user name */
    private String login;

    /** Default project (id) of this user */
    private String project;

	/**
	 * Name of the office this employee belongs to. <br>
	 * Government treats ministry (মন্ত্রনালয়ের কার্যালয়), division, directorate (অধিদপ্তর),
	 * council, project (প্রকল্প/প্রোগ্রাম) as offices but differentiate them based on type/category.
	 */
	private String office;
	private String officeUnit;
	private String officeUnitPost;

}
