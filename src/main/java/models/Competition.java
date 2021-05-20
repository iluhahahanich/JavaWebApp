package models;

import app.Identifiable;
import com.fasterxml.jackson.annotation.JsonProperty;
import dao.CsvDao;
import jdk.jfr.Enabled;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.Date;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
@Entity
@Table(name = "competitions")
@NamedQuery(name = "Competition.getAll", query = "SELECT c FROM Competition c")
public class Competition extends SportEvent {

    @OneToOne (optional = false, cascade = CascadeType.ALL)
    @JoinColumn (name = "pedestal_id")
    Pedestal pedestal = new Pedestal();

    public Competition() { }

    public Competition(@JsonProperty(value = "id") String id,
                       @JsonProperty(value = "title") String title,
                       @JsonProperty(value = "pedestal") Pedestal pedestal,
                       @JsonProperty(value = "date") Date date,
                       @JsonProperty(value = "place") String place,
                       @JsonProperty(value = "attendance") Attendance attendance) {
        super(id, title,  date, place, attendance);
        this.pedestal = pedestal;
    }

    @Override
    public String toString() {
        return super.toString() + " " + pedestal;
    }


    public Pedestal getPedestal() {
        return pedestal;
    }

    public void setPedestal(Pedestal pedestal) {
        this.pedestal = pedestal;
    }

    @Entity
    @Table(name = "pedestals")
    public static class Pedestal implements Identifiable<String> {
        private String gold = "", silver = "", bronze = "";

        @OneToOne (optional=false, mappedBy="pedestal")
        @CsvDao.Skip
        Competition owner;

        @Id
        @GeneratedValue
        @CsvDao.Skip
        private String id = UUID.randomUUID().toString();

        public Pedestal() {}

        public Pedestal(@JsonProperty(value = "gold") String gold,
                        @JsonProperty(value = "silver") String silver,
                        @JsonProperty(value = "bronze") String bronze) {
            this.gold = gold;
            this.silver = silver;
            this.bronze = bronze;
        }

        @Override
        public String getId() {
            return id;
        }

        @Override
        public void setId(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return gold + ":" + silver + ":" + bronze;
        }

        public String getGold() {
            return gold;
        }

        public String getSilver() {
            return silver;
        }

        public String getBronze() {
            return bronze;
        }

        public void setGold(String gold) {
            this.gold = gold;
        }

        public void setSilver(String silver) {
            this.silver = silver;
        }

        public void setBronze(String bronze) {
            this.bronze = bronze;
        }
    }
}

