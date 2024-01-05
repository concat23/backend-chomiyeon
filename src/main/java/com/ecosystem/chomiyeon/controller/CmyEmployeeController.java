package com.ecosystem.chomiyeon.controller;

import com.ecosystem.chomiyeon.dto.CmyEmployeeDTO;
import com.ecosystem.chomiyeon.response.CmnEmployeeResponse;
import com.ecosystem.chomiyeon.service.ICmyEmployeeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.ecosystem.chomiyeon.constant.Route.*;
import static org.springframework.http.HttpStatus.*;

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

    /**
     * Handles HTTP GET requests to retrieve a single CmyEmployee by ID.
     *
     * @param id The ID of the CmyEmployee to retrieve.
     * @return A ResponseEntity with the retrieved CmyEmployeeDTO and HTTP OK status if found,
     *         or HTTP NOT_FOUND if the CmyEmployee is not found.
     */
    @GetMapping(PATH_DETAIL_CMY_EMPLOYEE+"/{id}")
    public ResponseEntity<CmnEmployeeResponse> getCmyEmployee(@PathVariable Long id) {
        logger.info("Received request to retrieve CmyEmployee with ID: {}", id);

        // Delegate to the service to retrieve the CmyEmployee by ID
        CmyEmployeeDTO cmyEmployee = this.iCmyEmployeeService.getCmyEmployeeById(id);

        if (cmyEmployee != null) {
            logger.info("Retrieved CmyEmployee: {}", cmyEmployee);


            CmnEmployeeResponse cmnEmployeeResponse = new CmnEmployeeResponse(cmyEmployee, getUri(id));
            return new ResponseEntity<>(cmnEmployeeResponse, OK);
        } else {
            logger.warn("CmyEmployee with ID {} not found", id);
            return new ResponseEntity<>(NOT_FOUND);
        }
    }

    /**
     * Handles HTTP GET requests to retrieve a URI for a specific user by ID.
     *
     * @param cmyEmployeeId The ID of the user for which to generate the URI.
     * @return A ResponseEntity with the generated URI and HTTP OK status.
     */
    @GetMapping("/getUri/{cmyEmployeeId}")
    public ResponseEntity<URI> getCmyEmployeeUri(@PathVariable Long cmyEmployeeId) {
        logger.info("Received request to retrieve URI for user with ID: {}", cmyEmployeeId);

        // Use the getUri method to create the URI for the specified user ID
        URI userUri = getUri(cmyEmployeeId);

        logger.info("Generated URI for user with ID {}: {}", cmyEmployeeId, userUri);
        return new ResponseEntity<>(userUri, OK);
    }


    private URI getUri(Long cmyEmployeeId) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(PATH_DETAIL_CMY_EMPLOYEE +"/"+ cmyEmployeeId).toUriString());
    }
}
