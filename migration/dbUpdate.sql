/*update existing tables*/
ALTER TABLE user_login ADD COLUMN (second_fa TINYINT(1));
ALTER TABLE user_login ADD COLUMN (email_confirmation BIGINT);
ALTER TABLE user_login ADD COLUMN (email_conf_token varchar(255));
ALTER TABLE user_login ADD COLUMN (email_conf_code varchar(255));
ALTER TABLE user_login ADD COLUMN (renew_pw_req TINYINT(1));
ALTER TABLE user_login ADD CONSTRAINT uk_email_conf_token UNIQUE (email_conf_token);

/*update the new columns with a value*/
UPDATE user_login t SET t.second_fa=0 WHERE t.second_fa IS NULL;
UPDATE user_login t SET t.renew_pw_req=0 WHERE t.renew_pw_req IS NULL;
UPDATE user_login t SET t.email_confirmation=0 WHERE t.email_confirmation IS NULL;

/*update user table*/
ALTER TABLE user ADD COLUMN (creation BIGINT);
ALTER TABLE user MODIFY firstActive BIGINT;
ALTER TABLE user MODIFY lastActive BIGINT;
UPDATE user SET creation=0;

/*create table policy_consent*/
CREATE TABLE policy_consent (tempBarnacleGenerator varchar(1));
ALTER TABLE policy_consent ADD COLUMN (user_id VARCHAR(255));
ALTER TABLE policy_consent ADD COLUMN (version VARCHAR(255));
ALTER TABLE policy_consent ADD COLUMN (consent BIGINT);
ALTER TABLE policy_consent DROP COLUMN tempBarnacleGenerator;
ALTER TABLE policy_consent ADD PRIMARY KEY (user_id, version);
ALTER TABLE policy_consent CHARACTER SET UTF8;
ALTER TABLE policy_consent ADD CONSTRAINT fk_policy_consent_user_id FOREIGN KEY (user_id) REFERENCES user (id);
