package com.ideas2it.hrms.service.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.ideas2it.hrms.dao.UserDao;
import com.ideas2it.hrms.dao.impl.UserDaoImpl;
import com.ideas2it.hrms.exception.AppException;
import com.ideas2it.hrms.model.Employee;
import com.ideas2it.hrms.model.User;
import com.ideas2it.hrms.service.EmployeeService;
import com.ideas2it.hrms.service.UserService;

/**
 * <p>
 * The {@code UserServiceImpl} is represents the collection of 
 * the user service functions
 * This act the intermediate between the controller class and Dao
 * </p>
 *
 * @version 1
 * @author Balamurugan M
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();
    
    /** {@inheritDoc}*/
    public Integer createUser(User user) throws AppException {
        String password = user.getPassword();
        String hashedPassword = null;
        
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(password.getBytes());
                  BigInteger hash = new BigInteger(1, md.digest());
                  hashedPassword = hash.toString(16);                 
                  } catch (NoSuchAlgorithmException e) { 
            e.printStackTrace();
           }
        user.setPassword(hashedPassword);
        
        return userDao.createUser(user);
    }
    
    /** {@inheritDoc}*/
    public User searchUser(String userName, String password) 
            throws AppException {
        return userDao.searchUser(userName, password);
    }
    
    /** {@inheritDoc}*/
    public User searchName(String userName) throws AppException {
        return userDao.searchName(userName);
    }
    
    /** {@inheritDoc}*/
    public Employee checkEmployeeDetail(String mailId) throws AppException {
        EmployeeService employeeService = new EmployeeServiceImpl();
        return employeeService.searchEmployee(mailId);
    }

    @Override
    public Employee getEmployee(Integer id) throws AppException {
        EmployeeService employeeService = new EmployeeServiceImpl();
        return employeeService.getEmployeebyUserId(id);
    }
}