package org.mentalizr.persistence.rdbms.barnacle.vof;

import de.arthurpicht.barnacle.annotations.Annotations.Barnacle;
import de.arthurpicht.barnacle.annotations.Annotations.PrimaryKey;
import de.arthurpicht.barnacle.annotations.Annotations.SerializableVo;

import java.io.Serializable;

@Barnacle
@SerializableVo(serialVersionUID = 2024070201L)
public class ProjectVOF implements Serializable {

    @Barnacle
    @PrimaryKey
    protected String id;

    @Barnacle
    protected String label;

}
