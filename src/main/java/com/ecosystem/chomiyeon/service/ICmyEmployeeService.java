package com.ecosystem.chomiyeon.service;

import com.ecosystem.chomiyeon.dto.CmyEmployeeDTO;

import java.util.List;
import java.util.Map;

public interface ICmyEmployeeService {
    CmyEmployeeDTO createCmyEmployee(CmyEmployeeDTO cmyEmployeeDTO);
    List<CmyEmployeeDTO> listCmyEmployees();
    CmyEmployeeDTO getCmyEmployeeById(Long cmyEmployeeId);
    CmyEmployeeDTO updateCmyEmployee(Long cmyEmployeeId, Map<String, Object> updateFields);

    void deleteCmyEmployee(Long cmyEmployeeId);
}
