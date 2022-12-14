DROP SCHEMA IF EXISTS EXAMPLE CASCADE;
CREATE SCHEMA EXAMPLE AUTHORIZATION SA;

DROP SCHEMA IF EXISTS SNOW CASCADE;
CREATE SCHEMA SNOW AUTHORIZATION SA;

SET SCHEMA EXAMPLE;

CREATE TABLE IF NOT EXISTS product
(
    id    IDENTITY PRIMARY KEY,
    sku   VARCHAR(256),
    name  VARCHAR(256),
    price DECIMAL(20, 2)
);

CREATE TABLE IF NOT EXISTS customer
(
    id        IDENTITY PRIMARY KEY,
    name      VARCHAR(256),
    birthdate DATE
);

CREATE TABLE IF NOT EXISTS address
(
    id          INT8 PRIMARY KEY,
    street      VARCHAR(128),
    postal_code VARCHAR(16)
--    FOREIGN KEY (id) REFERENCES customer (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS email_address
(
    id          IDENTITY PRIMARY KEY,
    customer_id INT8,
    address     VARCHAR(128)
--     FOREIGN KEY (customer_id) REFERENCES customer (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS purchase_order
(
    id          IDENTITY PRIMARY KEY,
    customer_id INT8,
    order_date  DATE
--     FOREIGN KEY (customer_id) REFERENCES customer (id)
);

CREATE TABLE IF NOT EXISTS item
(
    id         IDENTITY PRIMARY KEY,
    order_id   INT8,
    product_id INT8,
    quantity   INT,
    total      DECIMAL(20, 2)
--     FOREIGN KEY (order_id) REFERENCES purchase_order (id),
--     FOREIGN KEY (product_id) REFERENCES product (id)
);

SET SCHEMA SNOW;

-- H2 doesn't allow "USER"; probably reserved word?
CREATE TABLE IF NOT EXISTS USERS
(
    ID VARCHAR(255) NOT NULL,
    SN_ID VARCHAR(255),
    FIRST_NAME VARCHAR(255),
    LAST_NAME VARCHAR(255),
    MIDDLE_NAME VARCHAR(255),
    PHONE_NUMBER VARCHAR(255),
    EMAIL_ADDRESS VARCHAR(255),
    CONSTRAINT "PK_USERS" PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS CHANGE_REQUEST
(
    ID VARCHAR(64) NOT NULL,
    CHANGE_NUMBER VARCHAR(255), -- NUMBER is a silly choice for a textual, natural id, I know.
    SHORT_DESCRIPTION VARCHAR(255),
    DESCRIPTION VARCHAR(1000),
    ACTIVE_INDICATOR NUMBER(1, 0), -- 0: false, 1: true
    CHANGE_REQUEST_TYPE VARCHAR(100), -- Enum candidate? - values: 'Emergency', 'Normal', 'Standard'
    ASSIGNED_TO_USER_ID VARCHAR(64),
    CREATED_BY_USER_ID VARCHAR(64),
    REQUESTED_BY_USER_ID VARCHAR(64),
    WORK_START_DATE DATE,
    WORK_END_DATE DATE,
    TIME_WORKED_HOURS NUMBER(6, 0),
    CREATED_TIMESTAMP TIMESTAMP(6) DEFAULT LOCALTIMESTAMP,
    UPDATED_TIMESTAMP TIMESTAMP(6) DEFAULT LOCALTIMESTAMP,
    CONSTRAINT "PK_CHANGE_REQUEST" PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS CHANGE_REQUEST_X_AFFECTED_USERS
(
    CHANGE_REQUEST_ID VARCHAR(64) NOT NULL,
    USERS_ID VARCHAR(64) NOT NULL,
    CREATED_TIMESTAMP TIMESTAMP(6) DEFAULT LOCALTIMESTAMP,
    UPDATED_TIMESTAMP TIMESTAMP(6) DEFAULT LOCALTIMESTAMP
);

ALTER TABLE CHANGE_REQUEST_X_AFFECTED_USERS
    ADD CONSTRAINT "PK_CHANGE_REQUEST_X_AFFECTED_USERS" PRIMARY KEY (CHANGE_REQUEST_ID, USERS_ID);

ALTER TABLE CHANGE_REQUEST_X_AFFECTED_USERS
    ADD CONSTRAINT "FK_CHANGE_REQUEST_X_AFFECTED_USERS_CR" FOREIGN KEY (CHANGE_REQUEST_ID)
        REFERENCES CHANGE_REQUEST (ID);

ALTER TABLE CHANGE_REQUEST_X_AFFECTED_USERS
    ADD CONSTRAINT "FK_CHANGE_REQUEST_X_AFFECTED_USERS_U" FOREIGN KEY (USERS_ID)
        REFERENCES USERS (ID);
