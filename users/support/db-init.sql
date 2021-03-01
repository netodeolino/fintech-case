CREATE DATABASE IF NOT EXISTS `users`;

USE `users`;


-- create table consumer (
--         id bigint not null auto_increment,
--          username varchar(75),
--          user_id bigint not null,
--          primary key (id)
--      ) engine=MyISAM
     
--      create table seller (
--         id bigint not null auto_increment,
--          cnpj varchar(14),
--          fantasy_name varchar(255),
--          social_name varchar(255),
--          username varchar(75),
--          user_id bigint not null,
--          primary key (id)
--      ) engine=MyISAM
     
--      create table transaction (
--         id bigint not null auto_increment,
--          transaction_date datetime,
--          value double precision,
--          payee_id bigint not null,
--          payer_id bigint not null,
--          primary key (id)
--      ) engine=MyISAM
     
--      create table user (
--         id bigint not null auto_increment,
--          cpf varchar(11),
--          email varchar(100),
--          full_name varchar(255),
--          password varchar(255),
--          phone_number varchar(255),
--          primary key (id)
--      ) engine=MyISAM
     
--      alter table consumer 
--         drop index UK_6a49w9ehx6rb4n7bjuj9l1l62
     
--      alter table consumer 
--         add constraint UK_6a49w9ehx6rb4n7bjuj9l1l62 unique (user_id)
     
--      alter table consumer 
--         drop index UK_buu0il5v4n94vnko7h169cxvh
     
--      alter table consumer 
--         add constraint UK_buu0il5v4n94vnko7h169cxvh unique (username)
     
--      alter table seller 
--         drop index UK_etfpl3vymasxfqc4ri4r3euf6
     
--      alter table seller 
--         add constraint UK_etfpl3vymasxfqc4ri4r3euf6 unique (user_id)
     
--      alter table seller 
--         drop index UK_3gnjncn8l4no25wl7pyjqrx3p
     
--      alter table seller 
--         add constraint UK_3gnjncn8l4no25wl7pyjqrx3p unique (username)
     
--      alter table user 
--         drop index UK_2qv8vmk5wxu215bevli5derq
     
--      alter table user 
--         add constraint UK_2qv8vmk5wxu215bevli5derq unique (cpf)
     
--      alter table user 
--         drop index UK_ob8kqyqqgmefl0aco34akdtpe
     
--      alter table user 
--         add constraint UK_ob8kqyqqgmefl0aco34akdtpe unique (email)
     
--      alter table consumer 
--         add constraint FK7ss1fhck02ao9art2q85fp91p 
--         foreign key (user_id) 
--         references user (id)
     
--      alter table seller 
--         add constraint FK6rgw0e6tb24n93c27njlv0wcl 
--         foreign key (user_id) 
--         references user (id)
     
--      alter table transaction 
--         add constraint FKcgyse5n2et34vlaafxw9bb4an 
--         foreign key (payee_id) 
--         references user (id)
     
--      alter table transaction 
--         add constraint FKfy3qkr7fidpcwibm1dv48hjhd 
--         foreign key (payer_id) 
--         references user (id)

INSERT IGNORE INTO user (cpf, email, full_name, phone_number, password) VALUES ('06604311357', 'deolino@email.com', 'Deolino', '(88) 999999999', '12345678');

INSERT IGNORE INTO user (cpf, email, full_name, phone_number, password) VALUES ('06611338399', 'fernanda@email.com', 'Fernanda ', '(88) 88888888888', '12345678');

INSERT IGNORE INTO consumer (username, user_id) VALUES ('fernandaf', 2);

INSERT IGNORE INTO seller (cnpj, social_name, fantasy_name, username, user_id) VALUES ('22896431000110', 'Challenge Services S.A', 'Challenge', 'deolinod', 1);