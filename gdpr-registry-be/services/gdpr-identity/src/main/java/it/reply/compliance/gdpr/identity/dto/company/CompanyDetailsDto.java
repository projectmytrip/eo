package it.reply.compliance.gdpr.identity.dto.company;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
@Data
public class CompanyDetailsDto extends CompanyDto {

    private String headquarterAddress;
    private String commercialRegister;
    private String emailAddress;
    private String phoneNumber;
    private String faxNumber;
    private String taxIdNumber;
    private String virtualName;
}
