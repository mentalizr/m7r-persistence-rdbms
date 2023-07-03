package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.*;

@Barnacle
@TableName("policy_consent")
@SerializableVo(serialVersionUID = 2023070301L)
public class PolicyConsentVOF {

    @Barnacle
    @ColumnName("user_id")
    @PrimaryKey
    @ForeignKey(
            foreignKeyName = "fk_policy_consent_user_id",
            referenceTableName = "user",
            referenceColumnName = "id",
            getEntityMethod = false,
//            entityMethodName = "therapistUserVO",
            getReferenceEntityMethod = true,
            referenceEntityMethodName = "policyConsentVOs"
    )
    protected String userId;

    @Barnacle
    @PrimaryKey
    protected String version;

    @Barnacle
    protected Long consent;

}
