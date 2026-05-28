-- =====================================================
-- DDL para el esquema de órdenes
-- Base de datos separada del catálogo (requisito de la actividad)
-- =====================================================

CREATE SCHEMA IF NOT EXISTS orders_db;
USE orders_db;

-- =====================================================
-- Tabla principal de órdenes
-- =====================================================

CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    
    user_id BIGINT NOT NULL,
    user_email VARCHAR(255),
    
    total_amount DECIMAL(10,2) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_created_at (created_at)
);

-- =====================================================
-- Tabla de items de la orden
-- =====================================================

CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    
    order_id BIGINT NOT NULL,
    book_id INT NOT NULL,
    book_title VARCHAR(255) NOT NULL,
    book_isbn VARCHAR(20),
    
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    
    FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE,
    INDEX idx_order_id (order_id),
    INDEX idx_book_id (book_id)
);
