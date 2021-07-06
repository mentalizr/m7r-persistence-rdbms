package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.*;

@Barnacle
public class UserAccessKeyVOF {

    @Barnacle
    @ColumnName("user_id")
    @PrimaryKey
    @ForeignKey(
            foreignKeyName = "fk_user_access_key_user_id",
            referenceTableName = "user",
            referenceColumnName = "user_id",
            getEntityMethod = true,
            entityMethodName = "userVO",
            getReferenceEntityMethod = true,
            referenceEntityMethodName = "userAccessKeyVO"
    )
    protected String userId;

    @Barnacle
    @NotNull
    @Unique
    protected String accessKey;

}
