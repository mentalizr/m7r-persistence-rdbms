package org.mentalizr.persistence.rdbms.barnacle.vob;


import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.vo.UserVO;

public class UserVOB extends UserVO {

    public UserVOB(String userId) {
        super(userId);
    }

    public boolean isTherapist() throws DataSourceException {
        return this.getRoleTherapistVO().size() > 0;
    }

    public boolean isPatient() throws DataSourceException {
        return this.getRolePatientVO().size() > 0;
    }

}
