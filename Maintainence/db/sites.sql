CREATE TABLE sites (
    site_id INT PRIMARY KEY,
    site_type VARCHAR(30),      -- Villa / Apartment / Independent / Open
    length INT,
    width INT,
    occupied BOOLEAN,
    cost_per_sqft INT
);

CREATE TABLE owners (
    owner_id INT PRIMARY KEY,
    name VARCHAR(50),
    site_id INT REFERENCES sites(site_id)
);

CREATE TABLE update_requests (
    request_id SERIAL PRIMARY KEY,
    site_id INT,
    new_site_type VARCHAR(30),
    new_occupied BOOLEAN,
    status VARCHAR(20) DEFAULT 'PENDING'
);

INSERT INTO sites VALUES
(1,'Villa',40,60,true,9),
(2,'Apartment',40,60,false,6),
(3,'Independent',40,60,true,9),
(4,'Open Site',40,60,false,6),
(5,'Villa',40,60,true,9),
(6,'Apartment',40,60,false,6),
(7,'Independent',40,60,true,9),
(8,'Open Site',40,60,false,6),
(9,'Villa',40,60,true,9),
(10,'Apartment',40,60,false,6),
(11,'Villa',30,50,true,9),
(12,'Apartment',30,50,false,6),
(13,'Independent',30,50,true,9),
(14,'Open Site',30,50,false,6),
(15,'Villa',30,50,true,9),
(16,'Apartment',30,50,false,6),
(17,'Independent',30,50,true,9),
(18,'Open Site',30,50,false,6),
(19,'Villa',30,50,true,9),
(20,'Apartment',30,50,false,6),
(21,'Villa',30,40,true,9),
(22,'Apartment',30,40,false,6),
(23,'Independent',30,40,true,9),
(24,'Open Site',30,40,false,6),
(25,'Villa',30,40,true,9),
(26,'Apartment',30,40,false,6),
(27,'Independent',30,40,true,9),
(28,'Open Site',30,40,false,6),
(29,'Villa',30,40,true,9),
(30,'Apartment',30,40,false,6),
(31,'Independent',30,40,true,9),
(32,'Open Site',30,40,false,6),
(33,'Villa',30,40,true,9),
(34,'Apartment',30,40,false,6),
(35,'Independent',30,40,true,9);

INSERT INTO owners VALUES
(101,'Ravi Kumar',1),
(102,'Sneha R',2),
(103,'Arjun S',3),
(104,'Meera P',4),
(105,'Vikram N',5),
(106,'Ananya M',6),
(107,'Rahul D',7),
(108,'Kavya T',8),
(109,'Suresh K',9),
(110,'Divya S',10),

(111,'Amit P',11),
(112,'Neha R',12),
(113,'Kiran S',13),
(114,'Pooja M',14),
(115,'Rohit K',15),
(116,'Swathi N',16),
(117,'Nikhil R',17),
(118,'Lavanya P',18),
(119,'Manoj S',19),
(120,'Priya D',20);

INSERT INTO update_requests(site_id,new_site_type,new_occupied,status) VALUES
(2,'Apartment',true,'PENDING'),
(4,'Villa',true,'PENDING'),
(12,'Independent',true,'PENDING'),
(18,'Apartment',false,'PENDING'),
(25,'Open Site',false,'PENDING');

select * from update_requests;
