create table if not EXISTS INPUTDOCUMENT (
 PK serial,
 ID varchar(512) not null ,
 TEXT text default 'unknown',
 PUBLISHDAY integer default 19700101,
 PUBLISHDATE bigint default 0,
 SOURCETYPE varchar(256) DEFAULT 'unknown',
 TITLE text default 'unknown',
 SOURCENAME varchar(256) default 'unknown',
 AUTHOR varchar(256) default 'unknown',
 SOURCEDOMAIN varchar(256) default 'unknown',
 SOURCETOPIC varchar(256) default 'unknown',
 LINK text default 'unknown',
 DOMAIN varchar(256) default 'unknown',
 TOPIC varchar(256) default 'unknown'
)