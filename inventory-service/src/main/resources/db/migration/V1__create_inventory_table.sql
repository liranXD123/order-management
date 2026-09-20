-- V1: Create the initial inventory table
CREATE TABLE inventory (
    id BIGSERIAL PRIMARY KEY,
    product_id VARCHAR(255) NOT NULL UNIQUE,
    quantity INT NOT NULL
);

-- Insert some dummy data so we have inventory to test with later
INSERT INTO inventory (product_id, quantity) VALUES ('LAPTOP-X1', 10);
INSERT INTO inventory (product_id, quantity) VALUES ('MOUSE-M1', 50);
INSERT INTO inventory (product_id, quantity) VALUES ('KEYBOARD-K1', 0);