package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.*;

import java.io.Serializable;

@Barnacle
@VobFactory
@SerializableVo(serialVersionUID = 2023070301L)
public class UserVOF implements Serializable {

    @Barnacle
    @PrimaryKey
    protected String id;

    @Barnacle
    protected boolean active;
    // TODO default: true

    @Barnacle
    protected Long firstActive;

    @Barnacle
    protected Long lastActive;

}
