package models;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Game extends SportEvent {
    private Score score = new Score();

    public Game() { }

    public Game(@JsonProperty(value = "id") String id,
                @JsonProperty(value = "title") String title,
                @JsonProperty(value = "score") Score score,
                @JsonProperty(value = "date") XMLGregorianCalendar date,
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

    public static class Score{
        private int first = 0, second = 0;

        public Score() {}

        public Score(@JsonProperty(value = "first") int first,
                     @JsonProperty(value = "second") int second) {
            this.first = first;
            this.second = second;
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
