package com.eni.enhop.be.shifthandoverlogbook.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(LogbookType.OPERATOR_EXTERNAL)
public class ExternalOperatorLogbookPage extends LogbookPage {

}
