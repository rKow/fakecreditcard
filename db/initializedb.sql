-- Drop table
CREATE TABLE if NOT EXISTS credit_cards (
	id SERIAL PRIMARY KEY,
    expiration_date date,
    human_readable_number varchar(20),
    encrypted_number varchar(200),
	created_at timestamp with time zone,
    generation_id varchar(50) not null
);
