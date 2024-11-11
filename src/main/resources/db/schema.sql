-- schema.sql
CREATE TABLE IF NOT EXISTS clicker_state (
    id       VARCHAR(60)  PRIMARY KEY,
    stone_counter INT,
    stone_multiplier INT,
    wood_counter INT,
    wood_multiplier INT,
    stone_mines INT,
    wood_camps INT,
    wheat_counter INT,
    wheat_multiplier INT,
    wheat_fields INT,
    population INT
    );