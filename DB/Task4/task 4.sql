select flights.flight_no as flight, round(avg(ticket_flights.amount), 2) as price
from flights
left join ticket_flights
on flights.flight_id = ticket_flights.flight_id 
group by flights.flight_no
order by flights.flight_no asc

-- SELECT flights.flight_no, ticket_flights.amount FROM ticket_flights
-- full join flights
-- on flights.flight_id = ticket_flights.flight_id
-- WHERE flight_no = 'PG0234';

-- select * from ticket_flights
-- where flight_id = 1;
