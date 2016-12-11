ALTER TABLE Account
  RENAME User;
ALTER TABLE Ticket
  RENAME Task;

ALTER TABLE User
  ADD role ENUM ('admin', 'user') NOT NULL DEFAULT 'user';

ALTER TABLE Project
  MODIFY name VARCHAR(100) NOT NULL;
