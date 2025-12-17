-- SQL statements for backlog tracker
CREATE DATABASE backlogtracker;

CREATE TABLE users (
	id bigserial PRIMARY KEY,
	username VARCHAR ( 255 ) NOT NULL,
	password VARCHAR ( 255 ) NOT NULL,
	email VARCHAR ( 255 ) UNIQUE NOT NULL,
	created_on TIMESTAMP NOT NULL,
    last_login TIMESTAMP 
);

CREATE TABLE roles (
  id bigserial PRIMARY KEY,
  name varchar(255) NOT NULL,
  description varchar(255) NOT NULL,
  created_by varchar(255) NOT NULL,
  created_date varchar(255) NOT NULL,
  updated_by varchar(255) NOT NULL,
  updated_date varchar(255) NOT NULL
);

CREATE TABLE user_role (
  users_id int NOT NULL,
  roles_id int NOT NULL,
  PRIMARY KEY (users_id,roles_id),
  CONSTRAINT user_role_ibfk_1 
   FOREIGN KEY (users_id) REFERENCES users (id) ON DELETE CASCADE,
  CONSTRAINT user_role_ibfk_2 
   FOREIGN KEY (roles_id) REFERENCES roles (id) ON DELETE CASCADE
);

insert into users 
(id, username, password, email, created_on, last_login) values
(1, 'Muzakkir', '$2b$12$./nGN7pUegqpPS07ZUXd4.ljn.wwekRW1u8rjUPjj0V0u/Rja9BrC', '',NOW(),null);

insert into roles 
(id, name, description, created_by, created_date, updated_by, updated_date) values
(1, 'ADMIN', 'Role for all the admin operations', 'SYSTEM',NOW(),'SYSTEM',NOW()),
(2, 'MANAGER', 'Role for all the manager operations', 'SYSTEM',NOW(),'SYSTEM',NOW());

insert into user_role 
(users_id, roles_id) values
(1, 1),
(1, 2);

CREATE TABLE password_reset_tokens (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    token VARCHAR(64) NOT NULL UNIQUE,   -- store 16-digit code as string; or store hash of it (better)
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    expires_at TIMESTAMP NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE items (
    id               BIGSERIAL PRIMARY KEY,
    sku              VARCHAR(50) UNIQUE NOT NULL,
    name             VARCHAR(100) NOT NULL,
    category         VARCHAR(50),
    unit_price       DECIMAL(12,2),
    active           BOOLEAN DEFAULT TRUE,
    created_at       TIMESTAMP DEFAULT NOW()
);

CREATE TABLE suppliers (
    id               BIGSERIAL PRIMARY KEY,
    name             VARCHAR(100) NOT NULL,
    contact          VARCHAR(100),
    phone            VARCHAR(20),
    email            VARCHAR(100),
    active           BOOLEAN DEFAULT TRUE
);

CREATE TABLE customer (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    phone VARCHAR(20),
    address VARCHAR(255),
    created_by BIGSERIAL NOT NULL,
  	created_date TIMESTAMP NOT NULL,
  	updated_by BIGSERIAL NOT NULL,
  	updated_date TIMESTAMP NOT NULL,
  	CONSTRAINT fk_customer_user_cb FOREIGN KEY (created_by) REFERENCES users(id),
  	CONSTRAINT fk_customer_user_ub FOREIGN KEY (updated_by) REFERENCES users(id)
);

alter table customer add column active BOOLEAN DEFAULT TRUE;

CREATE TABLE customer_order (
    id BIGSERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL,
    order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(12,2) DEFAULT 0,
    created_by BIGSERIAL NOT NULL,
  	created_date TIMESTAMP NOT NULL,
  	updated_by BIGSERIAL NOT NULL,
  	updated_date TIMESTAMP NOT NULL,
  	CONSTRAINT fk_customer_order_user_cb FOREIGN KEY (created_by) REFERENCES users(id),
  	CONSTRAINT fk_customer_order_user_ub FOREIGN KEY (updated_by) REFERENCES users(id),
    CONSTRAINT fk_order_customer FOREIGN KEY (customer_id) REFERENCES customer(id)
);

CREATE TABLE customer_order_status (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'NEW', -- NEW, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    created_by BIGSERIAL NOT NULL,
  	created_date TIMESTAMP NOT NULL,
  	updated_by BIGSERIAL NOT NULL,
  	updated_date TIMESTAMP NOT NULL,
  	CONSTRAINT fk_ccustomer_order_status_cb FOREIGN KEY (created_by) REFERENCES users(id),
  	CONSTRAINT fk_customer_order_status_ub FOREIGN KEY (updated_by) REFERENCES users(id),
    CONSTRAINT fk_customer_order_status FOREIGN KEY (order_id) REFERENCES customer_order(id)
);

CREATE TABLE customer_order_item (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGSERIAL NOT NULL,
    item_id BIGSERIAL NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_order_item_order FOREIGN KEY (order_id) REFERENCES customer_order(id),
    CONSTRAINT fk_order_item_item FOREIGN KEY (item_id) REFERENCES items(id)
);

CREATE TABLE purchase_order (
    id BIGSERIAL PRIMARY KEY,
    supplier_id BIGINT NOT NULL,
    po_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(12,2) DEFAULT 0,
	created_by BIGSERIAL NOT NULL,
  	created_date TIMESTAMP NOT NULL,
  	updated_by BIGSERIAL NOT NULL,
  	updated_date TIMESTAMP NOT NULL,
  	CONSTRAINT fk_purchase_order_user_cb FOREIGN KEY (created_by) REFERENCES users(id),
  	CONSTRAINT fk_purchase_order_user_ub FOREIGN KEY (updated_by) REFERENCES users(id),
    CONSTRAINT fk_purchase_order_supplier FOREIGN KEY (supplier_id) REFERENCES suppliers(id)
);



CREATE TABLE purchase_order_status (
    id BIGSERIAL PRIMARY KEY,
    po_id BIGINT NOT NULL,
    status VARCHAR(20) DEFAULT 'NEW', -- NEW, PROCESSING, SHIPPED, DELIVERED, CANCELLED
    created_by BIGSERIAL NOT NULL,
  	created_date TIMESTAMP NOT NULL,
  	updated_by BIGSERIAL NOT NULL,
  	updated_date TIMESTAMP NOT NULL,
  	CONSTRAINT fk_purchase_order_status_cb FOREIGN KEY (created_by) REFERENCES users(id),
  	CONSTRAINT fk_purchase_order_status_ub FOREIGN KEY (updated_by) REFERENCES users(id),
    CONSTRAINT fk_purchase_order_status FOREIGN KEY (po_id) REFERENCES purchase_order(id)
);


CREATE TABLE purchase_order_item (
    id BIGSERIAL PRIMARY KEY,
    po_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,
    CONSTRAINT fk_po_item_order FOREIGN KEY (po_id) REFERENCES purchase_order(id),
    CONSTRAINT fk_po_item_item FOREIGN KEY (item_id) REFERENCES items(id)
);

CREATE TABLE inventory_movement (
    id BIGSERIAL PRIMARY KEY,
    item_id BIGSERIAL NOT NULL,
    quantity INT NOT NULL,              -- + for IN, - for OUT
    movement_type VARCHAR(20) NOT NULL, -- IN, OUT, ADJUST
    reference_type VARCHAR(20),         -- PO, CO, ADJ
    created_by BIGSERIAL NOT NULL,
  	created_date TIMESTAMP NOT NULL,
  	updated_by BIGSERIAL NOT NULL,
  	updated_date TIMESTAMP NOT NULL,
  	CONSTRAINT fk_inventory_movement_user_cb FOREIGN KEY (created_by) REFERENCES users(id),
  	CONSTRAINT fk_inventory_movement_user_ub FOREIGN KEY (updated_by) REFERENCES users(id),
    CONSTRAINT fk_inventory_movement_items FOREIGN KEY (item_id) REFERENCES items(id)
);


CREATE TABLE inventory (
	id BIGSERIAL PRIMARY KEY,
    item_id BIGSERIAL PRIMARY KEY,
    quantity INT NOT NULL DEFAULT 0,
    updated_at TIMESTAMP,
    CONSTRAINT fk_inventory_movement_items FOREIGN KEY (item_id) REFERENCES items(id)
);

CREATE MATERIALIZED VIEW dashboard_snapshot AS
SELECT
    (SELECT SUM(quantity) FROM inventory)                AS total_items,
    (SELECT COUNT(*) FROM inventory WHERE quantity < reorder_level) AS low_stock_items,
    (SELECT COUNT(*) FROM purchase_order po
        JOIN purchase_order_status ps ON ps.po_id = po.id
        WHERE ps.status = 'CREATED'
        AND ps.created_date = (
            SELECT MAX(ps2.created_date)
            FROM purchase_order_status ps2
            WHERE ps2.po_id = po.id
        )
    ) AS pending_orders,
    (SELECT COUNT(*) FROM purchase_order
        WHERE DATE(po_date) = CURRENT_DATE
    ) AS today_pos;
