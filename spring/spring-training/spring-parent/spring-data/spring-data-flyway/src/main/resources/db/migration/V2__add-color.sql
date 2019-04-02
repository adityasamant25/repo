ALTER TABLE car ADD COLUMN (colour varchar(200));

UPDATE car SET COLOUR = 'Red' WHERE colour is null;
