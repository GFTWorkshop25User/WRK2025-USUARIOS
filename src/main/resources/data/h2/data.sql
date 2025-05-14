INSERT INTO USERS (ID, NAME, EMAIL, HASHED_PASSWORD, COUNTRY, ZIP_CODE, CITY, STREET, LOYALTY_POINTS)
VALUES ('11111111-1111-1111-1111-111111111111', 'Alice Johnson', 'alice@example.com', 'hashed_pass1', 'USA', '10001',
        'New York', '123 Main St', 150),
       ('22222222-2222-2222-2222-222222222222', 'Bob Smith', 'bob@example.com', 'hashed_pass2', 'USA', '90001',
        'Los Angeles', '456 Sunset Blvd', 200),
       ('33333333-3333-3333-3333-333333333333', 'Carol White', 'carol@example.com', 'hashed_pass3', 'Canada', 'M5H',
        'Toronto', '789 Queen St', 300),
       ('44444444-4444-4444-4444-444444444444', 'David Brown', 'david@example.com', 'hashed_pass4', 'UK', 'SW1A',
        'London', '10 Downing St', 120),
       ('55555555-5555-5555-5555-555555555555', 'Eve Black', 'eve@example.com', 'hashed_pass5', 'Australia', '2000',
        'Sydney', '99 George St', 180);


INSERT INTO USER_FAVORITE_PRODUCTS (PRODUCT_ID, USER_ID)
VALUES (101, '11111111-1111-1111-1111-111111111111'),
       (102, '11111111-1111-1111-1111-111111111111'),
       (103, '22222222-2222-2222-2222-222222222222'),
       (104, '22222222-2222-2222-2222-222222222222'),
       (105, '33333333-3333-3333-3333-333333333333'),
       (101, '33333333-3333-3333-3333-333333333333'),
       (106, '44444444-4444-4444-4444-444444444444'),
       (107, '55555555-5555-5555-5555-555555555555'),
       (108, '55555555-5555-5555-5555-555555555555'),
       (103, '55555555-5555-5555-5555-555555555555');
