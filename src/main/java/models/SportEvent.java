package models;

import dao.CsvDao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import app.Identifiable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class SportEvent implements Identifiable<String> {
    private String id = "";

    private String title = "";
    
    @CsvDao.Checkable(patten = "^([A-Z][a-z]*[-\\s]*)*$")
    private String place = "";

    @JsonFormat(pattern = "yyyy-MM-dd")
    private XMLGregorianCalendar date;

    {
        try {
            date = DatatypeFactory.newInstance().newXMLGregorianCalendar("2000-01-01");
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
    }

    private Attendance attendance = new Attendance();

    public SportEvent(){}

    public SportEvent(@JsonProperty(value = "id") String id,
                      @JsonProperty(value = "title") String title,
                      @JsonProperty(value = "date") XMLGregorianCalendar date,
                      @JsonProperty(value = "place") String place,
                      @JsonProperty(value = "attendance") Attendance attendance) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.place = place;
        this.attendance = attendance;
    }

    @Override
    public String toString() {
        return title +
                " " + date.getDay() +
                "-" + date.getMonth() +
                "-" + date.getYear() +
                " " + place +
                " " + attendance;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPlace() {
        return place;
    }

    public XMLGregorianCalendar getDate() {
        return date;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setDate(XMLGregorianCalendar date) {
        this.date = date;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "Attendance")
    public static class Attendance implements Identifiable<String>{

        private int children = 0, adults = 0, elderly = 0;

        @CsvDao.Skip
        private String id = UUID.randomUUID().toString();

        public Attendance(){}

        public Attendance(@JsonProperty(value = "children") int children,
                          @JsonProperty(value = "adults") int adults,
                          @JsonProperty(value = "elderly") int elderly) {
            if (children == -1 || adults == -1 || elderly == -1) {
                throw new IllegalArgumentException("arguments for SportEvent can't be null");
            }
            this.children = children;
            this.adults = adults;
            this.elderly = elderly;
        }

        public int getAttendanceByAgeGroup(AgeGroup ageGroup){
            return switch (ageGroup){
                case CHILD -> children;
                case ADULT -> adults;
                case OLD -> elderly;
            };
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
            return children + "_" + adults + "_" + elderly;
        }

        @JsonIgnore
        public int getTotalAttendance() {
            return children + adults + elderly;
        }

        public int getChildren() {
            return children;
        }

        public int getAdults() {
            return adults;
        }

        public int getElderly() {
            return elderly;
        }

        public void setChildren(int children) {
            this.children = children;
        }

        public void setAdults(int adults) {
            this.adults = adults;
        }

        public void setElderly(int elderly) {
            this.elderly = elderly;
        }
    }

}
