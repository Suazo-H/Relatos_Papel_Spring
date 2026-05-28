-- =====================================================
-- DDL para el esquema de catálogo de libros
-- =====================================================

CREATE SCHEMA IF NOT EXISTS catalogue_db;
USE catalogue_db;

-- =====================================================
-- Tabla principal de libros
-- =====================================================

CREATE TABLE books (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(100),

    price DECIMAL(10,2) NOT NULL,
    stock INT NOT NULL DEFAULT 0,

    isbn VARCHAR(20) NOT NULL UNIQUE,

    rating DECIMAL(3,2) DEFAULT 0.00,
    reviews INT DEFAULT 0,

    pages INT,
    language VARCHAR(50),
    publisher VARCHAR(255),
    format VARCHAR(50),

    synopsis TEXT,

    publishedYear INT,
    image VARCHAR(500),
    
    visible BOOLEAN DEFAULT TRUE,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    INDEX idx_title (title),
    INDEX idx_author (author),
    INDEX idx_genre (genre),
    INDEX idx_isbn (isbn),
    INDEX idx_visible (visible)
);
