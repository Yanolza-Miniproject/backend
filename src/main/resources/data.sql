INSERT INTO `accommodation` (`id`, `name`, `type`, `address`, `phone_number`, `homepage`, `info_detail`, `thumbnail_url`, `category_parking`, `category_cooking`, `category_pickup`, `category_amenities`, `category_dining_area`, `check_in`, `check_out`, `like_count`, `view_count`)
VALUES
    (1, 'Accommodation1', 'Hotel', '123 Main St', '123-456-7890', 'www.accommodation1.com', 'This is a detail for Accommodation1', 'www.accommodation1.com/thumbnail.jpg', 1, 0, 1, 0, 1, '2023-12-01 14:00:00', '2023-12-02 11:00:00', 10, 100),
    (2, 'Accommodation2', 'Motel', '456 Market St', '234-567-8901', 'www.accommodation2.com', 'This is a detail for Accommodation2', 'www.accommodation2.com/thumbnail.jpg', 0, 1, 0, 1, 0, '2023-12-02 15:00:00', '2023-12-03 12:00:00', 20, 200),
    (3, 'Accommodation3', 'Inn', '789 Broadway St', '345-678-9012', 'www.accommodation3.com', 'This is a detail for Accommodation3', 'www.accommodation3.com/thumbnail.jpg', 1, 1, 0, 0, 1, '2023-12-03 16:00:00', '2023-12-04 10:00:00', 30, 300),
    (4, 'Accommodation4', 'Resort', '012 5th Ave', '456-789-0123', 'www.accommodation4.com', 'This is a detail for Accommodation4', 'www.accommodation4.com/thumbnail.jpg', 0, 0, 1, 1, 0, '2023-12-04 17:00:00', '2023-12-05 12:00:00', 40, 400),
    (5, 'Accommodation5', 'Lodge', '345 6th Ave', '567-890-1234', 'www.accommodation5.com', 'This is a detail for Accommodation5', 'www.accommodation5.com/thumbnail.jpg', 1, 0, 0, 1, 1, '2023-12-05 18:00:00', '2023-12-06 11:00:00', 50, 500);

INSERT INTO `room` (`id`, `accommodation_id`, `name`, `price`, `capacity`, `inventory`, `category_tv`, `category_pc`, `category_internet`, `category_refrigerator`, `category_bathing_facilities`, `category_dryer`)
VALUES
    (1, 1, 'Room1', 100, 2, 10, 1, 0, 1, 1, 0, 1),
    (2, 1, 'Room2', 200, 3, 5, 0, 1, 0, 1, 1, 0),
    (3, 2, 'Room3', 300, 4, 0, 1, 1, 1, 0, 0, 1),
    (4, 2, 'Room4', 400, 1, 3, 0, 0, 1, 1, 1, 0),
    (5, 3, 'Room5', 500, 2, 8, 1, 0, 0, 0, 1, 1),
    (6, 3, 'Room6', 600, 3, 2, 0, 1, 1, 1, 0, 0),
    (7, 4, 'Room7', 700, 4, 1, 1, 1, 0, 0, 1, 1),
    (8, 4, 'Room8', 800, 1, 6, 0, 0, 1, 1, 0, 1),
    (9, 5, 'Room9', 900, 2, 4, 1, 1, 1, 0, 1, 0),
    (10, 5, 'Room10', 1000, 3, 0, 0, 0, 0, 1, 1, 1);
