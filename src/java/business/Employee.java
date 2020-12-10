
package business;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 *
 * @author n.riley
 */
@Entity
@Table(name="employee")
@NamedQuery(name="dbget_Employee",
        query="from Employee where EMP_ID = :empid")
public class Employee implements Serializable {
    @Id
    @Column(name="EMP_ID")
    private int empID;
    @Column(name="LAST_NAME")
    private String lname;
    @Column(name="FIRST_NAME")
    private String fname;
    @Column(name="MIDDLE_INITIAL")
    private String midname;
    @Column(name="HIRE_DATE")
    @Temporal(TemporalType.DATE)
    private Date hireDate;
    @Column(name="DEPT_ID")
    private int deptID;
    @Column(name="PASSWORD")
    private int password;
    @Column(name="ADMIN")
    private String admin;
    @Transient
    private int pwdAttempt;
    
    public Employee() {
        empID = 0;
        lname = "";
        fname = "";
        midname = "";
        hireDate = null;
        deptID = 0;
        password = 0;
        admin = "";
    }

    public int getEmpID() {return empID;}
    public String getLname() {return lname;}
    public String getFname() {return fname;}
    public String getMidname() {return midname;}
    public String getFullname() {return fname+" "+midname+" "+lname;}
    public Date getHireDate() {return hireDate;}
    public int getDeptID() {return deptID;}
    public int getPassword() {return password;}
    public String getAdmin() {return admin;}
    public int getPwdAttempt() {return pwdAttempt;}

    public void setEmpID(int empID) {this.empID = empID;}
    public void setLname(String lname) {this.lname = lname;}
    public void setFname(String fname) {this.fname = fname;}
    public void setMidname(String midname) {this.midname = midname;}
    public void setHireDate(Date hireDate) {this.hireDate = hireDate;}
    public void setDeptID(int deptID) {this.deptID = deptID;}
    public void setPassword(int password) {this.password = password;}
    public void setAdmin(String admin) {this.admin = admin;}
    public void setPwdAttempt(int pwdAttempt) {this.pwdAttempt = pwdAttempt;}
    
    public boolean isAuthenticated() {
        boolean result = false;
        if (password > 0) {if (password == pwdAttempt) {result = true;}}
        return result;
    }
}
