package com.ecosystem.chomiyeon.controller;

import com.ecosystem.chomiyeon.dto.CmyEmployeeDTO;
import com.ecosystem.chomiyeon.entity.CmyEmployee;
import com.ecosystem.chomiyeon.exception.ResourceNotFoundException;
import com.ecosystem.chomiyeon.response.CmnEmployeeResponse;
import com.ecosystem.chomiyeon.service.ICmyEmployeeService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

import static com.ecosystem.chomiyeon.constant.Route.*;
import static org.springframework.http.HttpStatus.*;

@CrossOrigin(value = "http://localhost:3000/")
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


            CmnEmployeeResponse cmnEmployeeResponse = new CmnEmployeeResponse(cmyEmployee, getUri(id,PATH_DETAIL_CMY_EMPLOYEE));
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
        URI userUri = getUri(cmyEmployeeId,null);

        logger.info("Generated URI for user with ID {}: {}", cmyEmployeeId, userUri);
        return new ResponseEntity<>(userUri, OK);
    }


    /**
     * Handles HTTP PUT requests to update a CmyEmployee with the provided ID.
     *
     * @param id                 The ID of the CmyEmployee to update.
     * @param updateFields The updated information for the CmyEmployee.
     * @return A ResponseEntity with a CmnEmployeeResponse and an HTTP status code.
     */
    @PutMapping(PATH_UPDATE_CMY_EMPLOYEE + "/{id}")
    public ResponseEntity<CmnEmployeeResponse> updateCmnEmployee(@PathVariable Long id, @RequestBody Map<String, Object> updateFields) {
        try {
            CmyEmployeeDTO cmyEmployee = this.iCmyEmployeeService.updateCmyEmployee(id, updateFields);

            if (cmyEmployee != null) {
                URI uri = getUri(id,PATH_UPDATE_CMY_EMPLOYEE);
                CmnEmployeeResponse cmnEmployeeResponse = new CmnEmployeeResponse( cmyEmployee, uri);
                return ResponseEntity.ok(cmnEmployeeResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Log the exception for further analysis
            logger.error("Error updating CmyEmployee with ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Handles HTTP DELETE requests to delete a CmyEmployee with the provided ID.
     *
     * @param id The ID of the CmyEmployee to be deleted.
     * @return A ResponseEntity with a success message and an HTTP status code.
     */
    @DeleteMapping(PATH_DELETE_CMY_EMPLOYEE+"/{id}")
    public ResponseEntity<String> deleteCmyEmployee(@PathVariable Long id){
        this.iCmyEmployeeService.deleteCmyEmployee(id);
        return ResponseEntity.ok("Cmy Employee deleted successfully !.");
    }

    private URI getUri(Long cmyEmployeeId, String featurePath) {
        return URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path(PATH_ORG_CMY_EMPLOYEE+featurePath +"/"+ cmyEmployeeId).toUriString());
    }
}
