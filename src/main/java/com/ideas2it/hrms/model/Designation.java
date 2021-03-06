package com.ideas2it.hrms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.SQLDelete;

/**
 * Used to get the Designation of the employee
 * Not a list of designation, only it's store the single designation detail
 * And used to get the list of employee have a same designation
 * 
 * @author Balamurugan M
 *
 */
@Entity
@Table(name="designation")
@SQLDelete(sql="update designation set expired_date = current_date() where id=?")
@FilterDef(name = "designationFilter", defaultCondition=" expired_date is null")
@Filter(name = "designationFilter") 
public class Designation {
    
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="name")
    private String name;

    @OneToMany(mappedBy="designation", fetch=FetchType.EAGER)
    private List<Employee> employees = new ArrayList<Employee>();

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}