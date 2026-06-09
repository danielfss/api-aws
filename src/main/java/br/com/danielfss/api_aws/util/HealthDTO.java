package br.com.danielfss.api_aws.util;

public class HealthDTO {
    private String status;

    public HealthDTO(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
