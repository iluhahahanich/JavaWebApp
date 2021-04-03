package models.sportEvents;

import clients.CsvDao;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import models.AgeGroup;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class SportEvent {

    private String title;
    
    @CsvDao.Checkable(patten = "^([A-Z][a-z]*[-\\s]*)+$")
    private String place;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private XMLGregorianCalendar date;
    private Attendance attendance;

    public SportEvent(){}

    public SportEvent(@JsonProperty(value = "title") String title,
                      @JsonProperty(value = "date") XMLGregorianCalendar date,
                      @JsonProperty(value = "place") String place,
                      @JsonProperty(value = "attendance") Attendance attendance) {
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

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlRootElement(name = "Attendance")
    public static class Attendance {

        private int children, adults, elderly;

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
    }
}
