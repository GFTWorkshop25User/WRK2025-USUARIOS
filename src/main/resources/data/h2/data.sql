INSERT INTO USERS (ID, NAME, EMAIL, HASHED_PASSWORD, COUNTRY, ZIP_CODE, CITY, STREET, LOYALTY_POINTS, DISABLED)
VALUES ('7d108b78-2d55-48cf-a39a-8b25bc667fe4', 'Alice Johnson', 'alice@example.com', '$2a$10$ynLl/PWEJGI6ZIVCbqsOcupdScWIowOB8t79dunjak7Wj9RjZxTri', 'USA', '10001',
        'New York', '123 Main St', 150, FALSE),
       ('f19069b6-e374-4969-9ad3-47bb556dbf1e', 'Bob Smith', 'bob@example.com', '$2a$10$qi7z05nhMMXeLqjftsyJt.VnDnTwsjbe0nN/NAxCp.XS5a3LARMTy', 'USA', '90001',
        'Los Angeles', '456 Sunset Blvd', 200, FALSE),
       ('084c54a7-7b02-4225-8038-d1ec9cbfea59', 'Carol White', 'carol@example.com', '$2a$10$gAWq4Mw7/pSrUw17QOQsQ.zp3sZBkZw4sDzxjGq9F7dK6htQ/qn6m', 'Canada', 'M5H',
        'Toronto', '789 Queen St', 300, FALSE),
       ('fe957011-1565-4e7f-9e3c-0e8f7d015ef3', 'David Brown', 'david@example.com', '$2a$10$1v./AJok7bwqc5H9/FIpyuwUXI9eeH4vWH4wHPOdHd1l29.rFPnVi', 'UK', 'SW1A',
        'London', '10 Downing St', 120, TRUE),
       ('ff9e84d8-cf7d-4730-a506-b9fb879d1bea', 'Eve Black', 'eve@example.com', '$2a$10$5PJjuYBKE9lI2EFsD4EkTulkmT1QxwtpU23KgXAQXdr6HCSSYzoTO', 'Australia', '2000',
        'Sydney', '99 George St', 180, FALSE);


INSERT INTO USER_FAVORITE_PRODUCTS (PRODUCT_ID, USER_ID)
VALUES (101, '7d108b78-2d55-48cf-a39a-8b25bc667fe4'),
       (102, '7d108b78-2d55-48cf-a39a-8b25bc667fe4'),
       (103, 'f19069b6-e374-4969-9ad3-47bb556dbf1e'),
       (104, 'f19069b6-e374-4969-9ad3-47bb556dbf1e'),
       (105, '084c54a7-7b02-4225-8038-d1ec9cbfea59'),
       (101, '084c54a7-7b02-4225-8038-d1ec9cbfea59'),
       (106, 'fe957011-1565-4e7f-9e3c-0e8f7d015ef3'),
       (107, 'ff9e84d8-cf7d-4730-a506-b9fb879d1bea'),
       (108, 'ff9e84d8-cf7d-4730-a506-b9fb879d1bea'),
       (103, 'ff9e84d8-cf7d-4730-a506-b9fb879d1bea');
