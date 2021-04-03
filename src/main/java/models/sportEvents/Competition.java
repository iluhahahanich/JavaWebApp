package models.sportEvents;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.datatype.XMLGregorianCalendar;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Competition extends SportEvent {

    Pedestal pedestal;

    public Competition() { }

    public Competition(@JsonProperty(value = "title") String title,
                       @JsonProperty(value = "pedestal") Pedestal pedestal,
                       @JsonProperty(value = "date") XMLGregorianCalendar date,
                       @JsonProperty(value = "place") String place,
                       @JsonProperty(value = "attendance") Attendance attendance) {
        super(title,  date, place, attendance);
        this.pedestal = pedestal;
    }

    @Override
    public String toString() {
        return super.toString() + " " + pedestal;
    }

    public Pedestal getPedestal() {
        return pedestal;
    }

    public static class Pedestal {
        private String gold, silver, bronze;

        public Pedestal() {}

        public Pedestal(@JsonProperty(value = "gold") String gold,
                        @JsonProperty(value = "silver") String silver,
                        @JsonProperty(value = "bronze") String bronze) {
            this.gold = gold;
            this.silver = silver;
            this.bronze = bronze;
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
    }
}

