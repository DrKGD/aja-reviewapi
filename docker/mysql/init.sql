DROP DATABASE IF EXISTS ReviewAPI;	
CREATE DATABASE ReviewAPI;
USE ReviewAPI;

CREATE TABLE Category (
	id						INT AUTO_INCREMENT PRIMARY KEY,
	name					VARCHAR(64) NOT NULL
);

CREATE TABLE Product (
	id						INT AUTO_INCREMENT PRIMARY KEY,
	name					VARCHAR(64)		NOT NULL,
	description		VARCHAR(512)	NOT NULL,
	price					DECIMAL(8,2)	NOT NULL
);

CREATE TABLE ProductImage (
	id						INT AUTO_INCREMENT PRIMARY KEY,
	id_product		INT NOT NULL,
	path					VARCHAR(256)	NOT NULL,
	nth						INT NOT NULL,
	CONSTRAINT UC_Order UNIQUE (id_product, nth),
	CONSTRAINT FOREIGN KEY (id_product) REFERENCES Product(id) ON DELETE CASCADE
);

CREATE TABLE Tag (
	id						INT AUTO_INCREMENT PRIMARY KEY,
	id_category		INT NOT NULL,
	id_product		INT NOT NULL,
	CONSTRAINT FOREIGN KEY (id_category) REFERENCES Category(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (id_product) REFERENCES Product(id) ON DELETE CASCADE
);


CREATE Table User (
	id						INT AUTO_INCREMENT PRIMARY KEY,
	username			VARCHAR(64) NOT NULL UNIQUE,	
	role					ENUM('user', 'editor') NOT NULL,
	email					VARCHAR(256) NOT NULL UNIQUE,
	salt					VARCHAR(32) NOT NULL UNIQUE,		-- Salt may be stored directly in the database, as it is not secret
																								-- it has size 16, but b64 encoded it'd be (16/3)*4
	secret				VARCHAR(128)	NOT NULL					-- Salted password using Argon2id
																								-- it has size 64, but b64 encoded it'd be (64/3)*4
);

CREATE Table Review (
	id						INT AUTO_INCREMENT PRIMARY KEY,
	id_product		INT NOT NULL,
	id_user				INT NOT NULL,
	rating				DECIMAL(3,2) NOT NULL,					-- 0.00 to 5.00 
	short_note		VARCHAR(128) NOT NULL,
	note					VARCHAR(2048) NOT NULL,
	date					DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
	last_modified	DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
	CONSTRAINT FOREIGN KEY (id_product) REFERENCES Product(id) ON DELETE CASCADE,
	CONSTRAINT FOREIGN KEY (id_user) REFERENCES User(id) ON DELETE CASCADE
);


-- Insert Categories
INSERT INTO Category (name) VALUES
('Kitchen'),
('Electrodomestics'),
('Furniture'),
('Outdoors'),
('Electronics'),
('Office Supplies'),
('Toys'),
('Sports');

-- Insert Products
INSERT INTO Product (price, name, description) VALUES
(599.99, 'Washing Machine', 'High-efficiency washing machine with multiple settings'),
(899.99, 'Refrigerator', 'Energy-efficient refrigerator with spacious compartments'),
(199.99, 'Microwave Oven', 'Compact microwave oven with advanced heating options'),
(299.99, 'Dining Table', 'Wooden dining table with a polished finish'),
(999.99, 'Smartphone', 'Latest model smartphone with cutting-edge technology'),
(1299.99, 'Laptop', 'Lightweight laptop with powerful performance'),
(79.99, 'Garden Chair', 'Comfortable garden chair made of durable material'),
(49.99, 'Blender', 'High-speed blender perfect for smoothies'),
(499.99, 'Air Conditioner', 'Energy-saving air conditioner for large rooms'),
(699.99, 'Television', '4K Ultra HD television with smart features'),
(19.99, 'Office Chair', 'Ergonomic office chair with adjustable height'),
(24.99, 'Desk Lamp', 'LED desk lamp with brightness control'),
(59.99, 'Toy Robot', 'Interactive toy robot with voice recognition'),
(149.99, 'Treadmill', 'Compact treadmill for home workouts'),
(39.99, 'Camping Tent', 'Lightweight camping tent for two people');

INSERT INTO ProductImage (id_product, nth, path) VALUES
(1, 1, '/static/product/1/washing_machine_1.jpg'),
(1, 2, '/static/product/1/washing_machine_2.jpg'),
(1, 3, '/static/product/1/washing_machine_3.jpg');

-- Insert Tags (Multiple tags per product)
INSERT INTO Tag (id_category, id_product) VALUES
(2, 1), -- Washing Machine is an Electrodomestic
(1, 1), -- Washing Machine can also be a Kitchen item
(2, 2), -- Refrigerator is an Electrodomestic
(1, 2), -- Refrigerator can also be a Kitchen item
(2, 3), -- Microwave Oven is an Electrodomestic
(1, 3), -- Microwave Oven can also be a Kitchen item
(3, 4), -- Dining Table is Furniture
(1, 4), -- Dining Table can also be Kitchen furniture
(5, 5), -- Smartphone is an Electronic
(5, 6), -- Laptop is an Electronic
(4, 7), -- Garden Chair is Outdoors
(3, 7), -- Garden Chair can also be Furniture
(2, 8), -- Blender is an Electrodomestic
(1, 8), -- Blender can also be a Kitchen item
(2, 9), -- Air Conditioner is an Electrodomestic
(5, 10), -- Television is an Electronic
(6, 11), -- Office Chair is an Office Supply
(3, 11), -- Office Chair can also be Furniture
(6, 12), -- Desk Lamp is an Office Supply
(5, 12), -- Desk Lamp is an Electronic
(7, 13), -- Toy Robot is a Toy
(5, 13), -- Toy Robot is an Electronic
(8, 14), -- Treadmill is a Sports item
(4, 15), -- Camping Tent is Outdoors
(8, 15); -- Camping Tent is also a Sports item

-- Insert data into User table
-- These users were generated with the following parameters, recompile them with different parameters in case
-- Iterations:	16
-- Memlimit:		65536
-- HashLength:	64
-- Parallism:		2
INSERT INTO User (role, username, email, salt, secret) VALUES
('editor', 'DrKGD', 'no-email@email.com', '4EFbtQ9qfkEewaix8FgiWw==', 'u0WnfP8GIhk9+KeLxGvQycEAM2YBVxYEndKTiBPz2+gMev6Cdjg5HvewsAeeXLctfatIZGRIBqySXMbir0xqrg==');

INSERT INTO User (username, email, salt, secret) VALUES
('Pablo', 'pablo@email.com', 'wrwhgcFFMruCs5vXn1u8DQ==', 'U8SukdAE5lne0aGAFAZK9WrHcKGlO18Ejb0z3uvoPNAIJHQr+r48NOhA9Tx8V8NFqextDgyr/onqmHf5cSIYOg=='),
('Carlos', 'carlos@email.com', '2jbVF0fjXCDlekm9Rj1whQ==', 'P7W1AOrcmNare8vSsvBFv0WTzD8YZSXQC2FMOJlNBttXEDdznUnVRdpcohUHd5lds3XWEZm1fXmXO8Bc/iADCw=='),
('Viego', 'viego@email.com', 'VluR/aPLv7x7kU2RP8e25w==', 'xKaCAXjn9K22bmfuXdZQmOXcXvimX3liuLrerfumgNrU7c2KNIoAGgrs/BGWSQeVQvk+0NKD+xkxjPz1jX7gzw=='),
('Quantum', 'quantum@email.com', 'i/TsJ0pCfVh36k8T4vOGCg==', 'a+BsbM0TBDKB+U0xQ0GfpgPIr3WS4NdfhxuZ1x2cNim5M5UTmCgg2cjAx3QuKFGfhEblVWwwYgcvkCaEHhqRnA=='),
('Steven', 'steven@email.com', '8BnNO5vqE+rk+oxJbIlTYA==', 'nP+XGuZozVn4VPvoIiQp6UTWhx5dUpwx9YUeY3Wr9GvtMRya+pzczb3GOL76xWfRqrx/LCw27QT0aQn76rWxBw==');

-- Insert data into Review table
INSERT INTO Review (id_product, id_user, rating, short_note, note, date, last_modified) VALUES
(1, 1, 4.50, 'Great performance', 'The washing machine works efficiently and is very easy to use.', '2023-12-15 10:30:00', '2023-12-15 10:30:00'),
(2, 2, 4.80, 'Spacious and efficient', 'The refrigerator has ample space and keeps food fresh for a long time.', '2023-12-10 14:20:00', '2023-12-11 09:00:00'),
(3, 3, 4.20, 'Compact and useful', 'The microwave oven is compact and heats food evenly.', '2023-12-05 18:45:00', '2023-12-05 18:45:00'),
(4, 4, 4.70, 'Sturdy and elegant', 'The dining table is well-built and adds a classy touch to the dining room.', '2023-12-03 12:15:00', '2023-12-04 08:00:00'),
(5, 5, 4.90, 'Amazing phone', 'The smartphone is incredibly fast and has a stunning display.', '2023-12-01 09:00:00', '2023-12-01 09:00:00'),
(6, 1, 4.60, 'Powerful laptop', 'This laptop is lightweight yet handles all tasks effortlessly.', '2023-12-08 15:00:00', '2023-12-10 12:30:00'),
(7, 2, 4.30, 'Comfortable seating', 'The garden chair is durable and very comfortable to sit on.', '2023-12-12 11:00:00', '2023-12-12 11:00:00'),
(8, 3, 4.50, 'Perfect for smoothies', 'The blender is powerful and makes smoothies in seconds.', '2023-12-18 16:30:00', '2023-12-19 08:45:00'),
(9, 4, 4.80, 'Cool and quiet', 'The air conditioner cools the room quickly and operates quietly.', '2023-12-07 13:45:00', '2023-12-07 13:45:00'),
(10, 5, 4.70, 'Great picture quality', 'The television offers vibrant colors and excellent picture clarity.', '2023-12-20 20:00:00', '2023-12-22 09:15:00'),
(1, 2, 4.40, 'Highly efficient', 'This washing machine saves water and energy while cleaning effectively.', '2023-12-04 08:00:00', '2023-12-06 14:30:00'),
(2, 3, 4.50, 'Very spacious', 'The refrigerator has a modern design and is very spacious.', '2023-12-14 19:30:00', '2023-12-14 19:30:00'),
(3, 4, 4.10, 'Good value', 'The microwave oven is reliable and worth the price.', '2023-12-06 10:15:00', '2023-12-07 16:00:00'),
(4, 5, 4.80, 'Beautiful design', 'The dining table is beautiful and blends well with the decor.', '2023-12-11 17:45:00', '2023-12-12 10:20:00'),
(5, 1, 4.95, 'Outstanding phone', 'The smartphone is feature-packed and offers excellent performance.', '2023-12-09 21:00:00', '2023-12-09 21:00:00'),
(6, 2, 4.70, 'Excellent choice', 'This laptop is perfect for work and entertainment.', '2023-12-02 14:00:00', '2023-12-03 09:30:00'),
(7, 3, 4.20, 'Good quality', 'The garden chair is lightweight yet sturdy.', '2023-12-16 10:30:00', '2023-12-17 12:00:00'),
(8, 4, 4.60, 'Highly recommend', 'The blender is easy to clean and works efficiently.', '2023-12-19 18:00:00', '2023-12-19 18:00:00'),
(9, 5, 4.85, 'Works perfectly', 'The air conditioner keeps the room cool even in extreme heat.', '2023-12-13 22:15:00', '2023-12-14 08:00:00'),
(10, 1, 4.75, 'Fantastic TV', 'The television has great sound and an intuitive interface.', '2023-12-17 20:45:00', '2023-12-18 09:45:00'),
(1, 1, 2.50, 'Not very efficient', 'The washing machine struggles with heavy loads and is quite noisy.', '2023-12-01 08:15:00', '2023-12-02 09:30:00'),
(2, 2, 1.80, 'Disappointing', 'The refrigerator is smaller than advertised and not energy-efficient.', '2023-12-03 14:00:00', '2023-12-04 16:00:00'),
(3, 3, 3.20, 'Average performance', 'The microwave oven heats unevenly and takes longer than expected.', '2023-12-05 19:45:00', '2023-12-05 19:45:00'),
(4, 4, 4.00, 'Decent quality', 'The dining table is nice but scratches easily.', '2023-12-07 12:30:00', '2023-12-07 12:30:00'),
(5, 5, 5.00, 'Fantastic phone', 'The smartphone is perfect in every way. Highly recommend!', '2023-12-10 09:00:00', '2023-12-10 09:00:00'),
(6, 1, 1.50, 'Terrible experience', 'The laptop crashes frequently and is slower than expected.', '2023-12-11 11:15:00', '2023-12-12 08:00:00'),
(7, 2, 2.80, 'Uncomfortable', 'The garden chair looks good but is uncomfortable for long use.', '2023-12-13 15:45:00', '2023-12-13 15:45:00'),
(8, 3, 4.50, 'Excellent blender', 'The blender works great and is easy to clean.', '2023-12-15 10:00:00', '2023-12-15 10:00:00'),
(9, 4, 3.00, 'Okay product', 'The air conditioner cools the room but is very noisy.', '2023-12-17 18:30:00', '2023-12-18 09:15:00'),
(10, 5, 4.75, 'Impressive TV', 'The television has excellent picture quality but mediocre sound.', '2023-12-20 20:00:00', '2023-12-21 12:30:00'),
(1, 2, 0.50, 'Horrible machine', 'This washing machine broke down after two weeks of use.', '2023-12-02 07:00:00', '2023-12-03 08:00:00'),
(2, 3, 2.30, 'Not worth it', 'The refrigerator is overpriced and has poor build quality.', '2023-12-04 14:30:00', '2023-12-05 09:00:00'),
(3, 4, 3.50, 'Decent microwave', 'The microwave oven is functional but lacks advanced features.', '2023-12-06 16:15:00', '2023-12-06 16:15:00'),
(4, 5, 4.20, 'Nice design', 'The dining table looks great but assembly was difficult.', '2023-12-08 11:00:00', '2023-12-08 11:00:00'),
(5, 1, 3.00, 'Average phone', 'The smartphone is okay but has a short battery life.', '2023-12-09 13:30:00', '2023-12-10 10:00:00'),
(6, 2, 4.50, 'Great laptop', 'The laptop is powerful and lightweight, perfect for work.', '2023-12-12 14:00:00', '2023-12-12 14:00:00'),
(7, 3, 1.80, 'Poor quality', 'The garden chair broke after a month of use.', '2023-12-14 17:45:00', '2023-12-15 09:30:00'),
(8, 4, 4.00, 'Good blender', 'The blender is reliable and easy to operate.', '2023-12-16 08:30:00', '2023-12-16 08:30:00'),
(9, 5, 2.50, 'Noisy AC', 'The air conditioner cools well but is too loud.', '2023-12-18 19:15:00', '2023-12-19 10:45:00'),
(10, 1, 5.00, 'Outstanding TV', 'The television is amazing for movies and gaming.', '2023-12-21 22:00:00', '2023-12-22 11:15:00'),
(1, 3, 4.00, 'Good machine', 'The washing machine is efficient and easy to use.', '2023-12-03 10:00:00', '2023-12-03 10:00:00'),
(2, 4, 3.00, 'Mediocre fridge', 'The refrigerator is fine but nothing special.', '2023-12-05 12:30:00', '2023-12-05 12:30:00'),
(3, 5, 2.00, 'Not great', 'The microwave oven is unreliable and takes too long to heat.', '2023-12-07 15:45:00', '2023-12-07 15:45:00'),
(4, 1, 4.80, 'Beautiful table', 'The dining table is sturdy and has a premium finish.', '2023-12-09 14:00:00', '2023-12-09 14:00:00'),
(5, 2, 0.00, 'Worst phone ever', 'The smartphone constantly crashes and is unusable.', '2023-12-11 08:00:00', '2023-12-12 09:15:00'),
(6, 3, 4.90, 'Top-notch laptop', 'The laptop is perfect for gaming and multitasking.', '2023-12-13 11:30:00', '2023-12-13 11:30:00'),
(7, 4, 3.80, 'Nice chair', 'The garden chair is comfortable but overpriced.', '2023-12-15 16:00:00', '2023-12-16 08:30:00'),
(8, 5, 1.20, 'Terrible blender', 'The blender leaks and is difficult to clean.', '2023-12-17 09:45:00', '2023-12-17 09:45:00'),
(9, 1, 5.00, 'Excellent AC', 'The air conditioner is quiet and cools the room perfectly.', '2023-12-19 20:00:00', '2023-12-19 20:00:00'),
(10, 2, 2.50, 'Mediocre TV', 'The television has poor sound quality and an outdated interface.', '2023-12-21 18:30:00', '2023-12-22 10:00:00'),
(1, 4, 4.30, 'Efficient washer', 'The washing machine is reliable and saves water.', '2023-12-04 11:00:00', '2023-12-04 11:00:00'),
(2, 5, 3.50, 'Decent fridge', 'The refrigerator is spacious but could be more energy-efficient.', '2023-12-06 13:30:00', '2023-12-06 13:30:00'),
(3, 1, 2.70, 'Below average', 'The microwave oven is slow and hard to clean.', '2023-12-08 15:45:00', '2023-12-08 15:45:00'),
(4, 2, 4.50, 'Great purchase', 'The dining table is stylish and well-built.', '2023-12-10 14:00:00', '2023-12-10 14:00:00'),
(5, 3, 4.70, 'Excellent phone', 'The smartphone is fast and has a great camera.', '2023-12-12 09:00:00', '2023-12-12 09:00:00'),
(6, 4, 1.00, 'Waste of money', 'The laptop is slow and freezes constantly.', '2023-12-14 10:15:00', '2023-12-14 10:15:00'),
(7, 5, 4.00, 'Good value', 'The garden chair is sturdy and weather-resistant.', '2023-12-16 11:30:00', '2023-12-16 11:30:00'),
(8, 1, 2.20, 'Not durable', 'The blender stopped working after a few weeks.', '2023-12-18 13:45:00', '2023-12-18 13:45:00'),
(9, 2, 4.60, 'Quiet and efficient', 'The air conditioner is energy-efficient and very quiet.', '2023-12-20 16:00:00', '2023-12-20 16:00:00'),
(10, 3, 3.80, 'Good TV', 'The television has decent features but lacks premium quality.', '2023-12-22 19:15:00', '2023-12-23 09:30:00');
