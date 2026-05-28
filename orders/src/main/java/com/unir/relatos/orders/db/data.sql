-- =====================================================
-- DML - Datos de ejemplo para órdenes
-- =====================================================

USE orders_db;

-- Insertar algunas órdenes de ejemplo
INSERT INTO orders (user_id, user_email, total_amount, status, created_at) VALUES
(1, 'usuario1@example.com', 54.48, 'CONFIRMED', '2024-01-15 10:30:00'),
(1, 'usuario1@example.com', 35.99, 'CONFIRMED', '2024-01-20 14:45:00'),
(2, 'usuario2@example.com', 89.97, 'CONFIRMED', '2024-01-22 09:15:00'),
(1, 'usuario1@example.com', 22.90, 'CONFIRMED', '2024-02-01 16:20:00'),
(3, 'usuario3@example.com', 41.49, 'CONFIRMED', '2024-02-05 11:00:00');

-- Insertar items para la orden 1 (usuario 1)
INSERT INTO order_items (order_id, book_id, book_title, book_isbn, quantity, unit_price, subtotal) VALUES
(1, 1, 'Lost Signal', 'ISBN200001', 1, 18.50, 18.50),
(1, 2, 'Shadow Protocol', 'ISBN200002', 1, 35.99, 35.99);

-- Insertar items para la orden 2 (usuario 1)
INSERT INTO order_items (order_id, book_id, book_title, book_isbn, quantity, unit_price, subtotal) VALUES
(2, 2, 'Shadow Protocol', 'ISBN200002', 1, 35.99, 35.99);

-- Insertar items para la orden 3 (usuario 2)
INSERT INTO order_items (order_id, book_id, book_title, book_isbn, quantity, unit_price, subtotal) VALUES
(3, 3, 'Beyond Mars', 'ISBN200003', 2, 22.90, 45.80),
(3, 6, 'Focus Mode', 'ISBN200006', 1, 16.75, 16.75),
(3, 11, 'Empire Rise', 'ISBN200011', 1, 25.99, 25.99);

-- Insertar items para la orden 4 (usuario 1)
INSERT INTO order_items (order_id, book_id, book_title, book_isbn, quantity, unit_price, subtotal) VALUES
(4, 3, 'Beyond Mars', 'ISBN200003', 1, 22.90, 22.90);

-- Insertar items para la orden 5 (usuario 3)
INSERT INTO order_items (order_id, book_id, book_title, book_isbn, quantity, unit_price, subtotal) VALUES
(5, 4, 'Silent Justice', 'ISBN200004', 1, 14.99, 14.99),
(5, 11, 'Empire Rise', 'ISBN200011', 1, 25.99, 25.99);
