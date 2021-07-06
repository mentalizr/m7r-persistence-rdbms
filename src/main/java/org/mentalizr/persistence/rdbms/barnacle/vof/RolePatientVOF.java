package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.*;

import java.io.Serializable;

@Barnacle
@TableName("role_patient")
public class RolePatientVOF implements Serializable {

    @Barnacle
    @ColumnName("id")
    @PrimaryKey
    @ForeignKey(
            foreignKeyName = "fk_patient_user_id",
            referenceTableName = "user",
            referenceColumnName = "user_id",
            getEntityMethod = true,
            entityMethodName = "userVO",
            getReferenceEntityMethod = true,
            referenceEntityMethodName = "rolePatientVO"
    )
    protected String id;

    @Barnacle
    @ColumnName("program_id")
    @ForeignKey(
            foreignKeyName = "fk_program_id",
            referenceTableName = "program",
            referenceColumnName = "program_id",
            getEntityMethod = true,
            entityMethodName = "programVO"
    )
    protected String programId;

    @Barnacle
    @ColumnName("therapist_id")
    @ForeignKey(
            foreignKeyName = "fk_therapist_id",
            referenceTableName = "role_therapist",
            referenceColumnName = "id",
            getEntityMethod = true,
            entityMethodName = "roleTherapistVO"
    )
    protected String therapistId;


}
