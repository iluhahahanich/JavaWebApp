package models.sportEvents;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Game extends SportEvent {
    private Score score;

    public Game() { }

    public Game(@JsonProperty(value = "title") String title,
                @JsonProperty(value = "score") Score score,
                @JsonProperty(value = "date") XMLGregorianCalendar date,
                @JsonProperty(value = "place") String place,
                @JsonProperty(value = "attendance") Attendance attendance) {
        super(title, date, place, attendance);
        this.score = score;
    }

    @Override
    public String toString() {
        return super.toString() + " " + score;
    }

    public Score getScore() {
        return score;
    }

    public static class Score{
        private int first, second;

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
    }
}
