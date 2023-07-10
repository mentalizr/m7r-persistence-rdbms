package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.*;

import java.io.Serializable;

@Barnacle
@TableName("role_patient")
@SerializableVo(serialVersionUID = 2023070301L)
public class RolePatientVOF implements Serializable {

    @Barnacle
    @ColumnName("user_id")
    @PrimaryKey
    @ForeignKey(
            foreignKeyName = "fk_patient_user_id",
            referenceTableName = "user",
            referenceColumnName = "id",
            getEntityMethod = true,
            entityMethodName = "userVO",
            getReferenceEntityMethod = true,
            referenceEntityMethodName = "rolePatientVO"
    )
    protected String userId;

    @Barnacle
    @ColumnName("therapist_id")
    @ForeignKey(
            foreignKeyName = "fk_therapist_id",
            referenceTableName = "role_therapist",
            referenceColumnName = "user_id",
            getEntityMethod = true,
            entityMethodName = "roleTherapistVO"
    )
    protected String therapistId;


}
