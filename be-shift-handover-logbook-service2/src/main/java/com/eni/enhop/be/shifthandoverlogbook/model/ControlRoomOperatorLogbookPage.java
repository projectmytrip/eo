package com.eni.enhop.be.shifthandoverlogbook.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(LogbookType.OPERATOR_CONTROL_ROOM)
public class ControlRoomOperatorLogbookPage extends LogbookPage {

}
