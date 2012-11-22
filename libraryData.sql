INSERT INTO Book VALUES ('callNumber1', '013048623X', 'Java', 'Johnston', 'Pearson', '2004');
INSERT INTO Book VALUES ('callNumber2', '9781550289473', 'Procrastination', 'Diane', 'Publisher', '2006');
INSERT INTO Book VALUES ('callNumber3', '1551922444', 'Harry Potter', 'Rowling', 'Raincoast', '1998');

INSERT INTO BorrowerType VALUES ('student', 2);
INSERT INTO BorrowerType VALUES ('faculty', 4);
INSERT INTO BorrowerType VALUES ('staff', 4);

INSERT INTO Borrower VALUES ('a', 'password', 'John', '1343 Wayfinding Street', '555-666-7777', 'doe@coolmail.com', '39878010', '01/01/2013', 'student');
INSERT INTO Borrower VALUES ('b', 'password', 'Jane', '3408 Main Street', '555-342-7777', 'doe@heatmail.com', '42398732', '01/02/2013', 'student');
INSERT INTO Borrower VALUES ('c', 'sdfsjkld', 'George', '2349 Baker Street', '555-342-7890', 'data@base.com', '79098789', '02/01/2013', 'faculty');
INSERT INTO Borrower VALUES ('d', 'jkl;lkjk', 'Diane', '3298 Victoria Street', '555-234-7890', 'sql@plus.com', '3958792', '02/01/2014', 'staff');

INSERT INTO HasSubject VALUES ('callNumber1', 'Programming');
INSERT INTO HasSubject VALUES ('callNumber1', 'Fun');
INSERT INTO HasSubject VALUES ('callNumber2', 'Productivity');

INSERT INTO HasAuthor VALUES ('callNumber1', 'Paul');
INSERT INTO HasAuthor VALUES ('callNumber1', 'Meghan');
INSERT INTO HasAuthor VALUES ('callNumber2', 'John');
INSERT INTO HasAuthor VALUES ('callNumber2', 'Mary');

INSERT INTO BookCopy VALUES ('callNumber1', 'copyNo1', 'available');
INSERT INTO BookCopy VALUES ('callNumber1', 'copyNo2', 'reserved');
INSERT INTO BookCopy VALUES ('callNumber1', 'copyNo3', 'borrowed');
INSERT INTO BookCopy VALUES ('callNumber2', 'copyNo1', 'available');
INSERT INTO BookCopy VALUES ('callNumber2', 'copyNo2', 'borrowed');
INSERT INTO BookCopy VALUES ('callNumber3', 'copyNo1', 'reserved');

INSERT INTO HoldRequest VALUES ('a', 'a', 'callNumber3', '20/11/2012');

INSERT INTO Borrowing VALUES ('a', 'b', 'callNumber1', 'copyNo3', '14/10/2012', NULL);
INSERT INTO Borrowing VALUES ('b', 'd', 'callNumber2', 'copyNo2', '14/5/2012', NULL);

INSERT INTO Fine VALUES ('a', 12.10, '14/6/2012', NULL, 'b');








