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

//    @Barnacle
//    // @NotNull
//    @Unique
//    protected String username;
//
//    @Barnacle
//    @ColumnName("password_hash")
//    // @NotNull
//    protected String passwordHash;

//    protected String accessKey;

//    @Barnacle
//    // @NotNull
//    protected String email;
//
//    @Barnacle
//    @ColumnName("first_name")
//    protected String firstName;
//
//    @Barnacle
//    @ColumnName("last_name")
//    protected String lastName;
//
//    @Barnacle
//    protected int gender;

}
