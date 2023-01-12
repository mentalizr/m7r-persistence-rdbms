package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.*;

@Barnacle
@TableName("policy")
public class PolicyVOF {

    @Barnacle
    @ColumnName("user_id")
    @PrimaryKey
    @ForeignKey(
            foreignKeyName = "fk_policy_user_id",
            referenceTableName = "user",
            referenceColumnName = "id",
            getEntityMethod = false,
//            entityMethodName = "therapistUserVO",
            getReferenceEntityMethod = true,
            referenceEntityMethodName = "policyVO"
    )
    protected String userId;

    @Barnacle
    @PrimaryKey
    protected String version;

    @Barnacle
    protected Long consent;

}
