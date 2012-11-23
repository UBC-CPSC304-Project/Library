INSERT INTO Book VALUES ('QA76.73 J38 2004', '013048623X', 'Java', 'Johnston', 'Pearson', '2004');
INSERT INTO Book VALUES ('BF637.P76 P47 2006', '9781550289473', 'Procrastination', 'Diane', 'Publisher', '2006');
INSERT INTO Book VALUES ('PZ7.R79 835 1998', '1551922444', 'Harry Potter', 'Rowling', 'Raincoast', '1998');
INSERT INTO Book VALUES ('Z7.RE83 1233 2008', '3454345', 'Dogs', 'R.Mclean', 'Pearson', '2008');

INSERT INTO BorrowerType VALUES ('student', 2);
INSERT INTO BorrowerType VALUES ('faculty', 12);
INSERT INTO BorrowerType VALUES ('librarian', 6);

INSERT INTO Borrower VALUES ('0297532234a', 'password', 'John', '1343 Wayfinding Street', '555-666-7777', 'doe@coolmail.com', '39878010', '01/01/2013', 'student');

INSERT INTO HasSubject VALUES ('Z7.RE83 1233 2008', 'Pets');
INSERT INTO HasSubject VALUES ('QA76.73 J38 2004', 'ComputerScience');
