package com.eni.enhop.be.shifthandoverlogbook.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.eni.enhop.be.shifthandoverlogbook.model.WorkShift;

public interface WorkShiftRepository extends PagingAndSortingRepository<WorkShift, Long> {

	List<WorkShift> findByLocationIdAndIsActiveTrue(long locationId);
}
