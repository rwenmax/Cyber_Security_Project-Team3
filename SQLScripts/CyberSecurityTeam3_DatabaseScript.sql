DROP DATABASE cyberteam3;
CREATE DATABASE cyberteam3;
USE cyberteam3;

DROP TABLE IF EXISTS token;
DROP TABLE IF EXISTS profile_item;
DROP TABLE IF EXISTS user_profile;
DROP TABLE IF EXISTS item;

CREATE TABLE user_profile (
	profile_id INT NOT NULL AUTO_INCREMENT,
    profile_username VARCHAR(50) NOT NULL,
    profile_password VARCHAR(500) NOT NULL,
    PRIMARY KEY (profile_id)
);

CREATE TABLE item (
	item_id INT NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(100) NOT NULL,
    item_type VARCHAR(20) NOT NULL,
    PRIMARY KEY (item_id)
);

CREATE TABLE profile_item (
	id INT NOT NULL AUTO_INCREMENT,
	profile_id INT NOT NULL,
    item_id INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_profile_item FOREIGN KEY (profile_id) REFERENCES user_profile (profile_id) ON DELETE CASCADE,
    CONSTRAINT fk_item_profile FOREIGN KEY (item_id) REFERENCES item (item_id) ON DELETE CASCADE
);

CREATE TABLE token (
	token_id INT NOT NULL AUTO_INCREMENT,
    token VARCHAR(100) NOT NULL,
    profile_id INT NOT NULL,
    PRIMARY KEY (token_id),
    CONSTRAINT fk_profile FOREIGN KEY (profile_id) REFERENCES user_profile (profile_id) ON DELETE CASCADE,
    ts_expiration TIMESTAMP DEFAULT (CURRENT_TIMESTAMP + INTERVAL 1 WEEK)
);

DROP event if exists deleteToken;

CREATE event deleteToken
    ON schedule EVERY 12 HOUR
    DO
        DELETE FROM token
        WHERE token.ts_expiration <= CURRENT_TIMESTAMP;