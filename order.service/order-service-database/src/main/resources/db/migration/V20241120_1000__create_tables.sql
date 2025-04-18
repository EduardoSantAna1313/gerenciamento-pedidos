CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS Orders (
    id UUID PRIMARY KEY,
    status VARCHAR(255),
    created TIMESTAMP,
    created_by VARCHAR(255),
    updated TIMESTAMP,
    updated_by VARCHAR(255),
    total NUMERIC(19, 2)
);

CREATE TABLE IF NOT EXISTS Items (
    id UUID PRIMARY KEY,
    product VARCHAR(255),
    price NUMERIC(19, 2),
    quantity INT,
    order_id UUID REFERENCES Orders(id) ON DELETE CASCADE
);

