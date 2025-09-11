CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS sales_order (
    id UUID PRIMARY KEY,
    num_order BIGINT,
    num_item INT,
    status VARCHAR(255),
    created TIMESTAMP,
    created_by VARCHAR(255),
    updated TIMESTAMP,
    updated_by VARCHAR(255),
    val_total NUMERIC(19, 2),
    product_id VARCHAR(255),
    price NUMERIC(19, 2),
    quantity INT,
    val_base_calculo NUMERIC(26, 10) null,
    val_icsm NUMERIC(26, 10) null,
    json_tabela_cheia JSON NULL,
    json_tabela_praticada JSON NULL,
    active BOOLEAN DEFAULT TRUE
);

CREATE INDEX idx_sales_order_num_order_active ON sales_order (num_order, active);

CREATE UNIQUE INDEX uidx_sales_order_num_order_num_item_active ON sales_order (num_order, num_item, active) WHERE active = true;