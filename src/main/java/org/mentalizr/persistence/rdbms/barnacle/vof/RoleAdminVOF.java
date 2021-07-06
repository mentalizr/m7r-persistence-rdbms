package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.*;

import java.io.Serializable;

@Barnacle
@TableName("role_admin")
public class RoleAdminVOF implements Serializable {

    @Barnacle
    @ColumnName("id")
    @PrimaryKey
    @ForeignKey(
            foreignKeyName = "fk_admin_user_id",
            referenceTableName = "user",
            referenceColumnName = "user_id",
            getEntityMethod = true,
            entityMethodName = "adminUserVO",
            getReferenceEntityMethod = true,
            referenceEntityMethodName = "roleAdminVO"
    )
    protected String id;

}
