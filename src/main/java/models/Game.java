package models;

import app.Identifiable;
import com.fasterxml.jackson.annotation.JsonProperty;
import dao.CsvDao;

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
@Table(name = "games")
public class Game extends SportEvent {
    @Embedded
    private Score score = new Score();

    public Game() { }

    public Game(@JsonProperty(value = "id") String id,
                @JsonProperty(value = "title") String title,
                @JsonProperty(value = "score") Score score,
                @JsonProperty(value = "date") Date date,
                @JsonProperty(value = "place") String place,
                @JsonProperty(value = "attendance") Attendance attendance) {
        super(id, title, date, place, attendance);
        this.score = score;
    }

    @Override
    public String toString() {
        return super.toString() + " " + score;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }


    @Embeddable
    public static class Score implements Identifiable<String> {

        @Column(name = "first")
        private int first = 0;

        @Column(name = "second")
        private int second = 0;

        @Transient
        @CsvDao.Skip
        private String id = UUID.randomUUID().toString();

        public Score() {}

        public Score(@JsonProperty(value = "first") int first,
                     @JsonProperty(value = "second") int second) {
            this.first = first;
            this.second = second;
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
            return first + ":" + second;
        }

        public int getFirst() {
            return first;
        }

        public int getSecond() {
            return second;
        }

        public void setFirst(int first) {
            this.first = first;
        }

        public void setSecond(int second) {
            this.second = second;
        }
    }
}
