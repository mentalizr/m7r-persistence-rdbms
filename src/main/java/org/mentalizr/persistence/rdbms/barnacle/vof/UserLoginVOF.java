package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.*;

@Barnacle
public class UserLoginVOF {

    @Barnacle
    @ColumnName("user_id")
    @PrimaryKey
    @ForeignKey(
            foreignKeyName = "fk_user_login_user_id",
            referenceTableName = "user",
            referenceColumnName = "user_id",
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


}
