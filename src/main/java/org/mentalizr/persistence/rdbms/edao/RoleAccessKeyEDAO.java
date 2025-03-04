package org.mentalizr.persistence.rdbms.edao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleAccessKeyEDAO {

    private static final Logger logger = LoggerFactory.getLogger(RolePatientEDAO.class);

    private static final String FIND_ALL_BY_PROJECT_ID_STATEMENT = "SELECT * FROM user_access_key WHERE user_id IN (SELECT role_patient.user_id FROM role_patient WHERE project_id = ?)";
    private static final String FIND_ALL_BY_PROGRAM_STATEMENT = "SELECT * FROM user_access_key WHERE user_id IN (SELECT patient_program.user_id FROM patient_program WHERE program_id = ?)";
    private static final String FIND_ALL_BY_PROGRAM_AND_PROJECT_STATEMENT = "SELECT * FROM user_access_key WHERE user_id IN (SELECT patient_program.user_id FROM patient_program WHERE program_id = ?) AND project_id = ?";



}
