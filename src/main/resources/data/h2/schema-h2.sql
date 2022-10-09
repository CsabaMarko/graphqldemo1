DROP SCHEMA IF EXISTS EXAMPLE CASCADE;
CREATE SCHEMA EXAMPLE AUTHORIZATION SA;
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
