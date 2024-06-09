select 
	f.flight_no as "race", 
	tf.fare_conditions as "class", 
	to_char(f.scheduled_departure::timestamp with time zone, 'Day') as "day", 
	tf.amount as "price"
from flights f
inner join ticket_flights tf
on f.flight_id = tf.flight_id
order by tf.amount