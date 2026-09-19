-- V1: Create the initial orders table
-- Using BIGSERIAL for auto-incrementing Long IDs

CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    product_id VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    status VARCHAR(50) NOT NULL
);