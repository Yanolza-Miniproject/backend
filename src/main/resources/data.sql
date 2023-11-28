INSERT INTO `accommodation` (`id`, `name`, `type`, `address`, `phone_number`, `homepage`, `info_detail`, `thumbnail_url`, `category_parking`, `category_cooking`, `category_pickup`, `category_amenities`, `category_dining_area`, `check_in`, `check_out`, `wish_count`, `view_count`)
VALUES
    (1, 'Accommodation1', 'PENSION', '경상남도 하동군 화개로 13', '123-456-7890', 'www.accommodation1.com', 'This is a detail for Accommodation1', 'www.accommodation1.com/thumbnail.jpg', 1, 0, 1, 'aaa', 'bbb', '14:00', '16:00', 10, 100),
    (2, 'Accommodation2', 'HOTEL', '제주특별자치도 서귀포시 중문관광로72번길 60', '234-567-8901', 'www.accommodation2.com', 'This is a detail for Accommodation2', 'www.accommodation2.com/thumbnail.jpg', 0, 1, 0, 'aaa', 'bbb', '14:00', '14:00', 20, 200),
    (3, 'Accommodation3', 'CONDOMINIUM', '서울특별시 종로구 자하문로5가길 11 (체부동)', '345-678-9012', 'www.accommodation3.com', 'This is a detail for Accommodation3', 'www.accommodation3.com/thumbnail.jpg', 1, 1, 0, 'aaa', 'bbb', '14:00', '14:00', 30, 300),
    (4, 'Accommodation4', 'CONDOMINIUM', '전라남도 무안군 현경면 홀통길 55', '456-789-0123', 'www.accommodation4.com', 'This is a detail for Accommodation4', 'www.accommodation4.com/thumbnail.jpg', 0, 0, 1, 'aaa', 'bbb', '14:00', '14:00', 40, 400),
    (5, 'Accommodation5', 'PENSION', '경상남도 통영시 욕지면 욕지일주로 1148-78 ', '567-890-1234', 'www.accommodation5.com', 'This is a detail for Accommodation5', 'www.accommodation5.com/thumbnail.jpg', 1, 0, 0, 'aaa', 'bbb', '14:00', '14:00', 50, 500),
    (6, 'Accommodation6', 'PENSION', '경상남도 통영시 욕지면 욕지일주로 1148-78 ', '567-890-1234', 'www.accommodation5.com', 'This is a detail for Accommodation5', 'www.accommodation5.com/thumbnail.jpg', 1, 0, 0, 'aaa', 'bbb', '14:00', '14:00', 50, 500),
    (7, 'Accommodation7', 'HOTEL', '경기도 수원시 팔달로 13', '123-456-7890', 'www.accommodation7.com', 'This is a detail for Accommodation7', 'www.accommodation7.com/thumbnail.jpg', 1, 0, 1, 'aaa', 'bbb', '15:00', '11:00', 10, 100);


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
    (10, 5, 'Room10', 1000, 3, 0, 0, 0, 0, 1, 1, 1),
    (11, 7, 'Room11', 100, 2, 10, 1, 0, 1, 1, 0, 1),
    (12, 7, 'Room12', 200, 3, 5, 0, 1, 0, 1, 1, 0);


INSERT INTO `room_inventory` (`id`, `room_id`, `date`, `inventory`)
VALUES
    (1, 11, '2023-11-30', 1),
    (2, 11, '2023-12-01', 0),
    (3, 11, '2023-12-02', 1),
    (4, 11, '2023-12-03', 1),
    (5, 11, '2023-12-04', 1),
    (6, 12, '2023-11-30', 1),
    (7, 12, '2023-12-01', 1),
    (8, 12, '2023-12-02', 0),
    (9, 12, '2023-12-03', 1),
    (10, 12, '2023-12-04', 1);