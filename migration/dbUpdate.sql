/*update existing tables*/
ALTER TABLE user_login ADD secondFA boolean;
ALTER TABLE user_login ADD emailConfirmation long;
ALTER TABLE user_login ADD emailConfToken varchar(255);
ALTER TABLE user_login ADD emailConfCode varchar(255);
ALTER TABLE user_login ADD renewPasswordRequired boolean;

/*update the new columns with a value*/
UPDATE mentalizr.user_login t SET t.secondFA=0 WHERE t.secondFA IS NULL;
UPDATE mentalizr.user_login t SET t.renewPasswordRequired=0 WHERE t.renewPasswordRequired IS NULL;
UPDATE mentalizr.user_login t SET t.emailConfirmation=0 WHERE t.emailConfirmation IS NULL;

/*show updated table*/
SELECT * FROM user_login;

/*create table policy_consent*/
CREATE TABLE mentalizr.policy_consent
(
    userid  varchar(255) ,
    version varchar(255) ,
    consent long         , 
    constraint policy_consent_pk
        primary key (userid, version),
    constraint policy_consent_user_null_fk
        foreign key (userid) references mentalizr.user (id)
);

/*show created table*/
SELECT * FROM policy_consent;

