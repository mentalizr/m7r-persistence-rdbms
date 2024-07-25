package org.mentalizr.persistence.rdbms.userAdmin;

import de.arthurpicht.utils.core.collection.Sets;
import org.mentalizr.persistence.rdbms.barnacle.connectionManager.DataSourceException;
import org.mentalizr.persistence.rdbms.barnacle.dao.PatientProgramDAO;
import org.mentalizr.persistence.rdbms.barnacle.dao.RolePatientDAO;
import org.mentalizr.persistence.rdbms.barnacle.vo.PatientProgramVO;
import org.mentalizr.persistence.rdbms.barnacle.vo.RolePatientVO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Patient {

    /**
     * Returns as set of all userIds for patients related to specified program and specified project.
     *
     * @param programId id or program
     * @param projectId is or project
     * @return set of patient userIds
     * @throws DataSourceException dataSourceException from underlying DAOs
     */
    public static Set<String> find(String programId, String projectId) throws DataSourceException {

        List<PatientProgramVO> patientProgramVOs = PatientProgramDAO.findByFk_program_id(programId);
        Set<String> patientProgramUserIds =
                patientProgramVOs.stream()
                        .map(PatientProgramVO::getUserId)
                        .collect(Collectors.toSet());
        if (patientProgramUserIds.isEmpty()) return new HashSet<>();


        List<RolePatientVO> rolePatientVOs = RolePatientDAO.findByFk_project_id(projectId);
        Set<String> rolePatientUserIds =
                rolePatientVOs.stream()
                        .map(RolePatientVO::getUserId)
                        .collect(Collectors.toSet());
        if (rolePatientUserIds.isEmpty()) return new HashSet<>();

        return Sets.intersection(patientProgramUserIds, rolePatientUserIds);
    }

}
