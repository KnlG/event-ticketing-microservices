CREATE TABLE `order` (
    id bigint AUTO_INCREMENT PRIMARY KEY,
    total decimal(10, 2) NOT NULL,
    quantity bigint NOT NULL,
    placed_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    customer_id bigint,
    event_id bigint,
    CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customer(id) ON DELETE SET NULL,
    CONSTRAINT fk_event FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE SET NULL
);