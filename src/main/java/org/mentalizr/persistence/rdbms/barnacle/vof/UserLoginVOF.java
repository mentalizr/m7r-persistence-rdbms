package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.*;

import java.util.Date;

@Barnacle
@TableName("user_login")
public class UserLoginVOF {

    @Barnacle
    @ColumnName("user_id")
    @PrimaryKey
    @ForeignKey(
            foreignKeyName = "fk_user_login_user_id",
            referenceTableName = "user",
            referenceColumnName = "id",
            getEntityMethod = true,
            entityMethodName = "userVO",
            getReferenceEntityMethod = true,
            referenceEntityMethodName = "userLoginVO"
    )
    protected String userId;

    @Barnacle
    @Unique
    protected String username;

    @Barnacle
    @ColumnName("password_hash")
    protected String passwordHash;

    @Barnacle
    // @NotNull
    protected String email;

    @Barnacle
    @ColumnName("first_name")
    protected String firstName;

    @Barnacle
    @ColumnName("last_name")
    protected String lastName;

    @Barnacle
    protected int gender;

    @Barnacle
    @ColumnName("second_fa")
    protected boolean secondFA;

    @Barnacle
    @ColumnName("email_confirmation")
    protected Long emailConfirmation;

    @Barnacle
    @ColumnName("email_conf_token")
    @Unique
    protected String emailConfToken;

    @Barnacle
    @ColumnName("email_conf_code")
    protected String emailConfCode;

    @Barnacle
    @ColumnName("renew_pw_req")
    protected boolean renewPasswordRequired;

}
