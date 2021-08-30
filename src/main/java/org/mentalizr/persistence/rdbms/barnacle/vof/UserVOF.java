package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.*;

import java.io.Serializable;
import java.util.Date;

@Barnacle
@VobFactory
public class UserVOF implements Serializable {

    @Barnacle
    @ColumnName("user_id")
    @PrimaryKey
    protected String userId;

    @Barnacle
    protected boolean active;
    // TODO default: true

    @Barnacle
    protected Date firstActive;

    @Barnacle
    protected Date lastActive;

}
