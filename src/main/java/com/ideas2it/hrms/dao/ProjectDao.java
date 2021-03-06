package com.ideas2it.hrms.dao;

import java.util.List;

import com.ideas2it.hrms.exception.AppException;
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
public interface ProjectDao {
        
    /**
     * Creates a new project 
     * 
     * @param project
     *        new project
     * @return 
     *        new project, if added, null otherwise
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
     * Gets all the projects allocated to the company
     * 
     * @return
     *        all projects allocated to the company
     */
    List<Project> getAllProjects() throws AppException;
    
    /**
     * Updates a project's info
     * 
     * @param project
     *        a project, with updated info 
     * @return
     *        the updated project
     */
    Project updateProject(Project project) throws AppException;
    
    /**
     * Removes a project
     * 
     * @param project
     *        a project
     * @return
     *        the project that was removed
     */
    Project removeProject(Project project) throws AppException;
}