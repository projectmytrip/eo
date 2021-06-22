package com.eni.enhop.be.shifthandoverlogbook.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(LogbookType.SHIFT_LEADER)
public class ShiftLeaderLogbookPage extends LogbookPage {

}
