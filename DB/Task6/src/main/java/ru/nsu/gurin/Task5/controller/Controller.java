package ru.nsu.gurin.Task5.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.nsu.gurin.Task5.dto.AirportDto;
import ru.nsu.gurin.Task5.dto.BookingDto;
import ru.nsu.gurin.Task5.dto.CheckInDto;
import ru.nsu.gurin.Task5.dto.CityDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.gurin.Task5.dto.FlightDto;
import ru.nsu.gurin.Task5.service.AirService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/air")
public class Controller {

    @Autowired
    private AirService airService;

    @GetMapping("/cities")
    public List<CityDto> getUniqueCity(@RequestParam(value = "lang", defaultValue = "ru") String lang) {
        airService.changeLanguage(lang);
        return airService.findUniqueCity();
    }

    @GetMapping("/airports")
    public List<AirportDto> getUniqueAirport(@RequestParam(value = "lang", defaultValue = "ru") String lang) {
        airService.changeLanguage(lang);
        return airService.findUniqueAirport();
    }

    @GetMapping("/airportWithCity")
    public Map<String, List<String>> getCityWithAirports(@RequestParam(value = "lang", defaultValue = "ru") String lang) {
        airService.changeLanguage(lang);
        return airService.getCityWithAirports();
    }

    @GetMapping("/inboundFlights")
    public List<Object[]> getFlightsByArrivalCity(@RequestParam String arrivalCity) {
        return airService.findDistinctFlightsByArrivalCity(arrivalCity);
    }

    @GetMapping("/outboundFlights")
    public List<Object[]> getFlightsByDepartureCity(@RequestParam String departureCity) {
        return airService.findDistinctFlightsByDepartureCity(departureCity);
    }

    @GetMapping("/findFlightsByAirport")
    public List<FlightDto> getFlightsByAirport(
            @RequestParam("departureAirport") String departureAirport,
            @RequestParam("arrivalAirport") String arrivalAirport,
            @RequestParam("departureTime") String departureTime,
            @RequestParam("fareConditions") String fareConditions,
            @RequestParam("targetLength") int targetLength) {
        return airService.findFlightsAirports(
                departureAirport,
                arrivalAirport,
                departureTime,
                fareConditions,
                targetLength
        );
    }

    @PostMapping("/book")
    public ResponseEntity<?> createBooking(
            @RequestParam("flightNo") List<String> flightNos,
            @RequestParam("name") String name,
            @RequestParam("document") String document,
            @RequestParam("date") String date,
            @RequestParam("fareConditions") String fareConditions) {

        List<BookingDto> bookingDtos = airService.createBooking(
                flightNos,
                name,
                document,
                date,
                fareConditions
        );

        if (bookingDtos.get(0).getFlightId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(bookingDtos.get(0).getTicketNo());
        }

        return ResponseEntity.ok(bookingDtos);
    }


    @PostMapping("/checkIn")
    public CheckInDto checkIn(
            @RequestParam("ticketNo") String ticketNo
    ){
        return airService.checkIn(
                ticketNo
        );
    }
}
