
package business;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.text.SimpleDateFormat;

/**
 *
 * @author n.riley
 */
@Entity
@Table(name="events")
public class Event implements Serializable {
    @Id
    @Column(name="EVT_ID")
    private int evtID;
    @Column(name="EVT_DT")
    @Temporal(TemporalType.DATE)
    private Date evtDT;
    @Column(name="DAY_OF_WEEK")
    private String evtDay;
    @Column(name="EVT_DESC")
    private String evtDesc;
    @Column(name="EVT_NOTES")
    private String evtNotes;
    @Column(name="CLOSED")
    private String evtClosed;
    @Column(name="PASS_LIMIT")
    private int evtLimit;
    @Column(name="LG_AREA")
    private String evtLgArea;
    @Column(name="LG_AREA_NAME")
    private String evtLgName;
    
    public Event(){
        evtID = 0;
        evtDT = null;
        evtDay = "";
        evtDesc = "";
        evtNotes = "";
        evtClosed = "";
        evtLimit = 0;
        evtLgArea = "";
        evtLgName = "";
    }

    public int getEvtID() {return evtID;}
    public Date getEvtDT() {return evtDT;}
    public String getevtDTS() {
        return new SimpleDateFormat("MM-dd-yyyy").
            format(this.evtDT);
    }
    public String getEvtDay() {return evtDay;}
    public String getEvtDesc() {return evtDesc;}
    public String getEvtNotes() {return evtNotes;}
    public String getEvtClosed() {return evtClosed;}
    public int getEvtLimit() {return evtLimit;}
    public String getEvtLgArea() {return evtLgArea;}
    public String getEvtLgName() {return evtLgName;}

    public void setEvtID(int evtID) {this.evtID = evtID;}
    public void setEvtDT(Date evtDT) {this.evtDT = evtDT;}
    public void setEvtDay(String evtDay) {this.evtDay = evtDay;}
    public void setEvtDesc(String evtDesc) {this.evtDesc = evtDesc;}
    public void setEvtNotes(String evtNotes) {this.evtNotes = evtNotes;}
    public void setEvtClosed(String evtClosed) {this.evtClosed = evtClosed;}
    public void setEvtLimit(int evtLimit) {this.evtLimit = evtLimit;}
    public void setEvtLgArea(String evtLgArea) {this.evtLgArea = evtLgArea;}
    public void setEvtLgName(String evtLgName) {this.evtLgName = evtLgName;}
}
