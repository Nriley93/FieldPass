
package business;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author n.riley
 */
@Entity
@Table(name="department")
public class Department implements Serializable {
    @Id
    @Column(name="DEPT_ID")
    private int deptID;
    @Column(name="DEPTNAME")
    private String deptNm;
    
    public Department(){
        deptID = 0;
        deptNm = "";
    }

    public int getDeptID() {return deptID;}
    public String getDeptNm() {return deptNm;}
    
    public void setDeptID(int deptID) {this.deptID = deptID;}
    public void setDeptNm(String deptNm) {this.deptNm = deptNm;}
 
}
