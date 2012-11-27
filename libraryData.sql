
INSERT INTO Book VALUES ('callNumber1', '013048623X', 'Java', 'Johnston', 'Pearson', '2004');
INSERT INTO Book VALUES ('callNumber2', '97815502894', 'Procrastination', 'Diane', 'Publisher', '2006');
INSERT INTO Book VALUES ('callNumber3', '1345454SD45', 'Harry Potter', 'Rowling', 'Raincoast', '1998');
INSERT INTO Book VALUES ('callNumber4', '236948FFK4', 'Flowers', 'J.Mast', 'Pearson', '2012');
INSERT INTO Book VALUES ('callNumber5', '45KGNSJDF84', 'Dogs', 'R.Mclean', 'Cal Publishers', '2002');
INSERT INTO Book VALUES ('callNumber6', 'DRT434543S', 'Coffee', 'James', 'RainPublishers', '2008');
INSERT INTO Book VALUES ('callNumber7', '34575HHHGGG', 'Computer Games', 'Binary', 'UBCPubl', '2002');
INSERT INTO Book VALUES ('callNumber8', '45654DFG45', 'Nature Walks', 'Wright', 'Casper', '1998');
INSERT INTO Book VALUES ('callNumber9', '10096GHFGH4', 'Waterfalls', 'Darby', 'Pearson', '2005');
INSERT INTO Book VALUES ('callNumber10', '004858576J', 'Clothing', 'Leroy', 'Canada Publishing', '2001');
INSERT INTO Book VALUES ('callNumber11', '474501793CB', 'Tractors', 'Gary', 'BNF Publishers', '1999');
INSERT INTO Book VALUES ('callNumber12', '474501793CB', 'Farm Animals', 'Garrett', 'Van Publishers', '1999');
INSERT INTO Book VALUES ('callNumber13', 'DFJG934854', 'Baked Goods', 'Stewart', 'Cooks Publishing', '2002');
INSERT INTO Book VALUES ('callNumber14', '945J4580GK', 'New York', 'Peters', 'BC Publishing', '2001');

INSERT INTO BorrowerType VALUES ('student', 2);
INSERT INTO BorrowerType VALUES ('faculty', 12);
INSERT INTO BorrowerType VALUES ('librarian', 6);

INSERT INTO Borrower VALUES ('a', 'password', 'John', '1343 Wayfinding Street', '555-666-7777', 'doe@coolmail.com', '39878010', '01/01/2013', 'student');
INSERT INTO Borrower VALUES ('b', 'password', 'Jane', '3408 Main Street', '555-342-7777', 'doe@heatmail.com', '42398732', '01/02/2013', 'student');
INSERT INTO Borrower VALUES ('c', 'sdfsjkld', 'George', '2349 Baker Street', '555-342-7890', 'data@base.com', '79098789', '02/01/2013', 'faculty');
INSERT INTO Borrower VALUES ('d', 'jkl;lkjk', 'Diane', '312 5th Ave', '555-245-3333', 'sql@plus.com', '34541233', '02/05/2014', 'librarian');
INSERT INTO Borrower VALUES ('e', 'dfgffgg', 'Peter', '775 Red Street', '555-566-4444', 'pete@gmail.com', '12324433', '02/06/2013', 'student');
INSERT INTO Borrower VALUES ('f', 'paer656', 'Mark', '3342 Cordova', '555-332-4444', 'mark@hello.com', '44534544', '01/06/2013', 'student');
INSERT INTO Borrower VALUES ('g', 'sd23434665', 'Larry', '22-1058 Howe Street', '555-312-6666', 'larry@school.com', '79098549', '07/01/2013', 'faculty');
INSERT INTO Borrower VALUES ('h', 'jk22ffggf', 'Rita', '3298 Heather Street', '555-343-2222', 'rita@wow.com', '39929987', '09/01/2014', 'librarian');

INSERT INTO HasSubject VALUES ('callNumber1', 'Programming');
INSERT INTO HasSubject VALUES ('callNumber1', 'Fun');
INSERT INTO HasSubject VALUES ('callNumber2', 'Productivity');
INSERT INTO HasSubject VALUES ('callNumber3', 'Fun');
INSERT INTO HasSubject VALUES ('callNumber4', 'Nature');
INSERT INTO HasSubject VALUES ('callNumber4', 'Plants');
INSERT INTO HasSubject VALUES ('callNumber5', 'Pets');
INSERT INTO HasSubject VALUES ('callNumber6', 'Drinks');
INSERT INTO HasSubject VALUES ('callNumber7', 'Fun');
INSERT INTO HasSubject VALUES ('callNumber8', 'Hiking');
INSERT INTO HasSubject VALUES ('callNumber8', 'Productivity');
INSERT INTO HasSubject VALUES ('callNumber9', 'Nature');
INSERT INTO HasSubject VALUES ('callNumber10', 'Fashion');
INSERT INTO HasSubject VALUES ('callNumber11', 'Farming');
INSERT INTO HasSubject VALUES ('callNumber12', 'Farming');
INSERT INTO HasSubject VALUES ('callNumber13', 'Kitchen');
INSERT INTO HasSubject VALUES ('callNumber13', 'Cooking');
INSERT INTO HasSubject VALUES ('callNumber14', 'Travel');


INSERT INTO HasAuthor VALUES ('callNumber1', 'Paul');
INSERT INTO HasAuthor VALUES ('callNumber1', 'Meghan');
INSERT INTO HasAuthor VALUES ('callNumber2', 'John');
INSERT INTO HasAuthor VALUES ('callNumber2', 'Mary');
INSERT INTO HasAuthor VALUES ('callNumber3', 'Paul');
INSERT INTO HasAuthor VALUES ('callNumber4', 'Tom');
INSERT INTO HasAuthor VALUES ('callNumber5', 'Jerry');
INSERT INTO HasAuthor VALUES ('callNumber5', 'Megan');
INSERT INTO HasAuthor VALUES ('callNumber6', 'Peter');
INSERT INTO HasAuthor VALUES ('callNumber8', 'Mike');
INSERT INTO HasAuthor VALUES ('callNumber10', 'Jake');
INSERT INTO HasAuthor VALUES ('callNumber12', 'Rita');

INSERT INTO BookCopy VALUES ('callNumber1', '1', 'in');
INSERT INTO BookCopy VALUES ('callNumber1', '2', 'out');
INSERT INTO BookCopy VALUES ('callNumber1', '3', 'out');
INSERT INTO BookCopy VALUES ('callNumber2', '1', 'in');
INSERT INTO BookCopy VALUES ('callNumber2', '2', 'out');
INSERT INTO BookCopy VALUES ('callNumber3', '1', 'in');
INSERT INTO BookCopy VALUES ('callNumber4', '1', 'in');
INSERT INTO BookCopy VALUES ('callNumber4', '2', 'out');
INSERT INTO BookCopy VALUES ('callNumber5', '1', 'out');
INSERT INTO BookCopy VALUES ('callNumber6', '1', 'in');
INSERT INTO BookCopy VALUES ('callNumber7', '1', 'out');
INSERT INTO BookCopy VALUES ('callNumber8', '1', 'in');
INSERT INTO BookCopy VALUES ('callNumber8', '2', 'in');
INSERT INTO BookCopy VALUES ('callNumber8', '3', 'out');
INSERT INTO BookCopy VALUES ('callNumber9', '1', 'out');
INSERT INTO BookCopy VALUES ('callNumber9', '2', 'in');
INSERT INTO BookCopy VALUES ('callNumber10', '1', 'out');
INSERT INTO BookCopy VALUES ('callNumber11', '1', 'in');
INSERT INTO BookCopy VALUES ('callNumber11', '2', 'in');
INSERT INTO BookCopy VALUES ('callNumber11', '3', 'out');
INSERT INTO BookCopy VALUES ('callNumber11', '4', 'out');
INSERT INTO BookCopy VALUES ('callNumber12', '1', 'in');
INSERT INTO BookCopy VALUES ('callNumber12', '2', 'out');
INSERT INTO BookCopy VALUES ('callNumber12', '3', 'in');
INSERT INTO BookCopy VALUES ('callNumber13', '1', 'in');
INSERT INTO BookCopy VALUES ('callNumber13', '2', 'out');
INSERT INTO BookCopy VALUES ('callNumber13', '3', 'out');
INSERT INTO BookCopy VALUES ('callNumber14', '1', 'in');
INSERT INTO BookCopy VALUES ('callNumber14', '2', 'out');
INSERT INTO BookCopy VALUES ('callNumber14', '3', 'in');

INSERT INTO HoldRequest VALUES ('a', 'a', 'callNumber3', '20/11/2012');
INSERT INTO HoldRequest VALUES ('b', 'b', 'callNumber6', '21/11/2012');
INSERT INTO HoldRequest VALUES ('c', 'd', 'callNumber7', '24/11/2012');
INSERT INTO HoldRequest VALUES ('d', 'e', 'callNumber1', '22/11/2012');
INSERT INTO HoldRequest VALUES ('e', 'g', 'callNumber10', '23/11/2012');
INSERT INTO HoldRequest VALUES ('f', 'c', 'callNumber14', '21/11/2012');
INSERT INTO HoldRequest VALUES ('d', 'a', 'callNumber9', '20/11/2012');
INSERT INTO HoldRequest VALUES ('e', 'f', 'callNumber12', '22/11/2012');
INSERT INTO HoldRequest VALUES ('f', 'h', 'callNumber11', '22/11/2012');

INSERT INTO Borrowing VALUES ('a', 'b', 'callNumber1', '3', '24/10/2012', '24/11/2012');
INSERT INTO Borrowing VALUES ('b', 'a', 'callNumber2', '2', '14/05/2012', '14/06/2012');
INSERT INTO Borrowing VALUES ('c', 'c', 'callNumber3', '1', '06/06/2012', '06/07/2012');
INSERT INTO Borrowing VALUES ('d', 'h', 'callNumber6', '1', '04/11/2012', '04/12/2012');
INSERT INTO Borrowing VALUES ('e', 'f', 'callNumber9', '2', '19/08/2012', '19/09/2012');
INSERT INTO Borrowing VALUES ('f', 'e', 'callNumber11', '1', '12/09/2012', '12/10/2012');
INSERT INTO Borrowing VALUES ('g', 'd', 'callNumber12', '1', '10/10/2012', '10/11/2012');
INSERT INTO Borrowing VALUES ('h', 'c', 'callNumber7', '2', '17/07/2012', '17/08/2012');

INSERT INTO Fine VALUES ('a', 12.10, '14/6/2012', NULL, 'c');
INSERT INTO Fine VALUES ('b', 16.10, '16/8/2012', NULL, 'b');
INSERT INTO Fine VALUES ('c', 22.10, '21/10/2012', NULL, 'a');


