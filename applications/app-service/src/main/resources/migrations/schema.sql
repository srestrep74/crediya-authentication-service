DROP TABLE IF EXISTS public.users;

CREATE TABLE public.users (
    user_id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(150) UNIQUE NOT NULL,
    identity_document VARCHAR(50) NOT NULL,
    phone_number VARCHAR(20),
    base_salary NUMERIC(15,2)
);
