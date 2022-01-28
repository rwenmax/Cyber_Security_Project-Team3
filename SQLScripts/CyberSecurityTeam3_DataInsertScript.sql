USE cyberteam3;

INSERT INTO user_profile (profile_username, profile_password)
VALUES 
	("kamil", 123),
    ("mihai", 321),
    ("pruthvi", 1234),
    ("ishmael", 43321),
    ("osama", "password");
    
INSERT INTO token (token, profile_id)
VALUES 
	("abcdefg", 1),
	("qwerty", 2),
    ("nginx", 3),
    ("token", 5);

INSERT INTO item (item_name, item_type)
VALUES
	("tree", "package"),
    ("docker", "package"),
    ("docker.io","component"),
    ("youttube","app"),
    ("gitbash","app"),
    ("git","component"),
    ("nginx","package");
    
INSERT INTO profile_item (profile_id, item_id)
VALUES
	(1,1),
    (1,2),
    (1,3),
    (2,5),
    (2,6),
    (3,7),
    (4,4),
    (4,1),
    (5,1),
    (5,4);