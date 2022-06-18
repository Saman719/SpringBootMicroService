package com.example.SpringProject.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Builder
@Entity
@Table(name = "t_cardprintrequest")
public class CardPrintRequest {

    @Valid
    @EmbeddedId
    CardPrintRequestPK cardPrintRequestPK;

    @Column(name = "c_personnelcode")
    @NotBlank(message = "Personnel Code Must Not Be Blank")
    @Size(min = 5, max = 10)
    private String personnelCode;

    @Column(name = "c_issueddate")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date issuedDate;

    @Column(name = "c_cardpan")
    private String cardPAN;

    public CardPrintRequest() {
    }

    public CardPrintRequest(CardPrintRequestPK cardPrintRequestPK, String personnelCode, Date issuedDate, String cardPAN) {
        this.cardPrintRequestPK = cardPrintRequestPK;
        this.personnelCode = personnelCode;
        this.issuedDate = issuedDate;
        this.cardPAN = cardPAN;
    }

    public CardPrintRequestPK getCardPrintRequestPK() {
        return cardPrintRequestPK;
    }

    public void setCardPrintRequestPK(CardPrintRequestPK cardPrintRequestPK) {
        this.cardPrintRequestPK = cardPrintRequestPK;
    }

    public String getPersonnelCode() {
        return personnelCode;
    }

    public void setPersonnelCode(String personnelCode) {
        this.personnelCode = personnelCode;
    }

    public Date getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(Date issuedDate) {
        this.issuedDate = issuedDate;
    }

    public void setCardPAN(String cardPAN) {
        this.cardPAN = cardPAN;
    }

    public String getCardPAN() {
        return cardPAN;
    }

    public String getIpAddress() {

        return cardPrintRequestPK != null ? cardPrintRequestPK.getIpAddress() : null;
    }

    public void setIpAddress(String ipAddress) {
        if(cardPrintRequestPK == null) {
            cardPrintRequestPK = new CardPrintRequestPK();
        }
        cardPrintRequestPK.setIpAddress(ipAddress);
    }

    public String getBranchCode() {
        return cardPrintRequestPK != null ? cardPrintRequestPK.getBranchCode() : null;
    }

    public void setBranchCode(String branchCode) {
        if(cardPrintRequestPK == null) {
            cardPrintRequestPK = new CardPrintRequestPK();
        }
        cardPrintRequestPK.setBranchCode(branchCode);
    }
}
