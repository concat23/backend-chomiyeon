package com.ecosystem.chomiyeon.mapper;

import com.ecosystem.chomiyeon.dto.CmyEmployeeDTO;
import com.ecosystem.chomiyeon.entity.CmyEmployee;

public class CmyEmployeeMapper {
    public static CmyEmployeeDTO mapToCmyEmployeeDTO(CmyEmployee cmyEmployee){
        return new CmyEmployeeDTO(
                cmyEmployee.getId(),
                cmyEmployee.getFirstName(),
                cmyEmployee.getLastName(),
                cmyEmployee.getEmail()
        );
    }

    public static CmyEmployee mapToCmyEmployee(CmyEmployeeDTO cmyEmployeeDTO){
        return new CmyEmployee(
                cmyEmployeeDTO.getId(),
                cmyEmployeeDTO.getFirstName(),
                cmyEmployeeDTO.getLastName(),
                cmyEmployeeDTO.getEmail()
        );
    }
}
