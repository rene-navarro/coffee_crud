

-- Script para crear la base de datos de la distribuidora de Cafes
-- PostgreSQL

DROP TABLE IF EXISTS suppliers, coffees, cof_inventory, merch_inventory, coffee_houses;

CREATE TABLE IF NOT EXISTS suppliers (
  sup_id INTEGER NOT NULL,
  sup_name VARCHAR(40) NOT NULL,
  street VARCHAR(40) NOT NULL,
  city VARCHAR(20) NOT NULL,
  state CHAR(2) NOT NULL,
  zip CHAR(5),
  PRIMARY KEY (sup_id)
);
  
CREATE TABLE IF NOT EXISTS coffees (
  cof_name VARCHAR(32) NOT NULL,
  sup_id INTEGER NOT NULL,
  price NUMERIC(6,2) NOT NULL,
  sales INTEGER NOT NULL DEFAULT 0,
  total INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY (cof_name),
  FOREIGN KEY (sup_id) REFERENCES suppliers (sup_id)
	ON UPDATE CASCADE
);
  
 
CREATE TABLE IF NOT EXISTS cof_inventory (
  warehouse_id INTEGER NOT NULL,
  cof_name VARCHAR(32) NOT NULL,
  sup_id INTEGER NOT NULL,
  quan INTEGER NOT NULL DEFAULT 0,
  date_val timestamp,
  FOREIGN KEY (cof_name) REFERENCES coffees (cof_name)
	ON UPDATE CASCADE,
  FOREIGN KEY (sup_id) REFERENCES suppliers (sup_id)
	ON UPDATE CASCADE
);
  
CREATE TABLE  IF NOT EXISTS merch_inventory (
  item_id INTEGER NOT NULL,
  item_name VARCHAR(20),
  sup_id INTEGER,
  quan INTEGER DEFAULT 0,
  date_val timestamp,
  PRIMARY KEY (item_id),
  FOREIGN KEY (sup_id) REFERENCES suppliers (sup_id)
	ON UPDATE CASCADE
    ON DELETE CASCADE
    
);
  
CREATE TABLE  IF NOT EXISTS coffee_houses (
  store_id INTEGER NOT NULL,
  city VARCHAR(32),
  coffee INTEGER NOT NULL DEFAULT 0,
  merch INTEGER NOT NULL DEFAULT 0,
  total INTEGER NOT NULL DEFAULT 0,
  PRIMARY KEY (store_id)
);

insert into suppliers values(49,  'Superior Coffee', '1 Party Place', 'Mendocino', 'CA', '95460');
insert into suppliers values(101, 'Acme, Inc.', '99 Market Street', 'Groundsville', 'CA', '95199');
insert into suppliers values(150, 'The High Ground', '100 Coffee Lane', 'Meadows', 'CA', '93966');
insert into suppliers values(456, 'Restaurant Supplies, Inc.', '200 Magnolia Street', 'Meadows', 'CA', '93966');
insert into suppliers values(927, 'Professional Kitchen', '300 Daisy Avenue', 'Groundsville', 'CA', '95199');

insert into coffees values('Colombian',          101, 7.99, 0, 0);
insert into coffees values('French_Roast',       49,  8.99, 0, 0);
insert into coffees values('Espresso',           150, 9.99, 0, 0);
insert into coffees values('Colombian_Decaf',    101, 8.99, 0, 0);
insert into coffees values('French_Roast_Decaf', 049, 9.99, 0, 0);

insert into cof_inventory values(1234, 'Colombian',       101, 0, '2006-04-01');
insert into cof_inventory values(1234, 'French_Roast',    49,  0, '2006-04-01');
insert into cof_inventory values(1234, 'Espresso',        150, 0, '2006-04-01');
insert into cof_inventory values(1234, 'Colombian_Decaf', 101, 0, '2006-04-01');

insert into merch_inventory values(00001234, 'Cup_Large', 456, 28, '2006-04-01');
insert into merch_inventory values(00001235, 'Cup_Small', 456, 36, '2006-04-01');
insert into merch_inventory values(00001236, 'Saucer', 456, 64, '2006-04-01');
insert into merch_inventory values(00001287, 'Carafe', 456, 12, '2006-04-01');
insert into merch_inventory values(00006931, 'Carafe', 927, 3, '2006-04-01');
insert into merch_inventory values(00006935, 'PotHolder', 927, 88, '2006-04-01');
insert into merch_inventory values(00006977, 'Napkin', 927, 108, '2006-04-01');
insert into merch_inventory values(00006979, 'Towel', 927, 24, '2006-04-01');
insert into merch_inventory values(00004488, 'CofMaker', 456, 5, '2006-04-01');
insert into merch_inventory values(00004490, 'CofGrinder', 456, 9, '2006-04-01');
insert into merch_inventory values(00004495, 'EspMaker', 456, 4, '2006-04-01');
insert into merch_inventory values(00006914, 'Cookbook', 927, 12, '2006-04-01');

insert into coffee_houses values(10023, 'Mendocino', 3450, 2005, 5455);
insert into coffee_houses values(33002, 'Seattle', 4699, 3109, 7808);
insert into coffee_houses values(10040, 'SF', 5386, 2841, 8227);
insert into coffee_houses values(32001, 'Portland', 3147, 3579, 6726);
insert into coffee_houses values(10042, 'SF', 2863, 1874, 4710);
insert into coffee_houses values(10024, 'Sacramento', 1987, 2341, 4328);
insert into coffee_houses values(10039, 'Carmel', 2691, 1121, 3812);
insert into coffee_houses values(10041, 'LA', 1533, 1007, 2540);
insert into coffee_houses values(33005, 'Olympia', 2733, 1550, 4283);
insert into coffee_houses values(33010, 'Seattle', 3210, 2177, 5387);
insert into coffee_houses values(10035, 'SF', 1922, 1056, 2978);
insert into coffee_houses values(10037, 'LA', 2143, 1876, 4019);
insert into coffee_houses values(10034, 'San_Jose', 1234, 1032, 2266);
insert into coffee_houses values(32004, 'Eugene', 1356, 1112, 2468);

  
