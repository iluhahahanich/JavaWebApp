package handlers;

import models.AgeGroup;
import models.sportEvents.SportEvent;

import java.time.DayOfWeek;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SportEventsHandler {
    public static List<?> getSortedByAgeGroupAttendance(List<? extends SportEvent> events, AgeGroup group){
        return events.stream()
                .sorted(Comparator.comparingInt(e -> e.getAttendance().getAttendanceByAgeGroup(group)))
                .collect(Collectors.toList());
    }

    public static int getAverageByDayOfWeek(List<? extends SportEvent> events, DayOfWeek dayOfWeek){
        var attendance = events.stream()
                .filter(e -> dayOfWeek.equals(DayOfWeek.of(e.getDate().toGregorianCalendar().get(Calendar.DAY_OF_WEEK))))
                .mapToInt(e -> e.getAttendance().getTotalAttendance())
                .toArray();
        return attendance.length != 0 ? Arrays.stream(attendance).sum() / attendance.length : 0;
    }
}
