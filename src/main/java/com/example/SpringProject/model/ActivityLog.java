package com.example.SpringProject.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Builder
@Entity
@Table(name = "t_activitylog")
@PropertySource("classpath:application.properties")
@Component
public class ActivityLog {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "my_seq_gen")
    @SequenceGenerator(name = "my_seq_gen", sequenceName = "SUPPLIER_SEQ")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "c_issueddate")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date activityTime;

    @Column(name = "c_personnelcode")
    @NotBlank(message = "Personnel Code Must Not Be Blank")
    @Size(min = 5, max = 10)
    private String personnelCode;

    @Value("${application.type}")
    @Column(name = "c_applicationtype")
    private String applicationType;

    @Column(name = "c_cardpan")
    private String cardPAN;

    @Column(name = "c_activitydesc")
    private String activityDesc;



    public ActivityLog() {

    }

    public ActivityLog(Long id, Date activityTime, String personnelCode, String applicationType, String cardPAN, String activityDesc) {
        this.id = id;
        this.activityTime = activityTime;
        this.personnelCode = personnelCode;
        this.applicationType = applicationType;
        this.cardPAN = cardPAN;
        this.activityDesc = activityDesc;
    }

    public ActivityLog(Date activityTime, String personnelCode, String applicationType, String cardPAN, String activityDesc) {
        this.activityTime = activityTime;
        this.personnelCode = personnelCode;
        this.applicationType = applicationType;
        this.cardPAN = cardPAN;
        this.activityDesc = activityDesc;
    }

    public ActivityLog(Date activityTime, String personnelCode, String cardPAN, String activityDesc) {
        this.activityTime = activityTime;
        this.personnelCode = personnelCode;
        this.cardPAN = cardPAN;
        this.activityDesc = activityDesc;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(Date activityTime) {
        this.activityTime = activityTime;
    }

    public String getPersonnelCode() {
        return personnelCode;
    }

    public void setPersonnelCode(String personnelCode) {
        this.personnelCode = personnelCode;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getCardPAN() {
        return cardPAN;
    }

    public void setCardPAN(String cardPAN) {
        this.cardPAN = cardPAN;
    }

    public String getActivityDesc() {
        return activityDesc;
    }

    public void setActivityDesc(String activityDesc) {
        this.activityDesc = activityDesc;
    }
}
