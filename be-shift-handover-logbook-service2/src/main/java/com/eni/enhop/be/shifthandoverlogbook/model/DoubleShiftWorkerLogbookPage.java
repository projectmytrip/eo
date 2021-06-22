package com.eni.enhop.be.shifthandoverlogbook.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(LogbookType.DOUBLE_SHIFT_WORKER)
public class DoubleShiftWorkerLogbookPage extends LogbookPage {

}
