<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<html>
<head>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script
    src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
 <link rel = "stylesheet" type = "text/css" href = "<c:url value='/resources/employeeView.css'/>"/>
 <link rel = "stylesheet" type = "text/css" href = "<c:url value='/resources/bootstrap.css'/>"/>
</head>
<jsp:include page='empHeader.jsp'/>
<body >
<div id="view" style="transition: margin-left .5s; padding: 16px;">
<c:if test="${not empty employeeDetail}">
  <div class="emp">
  <table class="table table-striped ">
    <tr>
        <td>Employee ID</td><td>:</td>
        <td>${employee.id}</td>  
    </tr>
    <tr>
        <td>Name</td> <td>:</td>
        <td>${employee.name}</td>  
    </tr>
    <tr>
        <td>MobileNo</td><td>:</td>
        <td>${employee.mobileNo}</td>
    </tr>
    <tr>  
        <td>Mail</td><td>:</td>
        <td>${employee.emailId}</td>
    </tr>
    <tr>
         <td>Designation</td><td>:</td>
         <td>${employee.designation.name}</td>
    </tr>
    <tr>
         <td>Salary</td><td>:</td>
         <td>${employee.salary}</td>
    </tr>
   </table>        
   <br>
   <br>
   <center><button type="button" data-toggle="modal" data-target="#Update"class="btn btn-success">Edit Profile</button></a></center>                  
 </div>
</c:if>

<c:if test="${not empty timeSheets}">
 <div id="taskInfo" align="center" >
     <center><strong><h3>TimeSheets</h3></strong></center>
     <button type="button" class="btn btn-info btn-lg pull-right " style="margin-bottom: 1%;"
       data-toggle="modal" data-target="#TimeSheetCreate">Add Entry</button>
    <div class="scroll">
    <table class="table table-striped text-center">
    <tr>
        <th class="text-center">Date</th>
        <th class="text-center">Project</th>
        <th class="text-center">Worked Hours</th>
        <th class="text-center">Action</th>
    </tr>
    <c:forEach var= "timeSheet" items= "${timeSheets}">
    <tr>
        <td>${timeSheet.entryDate}</td>
        <td>${timeSheet.project.name}</td>
        <td>${timeSheet.workedHours}</td>
        
          <td>
            <form method="post" action="/hrms/timeSheet/deleteEntry">
            <button type="button" class="btn btn-success btn-sm" data-toggle="modal" data-target="#${timeSheet.id}" >Update</button>
            <input type="hidden" name="id" value = "${timeSheet.id}">   
            </form>
            <div class="modal fade" id="${timeSheet.id}">
            <div class="modal-dialog modal-sm">
            <div class="modal-content">
            <div class="modal-header">
              <h4>Update TimeSheet Entry</h4>
            </div>
            <form method="post" action="/hrms/timeSheet/updateEntry">
            <div class="modal-body">
            <div class="form-group">
              <label>Date</label>
              <input type="text" class="form-control" name="entryDate" value= "${timeSheet.entryDate}" required="required">
            </div>
            <div class="form-group">
              <label>Project Name</label>
                <input type="hidden" name="projectId" value = "${timeSheet.project.id}">   
                <input type="text" class="form-control" name="projectName" value= "${timeSheet.project.name}" readonly="readonly" required="required">
            </div>
            <div class="form-group">
              <label>Hours Worked</label>
              <input type="text" class="form-control" name="workedHours" value= "${timeSheet.workedHours}" maxlength="30" required="required">
            </div>
            <div class="form-group">
              <button type="submit" class="btn btn-primary btn-block btn-lg">Update</button>
              <button type="button" class="btn btn-danger btn-block btn-lg" data-dismiss="modal">Cancel</button>
              <input type="hidden" name="id" value = "${timeSheet.id}">   
              <input type="hidden" name="empEmail" value="${employee.emailId}">
            </div>
            </div>
            </form>
            </div>
            </div>
          </td>
    </tr>
    </c:forEach>
    </table>
    </div>
 </div>
</c:if>

<c:remove var="timeSheets"/>

<c:if test="${not empty timesheetEmpty}">
    <div align="center">
        <h4>${timesheetEmpty}</h4>
        <button type="button" class="btn btn-info btn-lg " 
            data-toggle="modal" data-target="#TimeSheetCreate">Add Entry</button>
    </div>
</c:if>

<c:if test="${not empty projects}">
    <h4 align="center"> <b> History of Projects Worked On </b> </h4> 
    <div id=projectInfo align="center">
    <table class="table table-striped text-center table-bordered">
    <tr>
        <th class="text-center">Project</th>
        <th class="text-center">Client</th>
    </tr>
    <c:forEach var= "project" items= "${projects}">
    <tr>
        <td>${project.name}</td>
        <td>${project.client.name}</td>
    </tr>
    </c:forEach>
    </table>
    </div>
</c:if>

<c:if test="${not empty currentProjects}">
    <h4 align="center"><b>Current Projects</b>  </h4> 
    <div id=projectInfo align="center">
    <table class="table table-striped text-center ">
    <tr>
        <th class="text-center">Project</th>
        <th class="text-center">Client</th>
    </tr>
    <c:forEach var= "project" items= "${currentProjects}">
    <tr>
        <td>${project.name}</td>
        <td>${project.client.name}</td>
    </tr>
    </c:forEach>
    </table>
    </div>
</c:if>
    
<c:if test="${not empty projectEmpty}">
    <div align="center">
        <h4>${projectEmpty}</h4>
    </div>
</c:if>
    
<c:if test="${not empty attendanEmpty}">
    <div align="center">
        <h4>${attendanEmpty}</h4>
            <c:choose>
		    <c:when test="${isChecked=='true'}">
		      <input class="toggle" type="checkbox" name="checkAttendance" checked="checked" onclick="checkAttendance(this)" />
		    </c:when>    
		    <c:when test="${isChecked=='false'}">
		      <input class="toggle" type="checkbox" name="checkAttendance" onclick="checkAttendance(this)" />
		    </c:when>  
		    <c:otherwise>
		      <input class="toggle" type="checkbox" name="checkAttendance" onclick="checkAttendance(this)" />
		    </c:otherwise>
		  </c:choose>
    </div>
</c:if>
    
<c:if test="${not empty attendance}">
 <div id=attendanceInfo align="center">
  <center><strong><h3>Attendance</h3></strong></center>
  <c:choose>
    <c:when test="${isChecked=='true'}">
      <input class="toggle" type="checkbox" name="checkAttendance" checked="checked" onclick="checkAttendance(this)" />
    </c:when>    
    <c:when test="${isChecked=='false'}">
      <input class="toggle" type="checkbox" name="checkAttendance" onclick="checkAttendance(this)" />
    </c:when>  
    <c:otherwise>
      <input class="toggle" type="checkbox" name="checkAttendance" onclick="checkAttendance(this)" />
    </c:otherwise>
  </c:choose>
    <br>
    <br>
    <div class="scroll">
    <table class="table table-striped text-center">
    <tr>
        <th class="text-center">Date</th>
        <th class="text-center">Attendance</th>
    </tr>
    <c:forEach var="attendance" items="${attendance}">
    <tr>
        <td>${attendance.attendDate}</td>
        <c:if test="${attendance.status == true}">
        <td>Present</td>
        </c:if>
        <c:if test="${attendance.status == false}">
        <td>Absent</td>
        </c:if>
    </tr>
    </c:forEach>
    </table>
    </div>
    <button type="button" class="btn btn-success" data-toggle="modal" data-target="#ApplyLeave" >Apply Leave</button>        
 </div>
</c:if>   
       
       <div class="modal fade" id="ApplyLeave" >
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4>Leave Form</h4>
                </div>
                <div class="modal-body">
                    <form action="/hrms/employee/applyLeave" method="post">
                          <div class="form-group">
                              <input type="text" class="form-control" name="leaveEmpId"
                                  value= "${employee.id}" readonly="readonly">
                          
                          </div>
                          <div class="form-group">
                            <input type="text" class="form-control" name="employeeName"
                              value= "${employee.name}" readonly="readonly">
                          </div>
                          <div class="form-group">
	                           <input type="date" class="form-control" name="leaveDate"
	                              required="required" value="<%= java.time.LocalDate.now() %>" >   
                          </div>                         
                          <div class="form-group">
                              <input style="height:200px" type="text" class="form-control" name="leaveReason" placeholder="Reason for applying leave" required="required">
                          </div>
                          <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block btn-lg">Apply Leave</button>
                            <button type="button" class="btn btn-danger btn-block btn-lg" data-dismiss="modal">
                                Cancel</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
     </div>
   
   

    <div class="modal fade" id="Update" >
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4>Update profile info</h4>
                </div>
                <div class="modal-body">
                    <form action="/hrms/employee/updateEmployee" method="post">
                        <div class="form-group">
                                <input type="text" class="form-control" name="name"
                                    value= "${employee.name}" placeholder="Name" pattern="[A-Za-z\s]+" maxlength="30" required="required">
                        </div>
                        <div class="form-group">
                                <input type="text" class="form-control" name="mobileNo"
                                   value= "${employee.mobileNo}" placeholder="Mobile Number" pattern="[6789]{1}[0-9]{9}" maxlength="10" required="required">
                        </div>
                        <div class="form-group">
                                <input type="text" class="form-control" name="emailId"
                                   value= "${employee.emailId}" placeholder="Email Id" required="required">
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block btn-lg">Update</button>
                            <button type="button" class="btn btn-danger btn-block btn-lg" data-dismiss="modal">
                                Cancel</button>
                            <input type="hidden" name="id" value = "${employee.id}">
                            <input type="hidden" name="salary" value = "${employee.salary}">
                            <input type="hidden" name="hourlyRate" value = "${employee.hourlyRate}">
                        </div>
                    </form>
                </div>
            </div>
        </div>
     </div>
     
     <div class="modal fade" id="TimeSheetCreate">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">
                <div class="modal-header">
                    <h4>Add entry</h4>
                </div>
                <div class="modal-body">
                    <form action="/hrms/timeSheet/createEntry" method="post">
                        <div class="form-group">
                            <label>Date</label>
                              <input type="date" class="form-control" name="entryDate" value="<%= java.time.LocalDate.now() %>" readonly>
                        </div>
                        <div class="form-group">
                                <label>Project Name</label>
                                <select class="form-control" name="projectId">
                                    <c:forEach var="project" items="${employee.projects}">
                                         <option value="${project.id}">${project.name}</option>
                                    </c:forEach>
                                </select>
                        </div>
                       <div class="form-group">
                            <label>Hours Worked</label>
                            <div class="form-group">
                              <input type="text" class="form-control" name="workedHours" required="required" maxlength="2">
                            </div>
                        </div>
                        <div class="form-group">
                            <button type="submit" class="btn btn-primary btn-block btn-lg">Save</button>
                            <button type="button" class="btn btn-danger btn-block btn-lg" 
                                data-dismiss="modal">Cancel</button>
                            <input type="hidden" name="empEmail" value="${employee.emailId}">
                        </div>
                    </form>
                </div>
            </div>
        </div>
     </div>
     
</body>

<c:if test="${not empty message}">
<script>
    alert ("${message}");
</script>
<c:remove var="message"/>
</c:if>
<script>
    function checkAttendance(checkbox)
    {
      if (checkbox.checked)
      {        
          window.location = "/hrms/employee/markPresent";
      }
      else {
          window.location = "/hrms/employee/markAbsent";    
      }
    }
    
    function openNav() {
        document.getElementById("mySidenav").style.width = "250px";
        document.getElementById("main").style.marginLeft = "250px";
        document.getElementById("view").style.marginLeft = "250px";
    }

    function closeNav() {
        document.getElementById("mySidenav").style.width = "0";
        document.getElementById("main").style.marginLeft= "0";
        document.getElementById("view").style.marginLeft = "0";
    }
  </script>
</html>
