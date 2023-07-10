package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.Barnacle;
import de.arthurpicht.barnacle.annotations.Annotations.PrimaryKey;
import de.arthurpicht.barnacle.annotations.Annotations.SerializableVo;

import java.io.Serializable;

@Barnacle
@SerializableVo(serialVersionUID = 2023070301L)
public class ProgramVOF implements Serializable {

    @Barnacle
    @PrimaryKey
    protected String id;

}
