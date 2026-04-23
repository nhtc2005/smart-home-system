CREATE TABLE users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    last_name VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone_number VARCHAR(255) UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL CHECK (role IN ('ADMIN','USER')),
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE locations (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE devices (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    location_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    device_code VARCHAR(255) NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_seen TIMESTAMPTZ,

    FOREIGN KEY (location_id)
        REFERENCES locations(id)
        ON DELETE CASCADE,

    FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);

CREATE TABLE sensors (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    device_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('TEMPERATURE','HUMIDITY','LIGHT')),
    name VARCHAR(255) NOT NULL,
    mqtt_topic VARCHAR(255),

    FOREIGN KEY (device_id)
        REFERENCES devices(id)
        ON DELETE CASCADE
);

CREATE TABLE sensor_data (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    sensor_id BIGINT NOT NULL,
    value DOUBLE PRECISION NOT NULL,
    timestamp TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (sensor_id)
        REFERENCES sensors(id)
        ON DELETE CASCADE
);

CREATE TABLE actuators (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    device_id BIGINT NOT NULL,
    type VARCHAR(50) NOT NULL CHECK (type IN ('LIGHT','FAN')),
    name VARCHAR(255) NOT NULL,
    state VARCHAR(10) NOT NULL CHECK (state IN ('ON','OFF')),
    mode VARCHAR(20) NOT NULL CHECK (mode IN ('AUTO','MANUAL','SCHEDULE')),
    mqtt_topic VARCHAR(255),

    FOREIGN KEY (device_id)
        REFERENCES devices(id)
        ON DELETE CASCADE
);

CREATE TABLE command_logs (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    actuator_id BIGINT NOT NULL,
    source VARCHAR(20) NOT NULL CHECK (source IN ('USER','SYSTEM')),
    command VARCHAR(255) NOT NULL,
    status VARCHAR(20) NOT NULL CHECK (status IN ('SENT','DONE')),
    timestamp TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (actuator_id)
        REFERENCES actuators(id)
        ON DELETE CASCADE
);

CREATE TABLE schedules (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    actuator_id BIGINT NOT NULL,
    action VARCHAR(10) NOT NULL CHECK (action IN ('ON','OFF')),
    time TIME NOT NULL,
    mode VARCHAR(20) NOT NULL CHECK (mode IN ('ONCE','DAILY','WEEKLY')),
    cron_expression VARCHAR(255) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_executed_at TIMESTAMPTZ,

    FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    FOREIGN KEY (actuator_id)
        REFERENCES actuators(id)
        ON DELETE CASCADE
);

CREATE TABLE schedule_days (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    schedule_id BIGINT NOT NULL,
    day_of_week INT NOT NULL CHECK (day_of_week BETWEEN 1 AND 7),

    FOREIGN KEY (schedule_id)
        REFERENCES schedules(id)
        ON DELETE CASCADE,

    UNIQUE (schedule_id, day_of_week)
);

CREATE TABLE notifications (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id BIGINT NOT NULL,
    device_id BIGINT,
    message VARCHAR(255) NOT NULL,
    timestamp TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE,

    FOREIGN KEY (device_id)
        REFERENCES devices(id)
        ON DELETE SET NULL
);

CREATE TABLE invalidated_token (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    jti VARCHAR(255) NOT NULL UNIQUE,
    expired_at TIMESTAMPTZ NOT NULL
);
