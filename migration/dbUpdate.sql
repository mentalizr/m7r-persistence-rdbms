/*update existing tables*/
ALTER TABLE user_login ADD second_fa TINYINT(1);
ALTER TABLE user_login ADD email_confirmation BIGINT;
ALTER TABLE user_login ADD email_conf_token varchar(255);
ALTER TABLE user_login ADD email_conf_code varchar(255);
ALTER TABLE user_login ADD renew_pw_req TINYINT(1);
ALTER TABLE user_login ADD CONSTRAINT uk_email_conf_token UNIQUE(email_conf_token);

/*update the new columns with a value*/
UPDATE user_login t SET t.second_fa=0 WHERE t.second_fa IS NULL;
UPDATE user_login t SET t.renew_pw_req=0 WHERE t.renew_pw_req IS NULL;
UPDATE user_login t SET t.email_confirmation=0 WHERE t.email_confirmation IS NULL;

/*create table policy_consent*/
CREATE TABLE policy_consent
(
    user_id  varchar(255),
    version varchar(255) ,
    consent BIGINT       ,
    constraint pk_user_id_version
        primary key (user_id, version),
    constraint fk_policy_consent_user_id
        foreign key (user_id) references user (id)
);
