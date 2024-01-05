package com.ecosystem.chomiyeon.response;

import com.ecosystem.chomiyeon.dto.CmyEmployeeDTO;

import java.net.URI;

public class CmnEmployeeResponse {
    private CmyEmployeeDTO cmyEmployeeDTO;
    private URI uri;

    public CmnEmployeeResponse(CmyEmployeeDTO cmyEmployeeDTO, URI uri) {
        this.cmyEmployeeDTO = cmyEmployeeDTO;
        this.uri = uri;
    }

    public CmyEmployeeDTO getCmyEmployeeDTO() {
        return cmyEmployeeDTO;
    }

    public void setCmyEmployeeDTO(CmyEmployeeDTO cmyEmployeeDTO) {
        this.cmyEmployeeDTO = cmyEmployeeDTO;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
}
