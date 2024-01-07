package com.ecosystem.chomiyeon.service.implementation;

import com.ecosystem.chomiyeon.dto.CmyEmployeeDTO;
import com.ecosystem.chomiyeon.entity.CmyEmployee;
import com.ecosystem.chomiyeon.exception.ResourceNotFoundException;
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
import java.util.Map;

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

    @Override
    public CmyEmployeeDTO getCmyEmployeeById(Long cmyEmployeeId) {
        CmyEmployee cmyEmployee =this.iCmyEmployeeRepository.findById(cmyEmployeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Cmy Employee is not exist with given ID: " + cmyEmployeeId));
        return CmyEmployeeMapper.mapToCmyEmployeeDTO(cmyEmployee);
    }

    @Override
    public CmyEmployeeDTO updateCmyEmployee(Long cmyEmployeeId, Map<String, Object> updateFields) {
        logger.info("Updating CmyEmployee with ID: {}", cmyEmployeeId);

        CmyEmployee cmyEmployee = this.iCmyEmployeeRepository.findById(cmyEmployeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Cmy Employee is not exists with given ID: " + cmyEmployeeId));

        logger.info("Updating CmyEmployee properties: {}", updateFields);

        // Update the properties of the existing CmyEmployee entity with the data from updateFields
        if (updateFields.containsKey("firstName")) {
            cmyEmployee.setFirstName((String) updateFields.get("firstName"));
        }

        if (updateFields.containsKey("lastName")) {
            cmyEmployee.setLastName((String) updateFields.get("lastName"));
        }

        if (updateFields.containsKey("email")) {
            cmyEmployee.setEmail((String) updateFields.get("email"));
        }

        // Add more fields as needed

        // Save the updated CmyEmployee entity back to the repository
        CmyEmployee updatedCmyEmployeeObj = this.iCmyEmployeeRepository.save(cmyEmployee);

        logger.info("CmyEmployee with ID {} updated successfully", cmyEmployeeId);

        return CmyEmployeeMapper.mapToCmyEmployeeDTO(updatedCmyEmployeeObj);
    }

    /**
     * Deletes a CmyEmployee with the given ID.
     *
     * @param cmyEmployeeId The ID of the CmyEmployee to be deleted.
     * @throws ResourceNotFoundException if the CmyEmployee with the given ID is not found.
     */
    @Override
    public void deleteCmyEmployee(Long cmyEmployeeId) {
        // Attempt to find the CmyEmployee with the given ID
        CmyEmployee cmyEmployee = this.iCmyEmployeeRepository.findById(cmyEmployeeId)
                .orElseThrow(() -> new ResourceNotFoundException("Cmy Employee is not exists with given ID: " + cmyEmployeeId));

        // Delete the CmyEmployee from the repository
        this.iCmyEmployeeRepository.delete(cmyEmployee);

        // Log a message indicating successful deletion
        logger.info("CmyEmployee with ID {} deleted successfully", cmyEmployeeId);
    }

}
