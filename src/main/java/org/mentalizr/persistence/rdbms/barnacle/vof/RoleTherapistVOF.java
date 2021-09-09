package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.*;

import java.io.Serializable;

@Barnacle
@TableName("role_therapist")
public class RoleTherapistVOF implements Serializable {

    @Barnacle
    @ColumnName("user_id")
    @PrimaryKey
    @ForeignKey(
            foreignKeyName = "fk_therapist_user_id",
            referenceTableName = "user",
            referenceColumnName = "id",
            getEntityMethod = true,
            entityMethodName = "therapistUserVO",
            getReferenceEntityMethod = true,
            referenceEntityMethodName = "roleTherapistVO"
    )
    protected String userId;

    @Barnacle
    protected String title;




}
