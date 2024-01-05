package com.ecosystem.chomiyeon.service;

import com.ecosystem.chomiyeon.dto.CmyEmployeeDTO;

import java.util.List;

public interface ICmyEmployeeService {
    CmyEmployeeDTO createCmyEmployee(CmyEmployeeDTO cmyEmployeeDTO);
    List<CmyEmployeeDTO> listCmyEmployees();
}
