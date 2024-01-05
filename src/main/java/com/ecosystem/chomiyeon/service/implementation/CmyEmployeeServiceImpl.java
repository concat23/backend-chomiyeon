package com.ecosystem.chomiyeon.service.implementation;

import com.ecosystem.chomiyeon.dto.CmyEmployeeDTO;
import com.ecosystem.chomiyeon.entity.CmyEmployee;
import com.ecosystem.chomiyeon.mapper.CmyEmployeeMapper;
import com.ecosystem.chomiyeon.mapper.ListCmyEmployeeMapper;
import com.ecosystem.chomiyeon.repository.ICmyEmployeeRepository;
import com.ecosystem.chomiyeon.service.ICmyEmployeeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class CmyEmployeeServiceImpl implements ICmyEmployeeService {

    // Log
    private final Logger logger = LoggerFactory.getLogger(CmyEmployeeServiceImpl.class);

    private final ICmyEmployeeRepository iCmyEmployeeRepository;

    @Override
    public CmyEmployeeDTO createCmyEmployee(CmyEmployeeDTO cmyEmployeeDTO) {
        logger.info("Creating CmyEmployee: {}", cmyEmployeeDTO);

        CmyEmployee cmyEmployee = CmyEmployeeMapper.mapToCmyEmployee(cmyEmployeeDTO);

        CmyEmployee savedCmyEmployee = this.iCmyEmployeeRepository.save(cmyEmployee);

        logger.info("CmyEmployee created: {}", savedCmyEmployee);

        return CmyEmployeeMapper.mapToCmyEmployeeDTO(savedCmyEmployee);
    }

    @Override
    public List<CmyEmployeeDTO> listCmyEmployees() {
        logger.info("Fetching the list of CmyEmployees from the repository.");

        List<CmyEmployee> cmyEmployeeList = this.iCmyEmployeeRepository.findAll();

        List<CmyEmployeeDTO> cmyEmployeeDTOList = ListCmyEmployeeMapper.mapToCmyEmployeeDTOList(cmyEmployeeList);

        logger.info("Retrieved {} CmyEmployees from the repository.", cmyEmployeeDTOList.size());

        return cmyEmployeeDTOList;
    }
}
