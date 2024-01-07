package com.ecosystem.chomiyeon.builder;

import com.ecosystem.chomiyeon.entity.CmyEmployee;

public class CmyEmployeeBuilder {
    private CmyEmployee cmyEmployee;


    public CmyEmployeeBuilder(){
        this.cmyEmployee = new CmyEmployee();
    }


    public CmyEmployeeBuilder withId(Long Id) {
        this.cmyEmployee.setId(Id);
        return this;
    }
    public CmyEmployeeBuilder withFirstName(String firstName) {
        this.cmyEmployee.setFirstName(firstName);
        return this;
    }

    public CmyEmployeeBuilder withLastName(String lastName) {
        this.cmyEmployee.setLastName(lastName);
        return this;
    }

    public CmyEmployeeBuilder withEmail(String email) {
        this.cmyEmployee.setLastName(email);
        return this;
    }



    public CmyEmployeeBuilder builder() {
        return this;
    }
    public CmyEmployee build(){
        return this.cmyEmployee;
    }

}
