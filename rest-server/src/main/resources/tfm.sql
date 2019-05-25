

use tfm;

drop table if exists tfm.factura;
drop table if exists tfm.facturas;
drop table if exists tfm.invoice;
drop table if exists tfm.invoice_data;
drop table if exists tfm.kkeys;
drop table if exists tfm.back_up;
drop table if exists tfm.backUp;
drop table if exists tfm.users;

drop table if exists tfm.tbl_factura;
drop table if exists tfm.tbl_facturas;
drop table if exists tfm.tbl_invoice;
drop table if exists tfm.tbl_invoice_data;
drop table if exists tfm.tbl_kkeys;
drop table if exists tfm.tbl_back_up;
drop table if exists tfm.tbl_backUp;
drop table if exists tfm.tbl_users;


drop table if exists tfm.tbl_token;
drop table if exists tfm.tbl_usuari_role;
drop table if exists tfm.tbl_role;
drop table if exists tfm.tbl_usuari;






CREATE TABLE tfm.tbl_factura (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  data_creacio datetime NOT NULL,
  detall_factura longblob,
  num_factura VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



-- 2019-04-07

CREATE TABLE tfm.tbl_users (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  
  user VARCHAR(25) NOT NULL,
  pass VARCHAR(100) NOT NULL,
  
  creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE (user)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;






CREATE TABLE tfm.tbl_usuari (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  
  username VARCHAR(45) NOT NULL,
  pass VARCHAR(255) NOT NULL,
  certificate LONGTEXT,
  email VARCHAR(255) NOT NULL,
  enabled tinyint(1) NOT NULL DEFAULT '0',
  comentaris LONGTEXT,
  
  creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  
  PRIMARY KEY (id),
  UNIQUE KEY username_UNIQUE (username),
  UNIQUE KEY email_UNIQUE (email)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



CREATE TABLE tfm.tbl_role (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  
  nom VARCHAR(255) NOT NULL,
  comentaris LONGTEXT,
  
  creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY nom_UNIQUE (nom)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



CREATE TABLE tfm.tbl_usuari_role (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  
  usuari_id bigint(20) NOT NULL,
  role_id bigint(20) NOT NULL,
  
  creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY usuari_role (usuari_id,role_id),
  KEY tfm_usuari_role_ibfk_2 (role_id),
  CONSTRAINT tfm_usuari_role_ibfk_1 FOREIGN KEY (usuari_id) REFERENCES tfm.tbl_usuari (id) ON DELETE CASCADE,
  CONSTRAINT tfm_usuari_role_ibfk_2 FOREIGN KEY (role_id) REFERENCES tfm.tbl_role (id) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



CREATE TABLE tfm.tbl_token (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  
  usuari_id bigint(20) NOT NULL,
  token longtext NOT NULL,
  
  resum VARCHAR(32) not null,
  
  valid_from DATETIME not null,
  valid_to datetime not null,
  
  used tinyint(1) NOT NULL DEFAULT '0',
  
  creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  unique u_resum (resum),
  CONSTRAINT tfm_token_fk_1 FOREIGN KEY (usuari_id) REFERENCES tfm.tbl_usuari (id) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



CREATE TABLE tfm.tbl_kkeys (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  
  usuari_id bigint(20) NOT NULL,
  
  f VARCHAR(100) NOT NULL comment 'field',
  k longtext     NOT NULL comment 'Encrypted Symmetric Key',
  
  creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE u_f (f),
  CONSTRAINT tfm_kkeys_fk_1 FOREIGN KEY (usuari_id) REFERENCES tfm.tbl_usuari (id) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



CREATE TABLE tfm.tbl_invoice_data (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  
  usuari_id bigint(20) NOT NULL,
  
  f1 VARCHAR(100) NOT NULL comment 'UID',
  f2 VARCHAR(255) NOT NULL comment 'Tax Identification Number',
  f3 VARCHAR(255) NOT NULL comment 'Corporate Name',
  f4 VARCHAR(255) NOT NULL comment 'Invoice Number',
  f5 DOUBLE(15,4) default 0.0 comment 'Invoice Total',
  f6 DOUBLE(15,4) default 0.0 comment 'Total Gross Amount',
  f7 DOUBLE(15,4) default 0.0 comment 'Total Tax Outputs',
  f8 VARCHAR(255) NOT NULL comment 'Issue Date',
  
  creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE u_f1 (f1),
  CONSTRAINT tfm_invoice_data_fk_1 FOREIGN KEY (usuari_id) REFERENCES tfm.tbl_usuari (id) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



CREATE TABLE tfm.tbl_back_up (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  
  usuari_id bigint(20) NOT NULL,
  
  f1 VARCHAR(100) NOT NULL comment 'UID',
  i longtext NOT NULL comment 'Initialization Vector',
  k longtext NOT NULL comment 'Simmetric Key',
  f longtext NOT NULL comment 'Encrypted Invoice File',
  
  creation_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE u_f1 (f1),
  CONSTRAINT tfm_back_up_fk_1 FOREIGN KEY (usuari_id) REFERENCES tfm.tbl_usuari (id) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



CREATE TABLE tfm.tbl_invoice (
	id bigint(20) NOT NULL AUTO_INCREMENT,

	usuari_id bigint(20) NOT NULL,

	uid VARCHAR(100) NOT NULL,
	tax_identification_number VARCHAR(255) NOT NULL,
	corporate_name VARCHAR(255) NOT NULL,
	invoice_number VARCHAR(255) NOT NULL,
	invoice_total DOUBLE(15,4) default 0.0,
	total_Tax_outputs DOUBLE(15,4) default 0.0,
	issue_date VARCHAR(255) NOT NULL,
	signed_invoice LONGTEXT NOT NULL,

	iv LONGTEXT NOT NULL,
	sim_key LONGTEXT NOT NULL,

	data_creacio TIMESTAMP NOT NULL default CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	CONSTRAINT tfm_invoice_fk_1 FOREIGN KEY (usuari_id) REFERENCES tfm.tbl_usuari (id) ON DELETE CASCADE
	) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;



INSERT INTO tfm.tbl_role
(
nom,
comentaris)
VALUES
(
'ROLE_USER',
''),
(
'ROLE_ADMIN',
''),
(
'ROLE_DBA',
''),
(
'ROLE_CONSULTA',
'');



-- Change double fields to string
drop table if exists tfm.tbl_invoice_data;
CREATE TABLE tfm.tbl_invoice_data (
  id 							bigint(20) NOT NULL AUTO_INCREMENT,
  
  usuari_id 					bigint(20) NOT NULL,
  
  f1 							VARCHAR(100) NOT NULL comment 'UID',
  f2 							VARCHAR(255) NOT NULL comment 'Tax Identification Number',
  f3 							VARCHAR(255) NOT NULL comment 'Corporate Name',
  f4 							VARCHAR(255) NOT NULL comment 'Invoice Number',
  f5 							VARCHAR(255) NOT NULL comment 'Invoice Total',
  f6 							VARCHAR(255) NOT NULL comment 'Total Gross Amount',
  f7 							VARCHAR(255) NOT NULL comment 'Total Tax Outputs',
  f8 							VARCHAR(255) NOT NULL comment 'Issue Date',
  
  creation_time 				TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE u_f1 (f1),
  CONSTRAINT tfm_invoice_data_fk_1 FOREIGN KEY (usuari_id) REFERENCES tfm.tbl_usuari (id) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

drop table if exists tfm.tbl_invoice;
CREATE TABLE tfm.tbl_invoice (
	id 							bigint(20) NOT NULL AUTO_INCREMENT,

	usuari_id 					bigint(20) NOT NULL,

	uid 						VARCHAR(100) NOT NULL,
	tax_identification_number 	VARCHAR(255) NOT NULL,
	corporate_name 				VARCHAR(255) NOT NULL,
	invoice_number 				VARCHAR(255) NOT NULL,
	invoice_total 				VARCHAR(255) NOT NULL,
	total_Tax_outputs 			VARCHAR(255) NOT NULL,
	issue_date 					VARCHAR(255) NOT NULL,
	signed_invoice 				LONGTEXT NOT NULL,

	iv 							LONGTEXT NOT NULL,
	sim_key 					LONGTEXT NOT NULL,

	data_creacio 				TIMESTAMP NOT NULL default CURRENT_TIMESTAMP,
	PRIMARY KEY (id),
	CONSTRAINT tfm_invoice_fk_1 FOREIGN KEY (usuari_id) REFERENCES tfm.tbl_usuari (id) ON DELETE CASCADE
	) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS tfm.tbl_keystore;
CREATE TABLE tfm.tbl_keystore (
  id 							bigint(20) NOT NULL AUTO_INCREMENT,
  
  usuari_id 					bigint(20) NOT NULL,
  keystore 						LONGTEXT NOT NULL,
  
  creation_time 				TIMESTAMP DEFAULT '0000-00-00 00:00:00',
  update_time 					TIMESTAMP default now() on update now(),
  PRIMARY KEY (id),
  unique u_id (id),
  CONSTRAINT tfm_keystore_fk_1 FOREIGN KEY (usuari_id) REFERENCES tfm.tbl_usuari (id) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


SELECT * FROM tfm.tbl_usuari;
SELECT * FROM tfm.tbl_role;
SELECT * FROM tfm.tbl_usuari_role;
SELECT * FROM tfm.tbl_token;
SELECT * FROM tfm.tbl_keystore;

select * from tfm.tbl_factura;
select * from tfm.tbl_invoice;
select * from tfm.tbl_invoice_data;
select * from tfm.tbl_kkeys;
select * from tfm.tbl_back_up;

select * 
FROM tfm.tbl_usuari u
left join tfm.tbl_usuari_role ur on 1 = 1
	and u.id = ur.usuari_id
left join tfm.tbl_role r on 1 = 1
	and r.id = ur.role_id
;

select k.k, right(k.k, 25), count(*) 
from tfm.tbl_kkeys k
group by k.k;

