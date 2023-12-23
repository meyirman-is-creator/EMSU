create table students(
	first_name text,
	last_name text,
	password text,
	id int primary key,
	gender text,
	age int
);
create table teachers (
	id int primary key,
	first_name text,
	last_name text,
	password text,
	age int,
	gender text
);
create table courses(
	course_id int primary key,
	course_name text,
	teacher_id int references teachers(id)
);
create table enrollments (
	course_id int references courses(course_id),
	student_id int references students(id)
);

create table events(
	event_id int primary key,
	description text,
	event_name text,
	event_date date,
	placement text,
	course_id int references courses(course_id),
	teacher_id int references teachers(id),
	время text
);
create table calendars(
	student_id int references students(id),
	course_id int references courses(course_id),
	teacher_id int references teachers(id),
	event_id int references events(event_id)
);


insert into students
values
('Meyirman', 'Sarsenbay','041121501376',2201, 'male',19),
('Nurbol', 'Zhomart','nurbol202',2202, 'male',18),
('Aida', 'Kapar','AidaHanym',2203, 'female',18),
('Ospan', 'Akim','ospan01',2204, 'male',18),
('Duman', 'Rakhmatulla','duka05',2205, 'male',18),
('Salamat', 'Daribaev','sala0123',2206, 'male',18);

insert into teachers
values
(901, 'Azamat','Serek','aza0909',25,'male'),
(902, 'Magzhan','Zhailau','maga',26,'male'),
(903, 'Aierke','Myrzabayeva','aika',23,'female'),
(904, 'Aigerim','Raisovo','aigera',28,'female'),
(905, 'Arailym','Serikbay','araika',26,'female'),
(906, 'Meirbek','Slymkhan','meir',29,'male');
insert into courses
values
(220, 'Russian',null),
(221,'Fizra',null)
(201, 'Database', 901),
(202, 'DataAnalysis', 901),
(203, 'Math', 906),
(204, 'ICT', 903),
(205, 'Turkish', 904),
(206, 'English', 904),
(207, 'Algos', 902),
(208, 'Machine Learning', 905),
(209, 'AI', 905),
(210, 'Economy', null),
(211, 'History', null),
(212, 'Kazak tili', null);
insert into enrollments
values
(201, 2201),
(201, 2202),
(202, 2203),
(205, 2201),
(205, 2202),
(206, 2202),
(205, 2201),
(205, 2202),
(206, 2202),
(207, 2204),
(209, 2205),
(208, 2206),
(204,null);
select * from events
truncate table events
insert into events
values
(31,'Quiz','Final Interview', '2024-02-29','A1',201,901,'07:00:00')
(1,'Project Interview for dbms','Final Interview', '2023-12-19','A1',201,901,'07:00:00'),
(2,'Final exam on Moodle','Final exam', '2023-12-21','VR',202,902,'08:00:00'),
(3,'Final exam paper','Final exam', '2023-12-28','B1',205,904,'11:30:00'),
(4,'Final exam paper','Final exam', '2023-12-20','C1',206,904,'09:00:00'),
(5,'Project for dbms','Final project', '2023-12-04','Google driver',201,901,'11:00:00'),
(6,'Project for Data Analysis','Final project', '2023-12-11','Google driver',202,901,'10:00:00'),
(7,'Project for Machine Learning','Final project', '2023-12-31','Google driver',208,905,'18:00:00'),
(8, 'Project purpose for DataAnalysis', 'Project Purpose','2023-12-11','Google driver',208,905,'11:00:00'),
(9, 'Math finale','Final exam','2023-12-29','New Cumpus', 203,906,'15:00:00'),
(10,'Midterm on AI','midterm', '2024-08-04','C1',209,905,'06:00:00'),
(15,'Project Interview for dbms','Final Interview', '2024-01-19','A1',201,901,'06:00:00'),
(25,'Project Interview for dbms1 Nurbol','Final Interview', '2023-08-10','A1',201,901,'06:00:00');
select e.event
select * from events
insert into calendarsp
values
(2201,201,901,31)
(2201, 201, 901,1),
(2201, 201, 901,5),
(2202, 201, 901,1),
(2202, 201, 901,5),
(2202, 206, 904,4),
(2203, 202, 902,2),
(2206, 208, 905, 7);

---1
select * from teachers
select c.course_id, c.course_name
from courses c where c.teacher_id is null;

--2
select c.course_id, c.course_name
from courses c where c.teacher_id is not null;
select * from events
--3
select s.first_name, s.last_name, e1.event_id, e1.event_name, e1.event_date, e1.description, e1.placement, e1.время,c.course_id,c.teacher_id from students s join enrollments e on e.student_id = s.id join courses c using(course_id) join events e1 using(course_id) where s.first_name = 'Aida'
--4
select distinct s.first_name, s.last_name from students s
join enrollments e on e.student_id = s.id
join courses c using(course_id)
join teachers t on t.id = c.teacher_id
where t.first_name = 'Azamat'
select s.first_name, s.last_name, c.course_name, t.teacher_name
from students s join enrollments enrol using(student_id)
join courses c using(course_id)
join teachers t using(teacher_id)
group by s.first_name, s.last_name, c.course_name,t.teacher_name
having s.first_name = (
	select s1.first_name from students s1
	join enrollments enrol using(student_id)
	join courses c using(course_id)
	join teachers t using(teacher_id)
	where t.teacher_name = 'Azamat'
)
select * from events
drop table calendars
select e.event_id,e.description,e.event_name, e.event_date , placement, course_id, teacher_id, время from events e join teachers t on e.teacher_id = t.id where t.first_name = 'Bek'
select e.event_id,e.description,e.event_name, e.event_date , placement, course_id, teacher_id, время from events e join teachers t on e.teacher_id = t.id where t.first_name = 'Bazatbek'
select * from teachers
GRANT ALL PRIVILEGES ON table students TO designpatterns;
select s.first_name,s.last_name, c.course_name from students s
join enrollments using(student_id)
join courses c using(course_id)
where s.first_name = 'Meyirman'
select c.course_name,c.course_id,t.first_name, t.last_name from courses c left join teachers t on c.teacher_id = t.id where c.teacher_id is null
select distinct c.course_name, c.course_id, t.first_name, t.last_name from courses c left join teachers t on t.id = c.teacher_id where t.id is not null
select e.event_id,e.description,e.event_name, e.event_date , placement, course_id, teacher_id, время from events e join teachers t on e.teacher_id = t.id where t.first_name = 'Arailym'
select * from teachers
select t.first_name, t.last_name, e.event_id, e.description, e.event_name, e.event_date, e.placement, e.course_id, e.teacher_id, e.время from teachers t join calendars c on t.id = c.teacher_id join events e using(event_id) where t.first_name = 'Magzhan'
select t.first_name , t.last_name, c.course_id, c.course_name from teachers t join courses c on t.id = c.teacher_id where t.first_name = 'Arailym'
select * from enrollments
alter table events add column время time
update events set время = '15:00:00' where event_id=7
delete from enrollments where student_id = 2201
select * from students