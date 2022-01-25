CREATE DATABASE cyberteam3;

DROP TABLE IF EXISTS user_profile;
DROP TABLE IF EXISTS items;
DROP TABLE IF EXISTS profile_items;

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
	profile_id INT NOT NULL,
    item_id INT NOT NULL,
    CONSTRAINT fk_profile_item FOREIGN KEY (profile_id) REFERENCES user_profile (profile_id) ON DELETE CASCADE,
    CONSTRAINT fk_item_profile FOREIGN KEY (item_id) REFERENCES item (item_id) ON DELETE CASCADE
);