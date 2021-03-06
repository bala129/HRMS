package com.ideas2it.hrms.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.ideas2it.hrms.dao.EmployeeDao;
import com.ideas2it.hrms.dao.impl.EmployeeDaoImpl;
import com.ideas2it.hrms.exception.AppException;
import com.ideas2it.hrms.model.Attendance;
import com.ideas2it.hrms.model.Designation;
import com.ideas2it.hrms.model.Employee;
import com.ideas2it.hrms.model.Project;
import com.ideas2it.hrms.model.SalaryTracker;
import com.ideas2it.hrms.model.TimeSheet;
import com.ideas2it.hrms.service.AttendanceService;
import com.ideas2it.hrms.service.DesignationService;
import com.ideas2it.hrms.service.EmployeeService;
import com.ideas2it.hrms.service.ProjectService;
import com.ideas2it.hrms.service.SalaryTrackerService;
import com.ideas2it.hrms.service.TimeSheetService;

public class EmployeeServiceImpl implements EmployeeService{

    private EmployeeDao employeeDao = new EmployeeDaoImpl();
    
    /** {@inheritDoc}*/
    public Boolean createEmployee(Employee employee) throws AppException {
        return employeeDao.createEmployee(employee);
    }
    
    public List<Attendance> applyLeave(Employee employee, String message, String dateString) throws AppException {      
        // Set configurations for the email connection to Google's SMTP server using TLS
        Properties props = new Properties();

        props.put("mail.smtp.host", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        // Getting a session with authentication information of sender 
        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                String passwordJumble = "company123admin23ganesh23venkat007789jhihello7sideas23";
                String password = passwordJumble.substring(25, 34);
                return new PasswordAuthentication("ganeshvenkat@ideas2it.com", password);
            }
        });

        try {
            MimeMessage msg = new MimeMessage(session);
            String to = "ganeshvenkat@ideas2it.com";
            msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
            msg.setSubject("Applying for leave : " + dateString);
            msg.setSentDate(new Date());
            msg.setText(message);
            msg.setHeader("XPriority", "1");
            Transport.send(msg);
            System.out.println("Mail has been sent successfully");
        } catch (MessagingException msgEx) {
            System.out.println("Unable to send an email" + msgEx);
        }
        return getAttendanceSheet(employee);
    }
    
    public List<Attendance> getAttendanceSheet(Employee employee) throws AppException {
        AttendanceServiceImpl attendService = new AttendanceServiceImpl();
        return attendService.getAttendanceSheet(employee);
    }
    
    public List<Attendance> markPresent(Employee employee) throws AppException {
        AttendanceServiceImpl attendService = new AttendanceServiceImpl();
        return attendService.markPresent(employee);
    }
    
    /** {@inheritDoc}*/
    public List<Attendance> markAbsent(Employee employee) throws AppException {
        AttendanceServiceImpl attendService = new AttendanceServiceImpl();
        return attendService.markAbsent(employee);
    }
    
    /** {@inheritDoc}*/
    public Boolean updateEmployee(Employee employee) throws AppException {
        return employeeDao.updateEmployee(employee);

    }
    
    /** {@inheritDoc}*/
    public List<Employee> displayEmployees() throws AppException {
        return employeeDao.retrieveEmployees();
    }
    
    public List<Employee> getInactiveEmployees() throws AppException {
        return employeeDao.getInactiveEmployees();
    }
    
    /** {@inheritDoc}*/
    public Boolean deleteEmployee(Integer id) throws AppException {
        return employeeDao.deleteEmployee(id);
    }
    
    /** {@inheritDoc}*/
    public Boolean isEmployeeExist(String emailId) throws AppException {
        return (null == employeeDao.searchEmployee(emailId));
    }
    
    /** {@inheritDoc}*/
    public Employee searchEmployee(String emailId) throws AppException {
        return employeeDao.searchEmployee(emailId);
    }
    
    /** {@inheritDoc}*/
    public List<Designation> getDesignations() throws AppException {
        DesignationService designationService = new DesignationServiceImpl();
        return designationService.displayDesignations();
    }
    
    /** {@inheritDoc}*/
    public List<Project> getEmpProjects(List<TimeSheet> entries) {
        List<Project> projects = new ArrayList<Project>();
        
        for(TimeSheet entry:entries) {
            if(! projects.contains(entry.getProject())) {
                projects.add(entry.getProject());
            }
        }
        return projects;
    }
   
    /** {@inheritDoc}*/
    public List<Project> getAllProjects() throws AppException {
        ProjectService projectService = new ProjectServiceImpl();
        return projectService.getAllProjects();
    }
    
    /** {@inheritDoc}*/
    public Project getProjectById(Integer id) throws AppException {
        ProjectService projectService = new ProjectServiceImpl();
        return projectService.getProjectById(id);
    }

    /** {@inheritDoc}*/
    public boolean createTask(TimeSheet task) throws AppException {
        TimeSheetService sheetService = new TimeSheetServiceImpl();
        return (null != sheetService.createEntry(task));
    }
    
    /** {@inheritDoc}*/
    public Integer calculateNetProfit(LocalDate startDate, LocalDate endDate, 
            Employee employee) throws AppException {
        Integer billAmount = calculateBillAmount(startDate, endDate, employee);
        Integer costToCompany 
            = calculateCostToCompany(startDate, endDate, employee);
        return  billAmount - costToCompany;
    }
    
    /** {@inheritDoc}*/
    public Integer calculateCostToCompany(LocalDate startDate, 
            LocalDate endDate, Employee employee) throws AppException {
        SalaryTrackerService salaryService = new SalaryTrackerServiceImpl();
        Integer costToCompany = 0;
        List<LocalDate> workeddates = calculateBetweenDates(startDate, endDate);
        for (LocalDate workedDate : workeddates) {
            SalaryTracker currentTracker = salaryService.getSalaryTrackerOnDate(
                workedDate, employee.getSalaryTrackers());
            if (isEmpPresent(workedDate, employee)) {
                Integer dailySalary = currentTracker.getSalary()/30;
                costToCompany = costToCompany + dailySalary;
            }
        }
        return costToCompany; 
    }
    
    /** {@inheritDoc}*/
    public Integer calculateBillAmount(LocalDate startDate, LocalDate endDate, 
            Employee employee) {
        SalaryTrackerService salaryService = new SalaryTrackerServiceImpl();
        Integer billAmount = 0;
        List<TimeSheet> currentTimeSheets = getBetweenTimeSheets(startDate, 
                endDate, employee.getTimeSheet());
        for (TimeSheet timesheet : currentTimeSheets) {
            
            SalaryTracker currentTracker = salaryService.getSalaryTrackerOnDate(
                timesheet.getEntryDate(), employee.getSalaryTrackers());
            billAmount
                = timesheet.getWorkedHours() * currentTracker.getHourlyRate();
            
        }
        return billAmount;
    }
    
    /**
     * Used to get the employee time sheet between the two dates 
     * 
     * @param startDate
     *        Starting date of employee time sheet
     * @param endDate
     *        Ending date of employee time sheet
     * @param timesheets
     */
    private List<TimeSheet> getBetweenTimeSheets(LocalDate startDate, 
            LocalDate endDate, List<TimeSheet> timesheets) {
        List<TimeSheet> currentTimeSheets = new ArrayList<TimeSheet>();
        for (TimeSheet timeSheet : timesheets) {
            LocalDate date = timeSheet.getEntryDate();
            
            if ((date.compareTo(startDate) >= 0) 
                    && (date.compareTo(endDate) <= 0)) {
                currentTimeSheets.add(timeSheet);
            }
        }
        return currentTimeSheets;
    }
    
    /**
     * Used to get the dates between the two date
     * 
     * @param startDate
     *        Starting date
     * @param endDate
     *        Ending date 
     */
    private List<LocalDate> calculateBetweenDates(LocalDate startDate, 
            LocalDate endDate) {
            List<LocalDate> totalDates = new ArrayList<LocalDate>();
            while (!startDate.isAfter(endDate)) {
                totalDates.add(startDate);
                startDate = startDate.plusDays(1);
            }
            return totalDates;
        }
    
    /**
     * Returns true if employee has present on this day, otherwise false
     * 
     * @param workedDate
     *        date to check the employee is present
     * @param employee
     *        company employee
     */
    private boolean isEmpPresent(LocalDate workedDate, Employee employee) 
            throws AppException {
        AttendanceService attendanceService = new AttendanceServiceImpl();
        Attendance attendance = null;
        Boolean present = Boolean.FALSE;
        attendance = attendanceService.getAttendance(employee, workedDate);
        if (null !=  attendance) {
            present = attendance.getStatus();
        }
        return present;
    }

    @Override
    public boolean salaryIncrement(String emailId, SalaryTracker salaryTracker)
            throws AppException {
        Employee employee = employeeDao.searchEmployee(emailId);
        salaryTracker.setEmployee(employee);
        employee.setSalary(salaryTracker.getSalary());
        employee.getSalaryTrackers().add(salaryTracker);
        return employeeDao.updateEmployee(employee);
    }

    @Override
    public List<Employee> searchEmployeeByName(String name)
            throws AppException {
        return employeeDao.searchEmployeeByName(name);
    }
}