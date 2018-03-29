SET FILES LOB SCALE 16;
SET DATABASE TRANSACTION CONTROL MVCC;
SET DATABASE DEFAULT TABLE TYPE MEMORY;

create table COMPETENCY (
   ID                   BIGINT not null,
   NAME  	            varchar(255) not null,
   DEFINITION           varchar(2000) not null,
   DESCRIPTION          varchar(2000) not null,
   constraint PK_COMPETENCY primary key (ID)
);

create table DOMAIN (
   ID                   BIGINT not null,
   NAME  	            varchar(255) not null,
   constraint PK_DOMAIN primary key (ID)
);

create table DOMAIN_COMPETENCY (
   DOMAIN_ID                   BIGINT not null,
   COMPETENCY_ID  	           BIGINT not null
);
