package ru.nsu.gurin.Task5.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import ru.nsu.gurin.Task5.dto.AirportDto;
import ru.nsu.gurin.Task5.dto.BookingDto;
import ru.nsu.gurin.Task5.dto.CheckInDto;
import ru.nsu.gurin.Task5.dto.CityDto;
import ru.nsu.gurin.Task5.dto.FlightDto;
import ru.nsu.gurin.Task5.dto.exceptions.NoSeatsAvailableException;
import ru.nsu.gurin.Task5.model.Aircraft;
import ru.nsu.gurin.Task5.model.FlightDetails;
import ru.nsu.gurin.Task5.repository.AirRepository;
import ru.nsu.gurin.Task5.utils.BookingReferenceGenerator;
import ru.nsu.gurin.Task5.utils.TimeUtils;
import ru.nsu.gurin.Task5.utils.TicketNumberGenerator;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AirService {
    @Autowired
    private AirRepository airRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public void changeLanguage(String lang) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(connection -> {
            try (Statement statement = connection.createStatement()) {
                statement.execute("SET bookings.lang TO '" + lang + "'");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Transactional
    public List<CityDto> findUniqueCity() {
        return airRepository.findUniqueCity();
    }

    @Transactional
    public List<AirportDto> findUniqueAirport() {
        return airRepository.findUniqueAirport();
    }

    @Transactional
    public List<Object[]> findDistinctFlightsByArrivalCity(String arrivalCity) {
        return airRepository.findDistinctFlightsByArrivalCity(arrivalCity);
    }

    @Transactional
    public List<Object[]> findDistinctFlightsByDepartureCity(String departureCity) {
        return airRepository.findDistinctFlightsByDepartureCity(departureCity);
    }

    @Transactional
    public List<FlightDto> findFlightsAirports(String departureAirport, String arrivalAirport,
                                               String departureTime, String fareConditions,
                                               int targetLength) {
        List<Object[]> results = airRepository.findFlightsAirports(departureAirport, arrivalAirport,
                departureTime, fareConditions,
                targetLength);
        return mapToFlightDTOs(results);
    }

    private List<FlightDto> mapToFlightDTOs(List<Object[]> results) {
        List<FlightDto> flights = new ArrayList<>();

        for (Object[] row : results) {
            String departureAirport = (String) row[0];
            String arrivalAirport = (String) row[1];
            String fareConditions = (String) row[2];
            Timestamp[] departuresTime = (Timestamp[]) row[3];
            String[] route = (String[]) row[4];
            String[] routeNo = (String[]) row[5];
            BigDecimal[] amounts = (BigDecimal[]) row[6];

            flights.add(new FlightDto(departureAirport, arrivalAirport, fareConditions,
                    departuresTime, route, routeNo, amounts));
        }

        flights.forEach(FlightDto::adjustDepartureTimes);
        return flights;
    }


    @Transactional
    public Map<String, List<String>> getCityWithAirports() {
        List<Object[]> results = airRepository.findCityWithAirports();
        Map<String, List<String>> cityWithAirports = new HashMap<>();

        for (Object[] result : results) {
            String city = (String) result[0];
            String airportsStr = (String) result[1];
            List<String> airports = Arrays.asList(airportsStr.split(", "));
            cityWithAirports.put(city, airports);
        }

        return cityWithAirports;
    }

    @Transactional
    public CheckInDto checkIn(String ticketNo) {

        CheckInDto checkInDto = airRepository.preCheckIn(ticketNo).get(0);

        int boardingNo = airRepository.findLastBoardingNo(checkInDto.getFlightId());

        airRepository.checkIn(checkInDto.getTicketNo(), checkInDto.getFlightId(), boardingNo + 1, checkInDto.getSeatNo());

        return checkInDto;
    }

    private static final Map<String, Integer> airplaneSeats = new HashMap<>();

    static {
        airplaneSeats.put("Боинг 777-300", 10);
        airplaneSeats.put("Боинг 767-300", 10);
        airplaneSeats.put("Сухой Суперджет-100", 10);
        airplaneSeats.put("Аэробус A320-200", 10);
        airplaneSeats.put("Аэробус A321-200", 10);
        airplaneSeats.put("Аэробус A319-100", 10);
        airplaneSeats.put("Боинг 737-300", 10);
        airplaneSeats.put("Сессна 208 Караван", 10);
        airplaneSeats.put("Бомбардье CRJ-200", 10);
    }

    @Autowired
    private ObjectMapper objectMapper;

    @Transactional
    public List<BookingDto> createBooking(List<String> flightNos,
                                          String name,
                                          String document,
                                          String date,
                                          String fareConditions) {

        String bookRef = BookingReferenceGenerator.generateBookRef();
        while (airRepository.existsByBookRef(bookRef)) {
            bookRef = BookingReferenceGenerator.generateBookRef();
        }

        List<BookingDto> bookingDtos = new ArrayList<>();

        OffsetDateTime offsetDateTime = TimeUtils.extractTime(date);
        BigDecimal totalAmount = BigDecimal.valueOf(0);

        for (var flightNo : flightNos) {
            List<FlightDetails> flightDetailss = getFlightDetails(flightNo);
            bookingDtos.add(new BookingDto(flightDetailss.get(0).getFlightId(), bookRef, null, flightDetailss.get(0).getAmount(), flightNo, name, document, fareConditions));
            totalAmount = totalAmount.add(flightDetailss.get(0).getAmount());
        }

        // Создаем и сохраняем бронирование
        entityManager.createNativeQuery("INSERT INTO bookings (book_ref, book_date, total_amount) VALUES (?, ?, ?)")
                .setParameter(1, bookRef)
                .setParameter(2, offsetDateTime)
                .setParameter(3, totalAmount)
                .executeUpdate();

        for (var bookingDto : bookingDtos) {

            String jsonResult = airRepository.findModel(bookingDto.getFlightId());
            Aircraft aircraft = null;
            try {
                aircraft = objectMapper.readValue(jsonResult, Aircraft.class);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            int currentSeats = airplaneSeats.get(aircraft.getModelRu());
            currentSeats -= 1;
            if (currentSeats < 0) {
                // Возвращаем сообщение о нехватке мест
                bookingDtos.clear();
                bookingDtos.add(new BookingDto(null, null, "Количество мест на рейс " + bookingDto.getFlightId() +
                        " " + aircraft.getModelRu() + " закончилось.", null, bookingDto.getFlightNo(), name, document, fareConditions));
                return bookingDtos;
            } else {
                airplaneSeats.put(aircraft.getModelRu(), currentSeats);

                String ticketNo = TicketNumberGenerator.generateTicketNumber();
                while (airRepository.existsByTicketNo(ticketNo)) {
                    ticketNo = TicketNumberGenerator.generateTicketNumber();
                }

                bookingDto.setTicketNo(ticketNo);

                // Создаем и сохраняем билет
                entityManager.createNativeQuery("INSERT INTO tickets (ticket_no, book_ref, passenger_id, passenger_name) VALUES (?, ?, ?, ?)")
                        .setParameter(1, ticketNo)
                        .setParameter(2, bookRef)
                        .setParameter(3, document)
                        .setParameter(4, name)
                        .executeUpdate();

                // Создаем и сохраняем записи о рейсах для билета
                entityManager.createNativeQuery("INSERT INTO ticket_flights (ticket_no, flight_id, fare_conditions, amount) VALUES (?, ?, ?, ?)")
                        .setParameter(1, ticketNo)
                        .setParameter(2, bookingDto.getFlightId())
                        .setParameter(3, fareConditions)
                        .setParameter(4, bookingDto.getAmount())
                        .executeUpdate();
            }
        }
        return bookingDtos;
    }


    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<FlightDetails> getFlightDetails(String flightNo) {
        String sql = "SELECT * FROM flight_details " +
                "WHERE flight_no = ? ";

        return jdbcTemplate.query(sql, new Object[]{flightNo}, (rs, rowNum) ->
                mapFlightDetails(rs));
    }

    private FlightDetails mapFlightDetails(ResultSet rs) throws SQLException {
        FlightDetails flightDetails = new FlightDetails();
        flightDetails.setFlightId(rs.getInt("flight_id"));
        flightDetails.setFlightNo(rs.getString("flight_no"));
        flightDetails.setDepartureCity(rs.getString("departure_city"));
        flightDetails.setDepartureAirport(rs.getString("departure_airport"));
        flightDetails.setArrivalCity(rs.getString("arrival_city"));
        flightDetails.setArrivalAirport(rs.getString("arrival_airport"));
        flightDetails.setScheduledDeparture(rs.getString("scheduled_departure"));
        flightDetails.setScheduledArrival(rs.getString("scheduled_arrival"));

        // Mapping days_of_week array to List<Integer>
        Integer[] daysOfWeekArray = (Integer[]) rs.getArray("days_of_week").getArray();
        List<Integer> daysOfWeekList = Arrays.asList(daysOfWeekArray);
        flightDetails.setDaysOfWeek(daysOfWeekList);

        flightDetails.setFareConditions(rs.getString("fare_conditions"));
        flightDetails.setAmount(rs.getBigDecimal("amount"));
        return flightDetails;
    }
}
