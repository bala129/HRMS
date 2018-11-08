package com.ideas2it.hrms.service;

import java.util.List;

import com.ideas2it.hrms.exception.AppException;
import com.ideas2it.hrms.model.Employee;
import com.ideas2it.hrms.model.Project;

/**
 * <p>
 * Provides an interface for basic CRUD operations on the Project Model:
 * Add new project, Get project, Get all projects,
 * Update Project, Remove project
 * </p>
 *
 * @author Ganesh Venkat S
 */
public interface ProjectService {
    
    /**
     * Creates a new project 
     * 
     * @param project
     *       new project
     * @return 
     *       new project, if added, null otherwise
     */
    Project createProject(Project project) throws AppException;
    
    /**
     * Gets a project 
     *
     * @param id
     *        id of the requested project
     * @return
     *        requested project, if exists, null otherwise
     */
    Project getProjectById(Integer id) throws AppException;
    
    /**
     * Gets all the projects allocated to company
     * 
     * @return
     *        list of all projects
     */
    List<Project> getAllProjects() throws AppException;
    
    /**
     * Updates a project's details
     * 
     * @param project
     *        project to update
     */
    Project updateProject(Project project) throws AppException;
    
    /**
     * Removes a project
     * 
     * @param project
     *        project to remove
     */
    Project removeProject(Project project) throws AppException;
    
    /**
     * Calculates the total billable amount for a project
     * 
     * @param project
     *        billable project 
     */
    Integer calculateBudget(Project project);
    
    /**
     * Calculates the total billable amount for a project, for a single employee
     * 
     * @param project
     *        billable project 
     * @param employee
     *        employee working on the project
     */
    Integer calculateBillableAmount(Project project, Employee employee);
}