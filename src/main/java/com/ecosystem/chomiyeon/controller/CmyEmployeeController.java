package com.ecosystem.chomiyeon.controller;

import com.ecosystem.chomiyeon.dto.CmyEmployeeDTO;
import com.ecosystem.chomiyeon.service.ICmyEmployeeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.ecosystem.chomiyeon.constant.Route.PATH_CREATE_CMY_EMPLOYEE;
import static com.ecosystem.chomiyeon.constant.Route.PATH_ORG_CMY_EMPLOYEE;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@AllArgsConstructor
@RequestMapping(PATH_ORG_CMY_EMPLOYEE)
public class CmyEmployeeController {

    private final Logger logger = LoggerFactory.getLogger(CmyEmployeeController.class);

    private final ICmyEmployeeService iCmyEmployeeService;

    /**
     * This method handles HTTP POST requests for the //endpoint path.
     * It returns a response containing a message.
     * Build add cmy employee REST API
     * @return A ResponseEntity with a message and HTTP CREATED status.
     */

    @PostMapping(PATH_CREATE_CMY_EMPLOYEE)
    public ResponseEntity<CmyEmployeeDTO> createCmyEmployee(@RequestBody CmyEmployeeDTO requestCmyEmployeeDTO){
        CmyEmployeeDTO savedCmyEmployeeDTO = this.iCmyEmployeeService.createCmyEmployee(requestCmyEmployeeDTO);
        return new ResponseEntity<>(savedCmyEmployeeDTO,CREATED);
    }


    /**
     * Handles HTTP GET requests to list all CmyEmployees.
     *
     * @return A ResponseEntity with the list of CmyEmployeeDTOs and HTTP OK status.
     */
    @GetMapping
    public ResponseEntity<List<CmyEmployeeDTO>> listCmyEmployees() {
        logger.info("Received request to list CmyEmployees");

        // Delegate to the service to retrieve the list of CmyEmployees
        List<CmyEmployeeDTO> cmyEmployees = this.iCmyEmployeeService.listCmyEmployees();

        logger.info("List of CmyEmployees: {}", cmyEmployees);
        return new ResponseEntity<>(cmyEmployees, OK);
    }
}
