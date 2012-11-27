
INSERT INTO Book VALUES ('callNumber1', '013048623X', 'Java', 'Johnston', 'Pearson', '2004');
INSERT INTO Book VALUES ('callNumber2', '9781550289473', 'Procrastination', 'Diane', 'Publisher', '2006');
INSERT INTO Book VALUES ('callNumber3', '1345454SD45', 'Harry Potter', 'Rowling', 'Raincoast', '1998');
INSERT INTO Book VALUES ('callNumber4', '236948FFK4', 'Flowers', 'J.Mast', 'Pearson', '2012');
INSERT INTO Book VALUES ('callNumber5', '45KGNSJDF84', 'Dogs', 'R.Mclean', 'Cal Publishers', '2002');
INSERT INTO Book VALUES ('callNumber6', 'DRT434543S', 'Coffee', 'James', 'RainPublishers', '2008');
INSERT INTO Book VALUES ('callNumber7', '34575HHHGGG', 'Computer Games', 'Binary', 'UBCPubl', '2002');
INSERT INTO Book VALUES ('callNumber8', '45654DFG45', 'Nature Walks', 'Wright', 'Casper', '1998');
INSERT INTO Book VALUES ('callNumber9', '10096GHFGH4', 'Waterfalls', 'Darby', 'Pearson', '2005');
INSERT INTO Book VALUES ('callNumber10', '004858576J', 'Clothing', 'Leroy', 'Canada Publishing', '2001');
INSERT INTO Book VALUES ('callNumber12', '474501793CB', 'Farm Animals', 'Garrett', 'Van Publishers', '1999');
INSERT INTO Book VALUES ('callNumber13', 'DFJG934854', 'Baked Goods', 'Stewart', 'Cooks Publishing', '20002');
INSERT INTO Book VALUES ('callNumber14', '945J4580GK', 'New York', 'Peters', 'BC Publishing', '2001');

INSERT INTO BorrowerType VALUES ('student', 2);
INSERT INTO BorrowerType VALUES ('faculty', 12);
INSERT INTO BorrowerType VALUES ('librarian', 6);

INSERT INTO Borrower VALUES ('a', 'password', 'John', '1343 Wayfinding Street', '555-666-7777', 'doe@coolmail.com', '39878010', '01/01/2013', 'student');
INSERT INTO Borrower VALUES ('b', 'password', 'Jane', '3408 Main Street', '555-342-7777', 'doe@heatmail.com', '42398732', '01/02/2013', 'student');
INSERT INTO Borrower VALUES ('c', 'sdfsjkld', 'George', '2349 Baker Street', '555-342-7890', 'data@base.com', '79098789', '02/01/2013', 'faculty');
INSERT INTO Borrower VALUES ('d', 'jkl;lkjk', 'Diane', '3298 Victoria Street', '555-234-7890', 'sql@plus.com', '3958792', '02/01/2014', 'librarian');

INSERT INTO HasSubject VALUES ('callNumber1', 'Programming');
INSERT INTO HasSubject VALUES ('callNumber1', 'Fun');
INSERT INTO HasSubject VALUES ('callNumber2', 'Productivity');
INSERT INTO HasSubject VALUES ('callNumber2', 'Fun');

INSERT INTO HasAuthor VALUES ('callNumber1', 'Paul');
INSERT INTO HasAuthor VALUES ('callNumber1', 'Meghan');
INSERT INTO HasAuthor VALUES ('callNumber2', 'John');
INSERT INTO HasAuthor VALUES ('callNumber2', 'Mary');

INSERT INTO BookCopy VALUES ('callNumber1', '1', 'in');
INSERT INTO BookCopy VALUES ('callNumber1', '2', 'out');
INSERT INTO BookCopy VALUES ('callNumber1', '3', 'out');
INSERT INTO BookCopy VALUES ('callNumber2', '1', 'in');
INSERT INTO BookCopy VALUES ('callNumber2', '2', 'out');
INSERT INTO BookCopy VALUES ('callNumber3', '1', 'in');

INSERT INTO HoldRequest VALUES ('a', 'a', 'callNumber3', '20/11/2012');

INSERT INTO Borrowing VALUES ('a', 'b', 'callNumber1', '3', '14/10/2012', '14/11/2012');
INSERT INTO Borrowing VALUES ('b', 'd', 'callNumber2', '2', '14/05/2012', '14/06/2012');
INSERT INTO Borrowing VALUES ('c', 'd', 'callNumber2', '1', '14/06/2012', '14/07/2012');
INSERT INTO Borrowing VALUES ('d', 'd', 'callNumber2', '1', '14/11/2012', '22/11/2012');
INSERT INTO Borrowing VALUES ('e', 'd', 'callNumber1', '2', '14/05/2012', '14/12/2012');

INSERT INTO Fine VALUES ('a', 12.10, '14/6/2012', NULL, 'b');


