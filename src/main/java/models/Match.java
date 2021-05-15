package models;

import dao.CsvDao;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Match extends SportEvent {

    @CsvDao.Checkable(patten = "^([A-Z][a-z]*[-'\\s]*)*$")
    private String winner = "";

    public Match() {}

    public Match(@JsonProperty(value = "id") String id,
                 @JsonProperty(value = "title") String title,
                 @JsonProperty(value = "winner") String winner,
                 @JsonProperty(value = "date") XMLGregorianCalendar date,
                 @JsonProperty(value = "place") String place,
                 @JsonProperty(value = "attendance") Attendance attendance) {
        super(id, title, date, place, attendance);
        this.winner = winner;
    }

    @Override
    public String toString() {
        return super.toString() + " " + winner;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }
}
