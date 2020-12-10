
package business;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

/**
 *
 * @author n.riley
 */
@Entity
@Table(name="passes")
public class Passes implements Serializable {
    @Id
    @Column(name="PASS_ID")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long passID;
    @Column(name="EVT_DT")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date passDt;
    @Column(name="FIELD_ESCORT")
    private String escort;
    @Column(name="DEPT_ID")
    private int deptID;
    @Column(name="NO_IN_GROUP")
    private int groupNum;
    @Column(name="GROUP_NAME")
    private String groupNm;
    @Column(name="EMP_ID")
    private int empID;
    
    public Passes(){
        passID = 0;
        escort = "";
        deptID = 0;
        groupNum = 0;
        groupNm = "";
        empID = 0;
    }

    public long getPassID() {return passID;}
    public Date getPassDt() {return passDt;}
    public String getEscort() {return escort;}
    public int getDeptID() {return deptID;}
    public int getGroupNum() {return groupNum;}
    public String getGroupNm() {return groupNm;}
    public int getEmpID() {return empID;}

    public void setPassID(long passID) {this.passID = passID;}
    public void setPassDt(Date passDt) {this.passDt = passDt;}
    public void setEscort(String escort) {this.escort = escort;}
    public void setDeptID(int deptID) {this.deptID = deptID;}
    public void setGroupNum(int groupNum) {this.groupNum = groupNum;}
    public void setGroupNm(String groupNm) {this.groupNm = groupNm;}
    public void setEmpID(int empID) {this.empID = empID;}
    
    public String isDuplicate(Passes pass) {
        String msg = "";
        List<Passes> passList = PassesDB.getPasses();
        for(Passes p:passList ) {
            if(p.getGroupNm().equalsIgnoreCase(pass.getGroupNm()) &&
               p.getGroupNum() == pass.getGroupNum()              &&
               p.getDeptID() == pass.getDeptID()                  &&
               p.getPassDt().equals(pass.getPassDt())             &&
               p.getEscort().equalsIgnoreCase(pass.getEscort())) {
                msg = "Duplicate reservation already in the system.<br>";
            } 
        }  
    return msg;
    }
    public String isValid() {
        String msg = "";
        if(groupNum < 0){msg += "Group input is invalid.<br>";}
        if(groupNum > 8){msg += "Group exceeds max limit.<br>";}
        if(groupNm.isEmpty()){msg += "Group name is required.<br>";}
        if(deptID <= 0) {msg += "Choose a department.<br>";} 
        return msg;
    }
}
