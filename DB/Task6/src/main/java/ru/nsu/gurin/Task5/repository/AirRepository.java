package ru.nsu.gurin.Task5.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.nsu.gurin.Task5.dto.AirportDto;
import ru.nsu.gurin.Task5.dto.CheckInDto;
import ru.nsu.gurin.Task5.dto.CityDto;
import ru.nsu.gurin.Task5.model.Flight;

import java.util.List;


@Repository
public interface AirRepository extends JpaRepository<Flight, Long> {

    @Query("SELECT DISTINCT new ru.nsu.gurin.Task5.dto.CityDto(departureCity, arrivalCity) FROM FlightView")
    List<CityDto> findUniqueCity();

    @Query("SELECT DISTINCT new ru.nsu.gurin.Task5.dto.AirportDto(departureAirport, departureAirportName, arrivalAirport, arrivalAirportName) FROM FlightView ")
    List<AirportDto> findUniqueAirport();

    @Query(value = "SELECT city, STRING_AGG(airport_code, ', ') AS airports FROM airports GROUP BY city", nativeQuery = true)
    List<Object[]> findCityWithAirports();

    @Query("SELECT DISTINCT fv.flightNo AS flightNo, " +
            "fv.departureAirport AS departureAirport, " +
            "fv.departureCity AS departureCity, " +
            "fv.arrivalAirport AS arrivalAirport, " +
            "FUNCTION('to_char', fv.scheduledArrival, 'HH24:MI TZH:TZM') AS arrivalTime, " +
            "r.daysOfWeek AS daysOfWeek " +
            "FROM FlightView fv " +
            "JOIN Route r ON fv.flightNo = r.flightNo " +
            "WHERE fv.arrivalCity = :arrivalCity")
    List<Object[]> findDistinctFlightsByArrivalCity(@Param("arrivalCity") String arrivalCity);

    @Query("SELECT DISTINCT fv.flightNo AS flightNo, " +
            "fv.arrivalAirport AS arrivalAirport, " +
            "fv.arrivalCity AS arrivalCity, " +
            "fv.departureAirport AS departureAirport, " +
            "FUNCTION('to_char', fv.scheduledDeparture, 'HH24:MI TZH:TZM') AS departureTime, " +
            "r.daysOfWeek AS daysOfWeek " +
            "FROM FlightView fv " +
            "JOIN Route r ON fv.flightNo = r.flightNo " +
            "WHERE fv.departureCity = :departureCity")
    List<Object[]> findDistinctFlightsByDepartureCity(@Param("departureCity") String departureCity);

    @Query(value = """
        WITH RECURSIVE r AS (
            -- Инициализация с начальной даты
            SELECT
                flight_no,
                departure_city,
                arrival_city,
                departure_airport,
                arrival_airport,
                scheduled_departure,
                scheduled_arrival,
                fare_conditions,
                days_of_week,
                array [CAST(:currentDate AS TIMESTAMP) + scheduled_departure::time] AS array_scheduled_departures,
                array [departure_city::text] AS route_city,
                array [(departure_city || '(' || departure_airport || ')')::text] AS route,
                array [flight_no::text] AS route_no,
                array [amount::numeric] as amounts,
                0 AS len,
                CAST(:currentDate AS TIMESTAMP) AS current_date  -- начальная дата с временем
            FROM (
                SELECT DISTINCT
                    flight_no,
                    departure_city,
                    arrival_city,
                    departure_airport,
                    arrival_airport,
                    scheduled_departure,
                    scheduled_arrival,
                    amount,
                    fare_conditions,
                    days_of_week
                FROM flight_details
                WHERE EXTRACT(DOW FROM CAST(:currentDate AS TIMESTAMP)) = ANY(days_of_week)
                AND fare_conditions = :fareConditions
                ORDER BY scheduled_departure
            )
            WHERE departure_airport = :departureAirport  -- старт

            UNION ALL

            -- Рекурсивная часть
            SELECT
                fd.flight_no,
                fd.departure_city,
                fd.arrival_city,
                fd.departure_airport,
                fd.arrival_airport,
                fd.scheduled_departure,
                fd.scheduled_arrival,
                fd.fare_conditions,
                fd.days_of_week,
                array_append(r.array_scheduled_departures, r.current_date + fd.scheduled_departure::time),
                array_append(r.route_city, fd.departure_city::text),
                array_append(r.route, (fd.departure_city || '(' || fd.departure_airport || ')')::text),
                array_append(r.route_no, fd.flight_no::text),
                array_append(r.amounts, fd.amount::numeric),
                r.len + 1 AS len,
                CASE
                    WHEN (r.scheduled_arrival::time) < (fd.scheduled_departure::time)
                    THEN r.current_date
                    ELSE r.current_date + INTERVAL '1 day'
                END AS current_date  -- обновляем текущую дату
            FROM (
                SELECT DISTINCT
                    flight_no,
                    departure_city,
                    arrival_city,
                    departure_airport,
                    arrival_airport,
                    scheduled_departure,
                    scheduled_arrival,
                    amount,
                    fare_conditions,
                    days_of_week
                FROM flight_details
                WHERE fare_conditions = :fareConditions
                AND EXTRACT(DOW FROM CAST(:currentDate AS TIMESTAMP)) = ANY(days_of_week)
                ORDER BY scheduled_departure
            ) AS fd
            JOIN r ON r.arrival_airport = fd.departure_airport
            AND NOT (fd.arrival_city::text = ANY(r.route_city))
            AND r.len < (:targetLength + 1) -- количество промежуточных точек
            AND EXTRACT(DOW FROM (r.current_date + (fd.scheduled_departure::time))::timestamp) = ANY(fd.days_of_week)
        )
        SELECT DISTINCT
            :departureAirport AS departure_airport,
            :arrivalAirport AS arrival_airport,
            fare_conditions,
            array_scheduled_departures AS departures_time,
            route AS route,
            route_no,
            amounts
        FROM r
        WHERE len = (:targetLength + 1) AND r.departure_airport = :arrivalAirport  -- финиш и длина пути
        LIMIT 50
        """, nativeQuery = true)
    List<Object[]> findFlightsAirports(
            @Param("departureAirport") String departureAirport,
            @Param("arrivalAirport") String arrivalAirport,
            @Param("currentDate") String currentDate,
            @Param("fareConditions") String fareConditions,
            @Param("targetLength") int targetLength);

    @Query("SELECT COUNT(t) > 0 FROM Ticket t WHERE t.ticketNo = :ticketNo")
    boolean existsByTicketNo(@Param("ticketNo") String ticketNo);

    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.bookRef = :bookRef")
    boolean existsByBookRef(@Param("bookRef") String ticketNo);

    @Query("SELECT new ru.nsu.gurin.Task5.dto.CheckInDto(tf.flightId, tf.ticketNo, s.seatNo, fv.flightNo) " +
            "FROM TicketFlight tf " +
            "JOIN FlightView fv ON tf.flightId = fv.flightId " +
            "JOIN Seat s ON fv.aircraftCode = s.aircraftCode " +
            "WHERE tf.ticketNo = :ticketNo " +
            "AND s.fareConditions = tf.fareConditions")
    List<CheckInDto> preCheckIn(@Param("ticketNo") String ticketNo);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO boarding_passes (ticket_no, flight_id, boarding_no, seat_no) " +
            "VALUES (:ticketNo, :flightId, :boardingNo, :seatNo)", nativeQuery = true)
    void checkIn(@Param("ticketNo") String ticketNo,
                 @Param("flightId") Integer flightId,
                 @Param("boardingNo") Integer boardingNo,
                 @Param("seatNo") String seatNo);

    @Query(value = "SELECT boarding_no FROM boarding_passes WHERE flight_id = :flightId ORDER BY boarding_no DESC LIMIT 1", nativeQuery = true)
    int findLastBoardingNo(@Param("flightId") Integer flightId);

    @Query(value = """
            select ad.model from flights_v fv
            join aircrafts_data ad on fv.aircraft_code = ad.aircraft_code
            where fv.flight_id = :flightId
            """, nativeQuery = true)
    String findModel(@Param("flightId") Integer flightId);
}
