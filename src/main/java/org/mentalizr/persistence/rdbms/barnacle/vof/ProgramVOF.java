package org.mentalizr.persistence.rdbms.barnacle.vof;


import de.arthurpicht.barnacle.annotations.Annotations.Barnacle;
import de.arthurpicht.barnacle.annotations.Annotations.ColumnName;
import de.arthurpicht.barnacle.annotations.Annotations.PrimaryKey;

import java.io.Serializable;

@Barnacle
public class ProgramVOF implements Serializable {

    @Barnacle
    @PrimaryKey
    @ColumnName("program_id")
    protected String programId;

}
