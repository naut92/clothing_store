INSERT INTO store_or_stock(store_stock) VALUES
('store'),
('stock');



INSERT INTO clothes (name, size, cost, color, type, description) VALUES
('clothes 1', 42, '1009.00 руб', 'red', 'dress', 'loren ipsum123'),
('clothes 2', 43, '2100.00 руб', 'black', 'pants', 'is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry''s'),
('clothes 3', 44, '459.00 руб', 'green', 'skirt', 'as opposed to using ''Content here, content here'', making it look like'),
('clothes 4', 45, '768.00 руб', 'blue', 'vest', 'web page editors now use Lorem Ipsum as their default model text,'),
('clothes 5', 46, '891.00 руб', 'white', 'shirt', 'and a search for ''lorem ipsum'' will uncover many web sites still in their infancy.'),
('clothes 6', 48, '644.00 руб', 'green', 'pants', 'sometimes by accident, sometimes on purpose (injected humour and the like).'),
('clothes 7', 50, '571.00 руб', 'blue', 'dress', 'Where can I get some? There are many variations of passages of Lorem Ipsum available, but the majority have suffered '),
('clothes 8', 52, '3381.00 руб', 'red', 'shirt', 'All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet.'),
('clothes 9', 47, '1200.00 руб', 'red', 'shirt', 'tend to repeat predefined chunks as necessary, making this the first true generator on the.');


INSERT INTO clothes_all (clothes_id, store_stok_id) VALUES
(1, 1),
(2, 2),
(3, 1),
(4, 2),
(5, 1),
(6, 2),
(7, 1),
(8, 2);
--(9, 1);

