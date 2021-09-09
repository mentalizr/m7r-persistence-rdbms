package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.*;

import java.io.Serializable;

@Barnacle
@TableName("patient_program")
public class PatientProgramVOF implements Serializable {

    @Barnacle
    @ColumnName("user_id")
    @PrimaryKey
    @ForeignKey(
            foreignKeyName = "fk_user_id",
            referenceTableName = "role_patient",
            referenceColumnName = "user_id",
            getEntityMethod = true,
            entityMethodName = "rolePatientVO",
            getReferenceEntityMethod = true,
            referenceEntityMethodName = "patientProgramVO"
    )
    @Unique
    protected String userId;

    @Barnacle
    @ColumnName("program_id")
    @PrimaryKey
    @ForeignKey(
            foreignKeyName = "fk_program_id",
            referenceTableName = "program",
            referenceColumnName = "id",
            getEntityMethod = true,
            entityMethodName = "programVO"
    )
    protected String programId;

    @Barnacle
    protected boolean blocking;

}
