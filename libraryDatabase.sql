CREATE TABLE Borrower
	(bid char(11) not null,
	password varchar(40) not null,
	name varchar(40) not null,
	address varchar(50) null,
	phone char(12) null,
	emailAddress varchar(40) null,
	sinOrStNo char(8) not null,
	expiryDate char(10) null,
	type varchar(10) not null,
	CHECK( type IN (‘student’, ‘faculty’, ‘librarian’)),
	PRIMARY KEY (bid),
	FOREIGN KEY (type) references BorrowerType);

CREATE TABLE BorrowerType
	(type varchar(10) not null,
	bookTimeLimit int not null
	CHECK( type IN (‘student’, ‘faculty’, ‘librarian’)),
	PRIMARY KEY (type));

CREATE TABLE Book
	(callNumber varchar(40) not null,
	isbn char(13) not null,
	title varchar(255) not null,
	mainAuthor varChar(255) not null,
	publisher varChar(255) not null,
	year char(4) not null,
	PRIMARY KEY (callNumber));

CREATE TABLE HasAuthor
	(callNumber varchar(40) not null,
	name varchar(255) not null,
	PRIMARY KEY (callNumber, name),
	FOREIGN KEY (callNumber) REFERENCES Book);

CREATE TABLE HasSubject
	(callNumber varchar(40) not null,
	subject varchar(255) not null,
	PRIMARY KEY (callNumber, subject),
	FOREIGN KEY (callNumber) REFERENCES Book);

CREATE TABLE BookCopy
	(callNumber varchar(40) not null,
	copyNo varchar(40) not null,
	status varchar(40) not null,
	CHECK( status IN (on-hold, ‘in’, ‘out’)),
	PRIMARY KEY (callNumber, copyNo),
	FOREIGN KEY (callNumber) REFERENCES Book);

CREATE TABLE HoldRequest
	(hid varchar(40) not null,
	bid varchar(40) not null,
callNumber varchar(40) not null,
issuedDate char(10) not null,
	PRIMARY KEY (hid),
	FOREIGN KEY (bid) REFERENCES Borrower,
	FOREIGN KEY (callNumber) REFERENCES Book);

CREATE TABLE Borrowing
	(borid varchar(40) not null,
	bid varchar(40) not null,
callNumber varchar(40) not null,
copyNo varchar(40) not null,
outDate varchar(10) not null,
inDate varchar(10) not null,
	PRIMARY KEY (borid),
	FOREIGN KEY (bid) REFERENCES Borrower,
	FOREIGN KEY (callNumber) REFERENCES Book,
	FOREIGN KEY (copyNo) REFERENCES BookCopy);

CREATE TABLE Fine
	(fid varchar(40) not null,
	amount float not null,
issuedDate char(10) not null,
paidDate char(10) not null,
borid varchar(40) not null,
	PRIMARY KEY (fid),
	FOREIGN KEY (borid) REFERENCES Borrowing);
CREATE TABLE BookCopy
	(callNumber varchar(40) not null,
	copyNo varchar(40) not null,
	status varchar(40) not null,
	CHECK( status IN (on-hold, ‘in’, ‘out’)),
	PRIMARY KEY (callNumber, copyNo),
	FOREIGN KEY (callNumber) REFERENCES Book);

CREATE TABLE HoldRequest
	(hid varchar(40) not null,
	bid varchar(40) not null,
callNumber varchar(40) not null,
issuedDate char(10) not null,
	PRIMARY KEY (hid),
	FOREIGN KEY (bid) REFERENCES Borrower,
	FOREIGN KEY (callNumber) REFERENCES Book);

CREATE TABLE Borrowing
	(borid varchar(40) not null,
	bid varchar(40) not null,
callNumber varchar(40) not null,
copyNo varchar(40) not null,
outDate varchar(10) not null,
inDate varchar(10) not null,
	PRIMARY KEY (borid),
	FOREIGN KEY (bid) REFERENCES Borrower,
	FOREIGN KEY (callNumber) REFERENCES Book,
	FOREIGN KEY (copyNo) REFERENCES BookCopy);

CREATE TABLE Fine
	(fid varchar(40) not null,
	amount float not null,
issuedDate char(10) not null,
paidDate char(10) not null,
borid varchar(40) not null,
	PRIMARY KEY (fid),
	FOREIGN KEY (borid) REFERENCES Borrowing);
CREATE TABLE BookCopy
	(callNumber varchar(40) not null,
	copyNo varchar(40) not null,
	status varchar(40) not null,
	CHECK( status IN (on-hold, ‘in’, ‘out’)),
	PRIMARY KEY (callNumber, copyNo),
	FOREIGN KEY (callNumber) REFERENCES Book);

CREATE TABLE HoldRequest
	(hid varchar(40) not null,
	bid varchar(40) not null,
callNumber varchar(40) not null,
issuedDate char(10) not null,
	PRIMARY KEY (hid),
	FOREIGN KEY (bid) REFERENCES Borrower,
	FOREIGN KEY (callNumber) REFERENCES Book);

CREATE TABLE Borrowing
	(borid varchar(40) not null,
	bid varchar(40) not null,
callNumber varchar(40) not null,
copyNo varchar(40) not null,
outDate varchar(10) not null,
inDate varchar(10) not null,
	PRIMARY KEY (borid),
	FOREIGN KEY (bid) REFERENCES Borrower,
	FOREIGN KEY (callNumber) REFERENCES Book,
	FOREIGN KEY (copyNo) REFERENCES BookCopy);

CREATE TABLE Fine
	(fid varchar(40) not null,
	amount float not null,
issuedDate char(10) not null,
paidDate char(10) not null,
borid varchar(40) not null,
	PRIMARY KEY (fid),
	FOREIGN KEY (borid) REFERENCES Borrowing);