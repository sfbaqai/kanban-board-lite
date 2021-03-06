CREATE TABLE IF NOT EXISTS Account (
  id         INTEGER UNSIGNED    NOT NULL AUTO_INCREMENT,
  first_name VARCHAR(40)         NOT NULL,
  last_name  VARCHAR(40)         NOT NULL,
  email      VARCHAR(100) UNIQUE NOT NULL,
  password   VARBINARY(64)       NOT NULL, -- Password Hash using PBKDF2 Hmac SHA512
  salt       VARBINARY(64)       NOT NULL, -- Password Hash Salt
	PRIMARY KEY (id),
  UNIQUE KEY uk_email (email),
  KEY k_email (email)
);

CREATE TABLE IF NOT EXISTS Project (
  id          Integer Unsigned Auto_Increment,
  name        VARCHAR(200) UNIQUE NOT NULL,
  description Text,
  owner       Integer Unsigned    NOT NULL,
  parent      INTEGER UNSIGNED,
	PRIMARY KEY (id),
  INDEX (name),
	FOREIGN KEY fk_owner (owner) REFERENCES Account (id) ON DELETE RESTRICT,
	FOREIGN KEY fk_parent (parent) REFERENCES Project (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS TICKET_STATUS (
	title Varchar(20) NOT NULL UNIQUE,
	level Smallint(2) Unsigned NOT NULL UNIQUE,
	PRIMARY KEY (level),
	INDEX (title)
);

CREATE TABLE IF NOT EXISTS TICKET_PRIORITY (
	title Varchar(20) NOT NULL UNIQUE,
	level Smallint(3) Unsigned NOT NULL UNIQUE,
	PRIMARY KEY (level),
	INDEX (title)
);

CREATE TABLE IF NOT EXISTS Ticket (
	id Integer Unsigned Auto_Increment,
	title Varchar(100) NOT NULL,
	description Text,
	status Smallint(2) Unsigned NOT NULL DEFAULT 0,
	priority Smallint(3) Unsigned NOT NULL DEFAULT 0,
	due Date,
	assigned_to Integer Unsigned,
	project Integer Unsigned NOT NULL,
	PRIMARY KEY (id),
	FOREIGN KEY fk_assigned_to (assigned_to) REFERENCES Account (id) ON DELETE SET NULL,
	FOREIGN KEY fk_priority (priority) REFERENCES TICKET_PRIORITY (level) ON DELETE RESTRICT,
	FOREIGN KEY fk_status (status) REFERENCES TICKET_STATUS (level) ON DELETE RESTRICT,
	FOREIGN KEY fk_project (project) REFERENCES Project (id) ON DELETE CASCADE
);
