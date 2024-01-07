package com.ecosystem.chomiyeon.builder;

import com.ecosystem.chomiyeon.dto.CmyEmployeeDTO;

public class CmyEmployeeDTOBuilder {
    private CmyEmployeeDTO cmyEmployeeDTO;


    public CmyEmployeeDTOBuilder(){
        this.cmyEmployeeDTO = new CmyEmployeeDTO();
    }


    public CmyEmployeeDTOBuilder withId(Long Id) {
        this.cmyEmployeeDTO.setId(Id);
        return this;
    }
    public CmyEmployeeDTOBuilder withFirstName(String firstName) {
        this.cmyEmployeeDTO.setFirstName(firstName);
        return this;
    }

    public CmyEmployeeDTOBuilder withLastName(String lastName) {
        this.cmyEmployeeDTO.setLastName(lastName);
        return this;
    }

    public CmyEmployeeDTOBuilder withEmail(String email) {
        this.cmyEmployeeDTO.setLastName(email);
        return this;
    }



    public CmyEmployeeDTOBuilder builder() {
        return this;
    }
    public CmyEmployeeDTO build(){
        return this.cmyEmployeeDTO;
    }

}
