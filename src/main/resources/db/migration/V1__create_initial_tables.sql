CREATE TABLE hotel_category (
                                id BIGSERIAL PRIMARY KEY,
                                category_name VARCHAR(150) NOT NULL UNIQUE,
                                description VARCHAR(2000) NOT NULL,
                                created_at TIMESTAMP NOT NULL,
                                updated_at TIMESTAMP
);

CREATE TABLE room_type (
                           id BIGSERIAL PRIMARY KEY,
                           name VARCHAR(100) NOT NULL,
                           max_capacity INTEGER NOT NULL,
                           base_daily_rate DECIMAL(10,2) NOT NULL,
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP
);

CREATE TABLE hotel (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(150) NOT NULL,
                       tax_id VARCHAR(20) NOT NULL UNIQUE,

                       street VARCHAR(255) NOT NULL,
                       number VARCHAR(20) NOT NULL,
                       complement VARCHAR(100),
                       neighborhood VARCHAR(255) NOT NULL,
                       city VARCHAR(255) NOT NULL,
                       state VARCHAR(255) NOT NULL,
                       country VARCHAR(255) NOT NULL,
                       zip_code VARCHAR(20) NOT NULL,

                       category_id BIGINT,
                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP,
                       CONSTRAINT fk_hotel_category FOREIGN KEY (category_id) REFERENCES hotel_category (id)
);

CREATE TABLE room (
                      id BIGSERIAL PRIMARY KEY,
                      number VARCHAR(20) NOT NULL,
                      maintenance_status VARCHAR(30) NOT NULL,
                      room_type_id BIGINT NOT NULL,
                      hotel_id BIGINT NOT NULL,
                      created_at TIMESTAMP NOT NULL,
                      updated_at TIMESTAMP,
                      CONSTRAINT fk_room_type FOREIGN KEY (room_type_id) REFERENCES room_type (id),
                      CONSTRAINT fk_room_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id)
);

CREATE TABLE department (
                            id BIGSERIAL PRIMARY KEY,
                            name VARCHAR(150) NOT NULL,
                            hotel_id BIGINT,
                            created_at TIMESTAMP NOT NULL,
                            updated_at TIMESTAMP,
                            CONSTRAINT fk_department_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id)
);

CREATE TABLE employee (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(150) NOT NULL,
                          document VARCHAR(100) NOT NULL UNIQUE,
                          birth_date DATE NOT NULL,
                          email VARCHAR(150) NOT NULL,
                          phone_number VARCHAR(30) NOT NULL,
                          role VARCHAR(150) NOT NULL,
                          employee_number VARCHAR(20) NOT NULL,
                          department_id BIGINT,
                          street VARCHAR(255) NOT NULL,
                          number VARCHAR(20) NOT NULL,
                          complement VARCHAR(100),
                          neighborhood VARCHAR(255) NOT NULL,
                          city VARCHAR(255) NOT NULL,
                          state VARCHAR(255) NOT NULL,
                          country VARCHAR(255) NOT NULL,
                          zip_code VARCHAR(20) NOT NULL,
                          created_at TIMESTAMP NOT NULL,
                          updated_at TIMESTAMP,
                          CONSTRAINT fk_department FOREIGN KEY (department_id) REFERENCES department(id)
);

CREATE TABLE guest (
                       id BIGSERIAL PRIMARY KEY,
                       name VARCHAR(150) NOT NULL,
                       document VARCHAR(100) NOT NULL UNIQUE,
                       birth_date DATE NOT NULL,
                       email VARCHAR(150) NOT NULL,
                       phone_number VARCHAR(30) NOT NULL,

                       street VARCHAR(255) NOT NULL,
                       number VARCHAR(20) NOT NULL,
                       complement VARCHAR(100),
                       neighborhood VARCHAR(255) NOT NULL,
                       city VARCHAR(255) NOT NULL,
                       state VARCHAR(255) NOT NULL,
                       country VARCHAR(255) NOT NULL,
                       zip_code VARCHAR(20) NOT NULL,

                       created_at TIMESTAMP NOT NULL,
                       updated_at TIMESTAMP
);

CREATE TABLE reservation (
                             id BIGSERIAL PRIMARY KEY,
                             booker_id BIGINT NOT NULL,
                             hotel_id BIGINT NOT NULL,
                             expected_checkin_date DATE NOT NULL,
                             expected_checkout_date DATE NOT NULL,
                             status VARCHAR(30) NOT NULL,
                             total_amount DECIMAL(10,2),
                             created_at TIMESTAMP NOT NULL,
                             updated_at TIMESTAMP,
                             CONSTRAINT fk_reservation_booker FOREIGN KEY (booker_id) REFERENCES guest (id),
                             CONSTRAINT fk_reservation_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id)
);

CREATE TABLE reservation_item (
                                  id BIGSERIAL PRIMARY KEY,
                                  reservation_id BIGINT NOT NULL,
                                  room_id BIGINT NOT NULL,
                                  applied_daily_rate DECIMAL(10,2) NOT NULL,
                                  created_at TIMESTAMP NOT NULL,
                                  updated_at TIMESTAMP,
                                  CONSTRAINT fk_res_item_reservation FOREIGN KEY (reservation_id) REFERENCES reservation (id),
                                  CONSTRAINT fk_res_item_room FOREIGN KEY (room_id) REFERENCES room (id)
);

CREATE TABLE occupancy (
                           id BIGSERIAL PRIMARY KEY,
                           reservation_item_id BIGINT NOT NULL,
                           guest_id BIGINT NOT NULL,
                           is_room_responsible BOOLEAN NOT NULL,
                           created_at TIMESTAMP NOT NULL,
                           updated_at TIMESTAMP,
                           CONSTRAINT fk_occupancy_res_item FOREIGN KEY (reservation_item_id) REFERENCES reservation_item (id),
                           CONSTRAINT fk_occupancy_guest FOREIGN KEY (guest_id) REFERENCES guest (id)
);