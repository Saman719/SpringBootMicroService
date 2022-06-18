package com.example.SpringProject.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Embeddable
public class CardPrintRequestPK implements Serializable {

    @Column(name = "c_ipaddress")
    @Pattern(regexp = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)")
    private String ipAddress;

    @Column(name = "c_branchcode")
    @NotBlank(message = "Branch Code Must Not Be Blank")
    @Size(min = 3)
    private String branchCode;



    public CardPrintRequestPK() {}

    public CardPrintRequestPK(String ipAddress, String branchCode) {
        this.ipAddress = ipAddress;
        this.branchCode = branchCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }
}
