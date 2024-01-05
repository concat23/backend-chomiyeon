package com.ecosystem.chomiyeon.mapper;

import com.ecosystem.chomiyeon.dto.CmyEmployeeDTO;
import com.ecosystem.chomiyeon.entity.CmyEmployee;

import java.util.List;
import java.util.stream.Collectors;

public class ListCmyEmployeeMapper {
    public static List<CmyEmployeeDTO> mapToCmyEmployeeDTOList(List<CmyEmployee> cmyEmployeeList) {
        return cmyEmployeeList.stream()
                .map(CmyEmployeeMapper::mapToCmyEmployeeDTO)
                .collect(Collectors.toList());
    }

    public static List<CmyEmployee> mapToCmyEmployeeList(List<CmyEmployeeDTO> cmyEmployeeDTOList) {
        return cmyEmployeeDTOList.stream()
                .map(CmyEmployeeMapper::mapToCmyEmployee)
                .collect(Collectors.toList());
    }
}
