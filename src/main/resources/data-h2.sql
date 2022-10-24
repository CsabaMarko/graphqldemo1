SET SCHEMA EXAMPLE;

INSERT INTO product (id, sku, name, price) VALUES (1, 'keyboard', 'Keyboard', 7.99);
INSERT INTO product (id, sku, name, price) VALUES (2, 'tv', 'Television', 351.96);
INSERT INTO product (id, sku, name, price) VALUES (3, 'shirt', 'Shirt', 3.57);
INSERT INTO product (id, sku, name, price) VALUES (4, 'bed', 'Bed', 131.00);
INSERT INTO product (id, sku, name, price) VALUES (5, 'cell-phone', 'Cell Phone', 1000.00);
INSERT INTO product (id, sku, name, price) VALUES (6, 'spoon', 'Spoon', 1.00);

INSERT INTO customer (id, name, birthdate) VALUES (1, 'John Doe', '1960-10-30');
INSERT INTO customer (id, name, birthdate) VALUES (2, 'Pepito Pérez', '1954-07-15');
INSERT INTO customer (id, name, birthdate) VALUES (3, 'Cosme Fulanito', '1956-05-12');
INSERT INTO customer (id, name, birthdate) VALUES (4, 'Duffy Duck', '1976-04-11');

INSERT INTO address (id, street, postal_code) VALUES (1, 'La Habana 4310', '1000');
INSERT INTO address (id, street, postal_code) VALUES (2, '10 rue Henri Poincaré', '13014');
INSERT INTO address (id, street, postal_code) VALUES (3, 'Calle Falsa 123', '01102');

INSERT INTO email_address (id, customer_id, address) VALUES (1, 1, 'john.doe@gmail.com');
INSERT INTO email_address (id, customer_id, address) VALUES (2, 1, 'john.doe@hotmail.com');
INSERT INTO email_address (id, customer_id, address) VALUES (3, 2, 'pepito@perez.com');
INSERT INTO email_address (id, customer_id, address) VALUES (4, 3, 'cosme@fulanito.com');

INSERT INTO purchase_order (id, customer_id, order_date) VALUES (1, 2, '2018-01-04');
INSERT INTO purchase_order (id, customer_id, order_date) VALUES (2, 1, '2018-02-13');
INSERT INTO purchase_order (id, customer_id, order_date) VALUES (3, 2, '2018-02-25');

INSERT INTO item (id, order_id, product_id, quantity, total) VALUES (1, 1, 1, 10, 79.90);
INSERT INTO item (id, order_id, product_id, quantity, total) VALUES (2, 1, 2, 2, 703.92);
INSERT INTO item (id, order_id, product_id, quantity, total) VALUES (3, 1, 3, 7, 24.99);
INSERT INTO item (id, order_id, product_id, quantity, total) VALUES (4, 2, 4, 2, 262.00);
INSERT INTO item (id, order_id, product_id, quantity, total) VALUES (5, 2, 5, 15, 15000.00);
INSERT INTO item (id, order_id, product_id, quantity, total) VALUES (6, 3, 1, 7, 55.93);
INSERT INTO item (id, order_id, product_id, quantity, total) VALUES (7, 3, 6, 18, 18.00);

COMMIT;



SET SCHEMA SNOW;

INSERT INTO USERS (ID, SN_ID, FIRST_NAME, LAST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL_ADDRESS)
    VALUES ('U1', 'bugs_bunny', 'Bugs', 'Bunny', null, '1-555-123-4567', 'Bugs.Bunny@notwarner.com');
INSERT INTO USERS (ID, SN_ID, FIRST_NAME, LAST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL_ADDRESS)
    VALUES ('U2', 'lola_bunny', 'Lola', 'Bunny', null, null, null);
INSERT INTO USERS (ID, SN_ID, FIRST_NAME, LAST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL_ADDRESS)
    VALUES ('U3', 'duffy_duck', 'Duffy', 'Duck', null, null, 'Duffy.Duck@notwarner.com');
INSERT INTO USERS (ID, SN_ID, FIRST_NAME, LAST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL_ADDRESS)
    VALUES ('U4', 'porky_pig', 'Porky', 'Pig', null, null, 'Porky.Pig@notwarner.com');
INSERT INTO USERS (ID, SN_ID, FIRST_NAME, LAST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL_ADDRESS)
    VALUES ('U5', 'sylvester_the_cat', 'Sylvester', 'Cat', 'the', null, 'Sylvester.the.Cat@notwarner.com');
INSERT INTO USERS (ID, SN_ID, FIRST_NAME, LAST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL_ADDRESS)
    VALUES ('U6', 'tweety', 'Tweety', null, null, null, 'Tweety@notwarner.com');
INSERT INTO USERS (ID, SN_ID, FIRST_NAME, LAST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL_ADDRESS)
    VALUES ('U7', 'wile_e_coyote', 'Wile', 'Coyote', 'E.', null, 'Wile.E.Coyote@notwarner.com');
INSERT INTO USERS (ID, SN_ID, FIRST_NAME, LAST_NAME, MIDDLE_NAME, PHONE_NUMBER, EMAIL_ADDRESS)
    VALUES ('U8', 'road_runner', 'Road', 'Runner', null, null, null);


INSERT INTO CHANGE_REQUEST (ID, CHANGE_NUMBER, SHORT_DESCRIPTION, DESCRIPTION, ACTIVE_INDICATOR, CHANGE_REQUEST_TYPE,
                            ASSIGNED_TO_USER_ID, CREATED_BY_USER_ID, REQUESTED_BY_USER_ID, WORK_START_DATE, WORK_END_DATE,
                            TIME_WORKED_HOURS, CREATED_TIMESTAMP, UPDATED_TIMESTAMP)
    VALUES ('CR1', 'CHG903001', 'Short Description 1', 'Description 1', 1, 'Normal',
            'U1', 'U2', 'U3', DATE '2021-04-12', DATE '2021-04-13',
            5, TIMESTAMP '2021-04-12 09:59:59', TIMESTAMP '2021-04-13 16:45:03');

INSERT INTO CHANGE_REQUEST (ID, CHANGE_NUMBER, SHORT_DESCRIPTION, DESCRIPTION, ACTIVE_INDICATOR, CHANGE_REQUEST_TYPE,
                            ASSIGNED_TO_USER_ID, CREATED_BY_USER_ID, REQUESTED_BY_USER_ID, WORK_START_DATE, WORK_END_DATE,
                            TIME_WORKED_HOURS, CREATED_TIMESTAMP, UPDATED_TIMESTAMP)
    VALUES ('CR2', 'CHG903002', 'Short Description 2', 'Description 2', 0, 'Standard',
            null, 'U2', 'U3', null, null,
            null, TIMESTAMP '2021-04-12 09:50:00', TIMESTAMP '2021-04-12 16:43:00');

INSERT INTO CHANGE_REQUEST (ID, CHANGE_NUMBER, SHORT_DESCRIPTION, DESCRIPTION, ACTIVE_INDICATOR, CHANGE_REQUEST_TYPE,
                            ASSIGNED_TO_USER_ID, CREATED_BY_USER_ID, REQUESTED_BY_USER_ID, WORK_START_DATE, WORK_END_DATE,
                            TIME_WORKED_HOURS, CREATED_TIMESTAMP, UPDATED_TIMESTAMP)
    VALUES ('CR3', 'CHG903003', 'Short Description 3', 'Description 3', 1, 'Emergency',
            'U5', 'U4', 'U6', DATE '2022-05-12', DATE '2022-05-13',
            3, TIMESTAMP '2022-05-12 09:11:07', TIMESTAMP '2022-05-13 14:33:03');


INSERT INTO CHANGE_REQUEST_X_AFFECTED_USERS (CHANGE_REQUEST_ID, USERS_ID)
VALUES ('CR1', 'U3');

INSERT INTO CHANGE_REQUEST_X_AFFECTED_USERS (CHANGE_REQUEST_ID, USERS_ID)
VALUES ('CR1', 'U7');

INSERT INTO CHANGE_REQUEST_X_AFFECTED_USERS (CHANGE_REQUEST_ID, USERS_ID)
VALUES ('CR1', 'U8');

INSERT INTO CHANGE_REQUEST_X_AFFECTED_USERS (CHANGE_REQUEST_ID, USERS_ID)
VALUES ('CR3', 'U5');

INSERT INTO CHANGE_REQUEST_X_AFFECTED_USERS (CHANGE_REQUEST_ID, USERS_ID)
VALUES ('CR3', 'U6');

INSERT INTO CHANGE_REQUEST_X_AFFECTED_USERS (CHANGE_REQUEST_ID, USERS_ID)
VALUES ('CR3', 'U8');


COMMIT;
