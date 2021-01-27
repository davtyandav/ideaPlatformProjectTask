package com.github.daviddavtyan;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private final static String AVERAGE_FLIGHT_TIME = "- среднее время полета между городами Владивосток и Тель-Авив - ";
    private final static String PERCENTILE_FLIGHT_TIME = "- 90-й процентиль времени полета между городами  Владивосток и Тель-Авив - ";

    public static void main(String[] args) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        File src = new File(args[0]);
        TicketList ticketList = mapper.readValue(src, TicketList.class);

        List<Duration> times = ticketList.getTickets().stream()
                .map(Main::flightTime)
                .collect(Collectors.toList());

        Duration averageTime = Util.getAverage(times);
        Duration percentileTime = Util.getPercentile(times, 90);

        try (FileWriter file = new FileWriter(args[1], true);) {
            String dataOfFlight = AVERAGE_FLIGHT_TIME + formatDuration(averageTime) + "\n"
                    + PERCENTILE_FLIGHT_TIME + formatDuration(percentileTime) + "\n";
            file.write(dataOfFlight);
        }
    }

    public static Duration flightTime(Ticket ticket) {

        String start = ticket.getDepartureDate() + " " + ticket.getDepartureTime();
        String end = ticket.getArrivalDate() + " " + ticket.getArrivalTime();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");

        LocalDateTime dateStartOfFlight = LocalDateTime.parse(start, formatter);
        LocalDateTime dateLanding = LocalDateTime.parse(end, formatter);

        return Duration.between(dateStartOfFlight, dateLanding);
    }

    public static String formatDuration(Duration time) {
        return time.toHoursPart() + " часов " + time.toMinutesPart() + " минут";
    }
}
