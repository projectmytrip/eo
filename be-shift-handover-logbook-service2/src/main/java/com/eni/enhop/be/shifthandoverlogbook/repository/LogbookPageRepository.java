package com.eni.enhop.be.shifthandoverlogbook.repository;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.eni.enhop.be.shifthandoverlogbook.model.LogbookPage;
import com.eni.enhop.be.shifthandoverlogbook.model.LogbookType;
import com.eni.enhop.be.shifthandoverlogbook.model.QLogbookPage;
import com.eni.enhop.be.shifthandoverlogbook.model.QSquadMember;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;

public interface LogbookPageRepository extends PagingAndSortingRepository<LogbookPage, Long>,
		QuerydslPredicateExecutor<LogbookPage>, QuerydslBinderCustomizer<QLogbookPage> {

	Page<LogbookPage> findAll(Predicate predicate, Pageable pageable);

	Optional<LogbookPage> findByLogbookTypeAndIdAndIsActiveTrue(String logbookType, Long id);

	@Query("select l from LogbookPage l where l.logbookDate=:logbookDate and l.workShift.id=:workshiftId and l.logbookType=:logbookType and l.locationId=:locationId and l.plantSection.id = :plantSectionId")
	Optional<LogbookPage> findByExistingLogbookPage(Long locationId, String logbookType,
			LocalDate logbookDate, Long workshiftId, Long plantSectionId);

	@Override
	default void customize(QuerydslBindings bindings, QLogbookPage model) {
		bindings.bind(model.logbookDate).all((path, value) -> {
			Iterator<? extends LocalDate> it = value.iterator();
			LocalDate from = it.next();
			if (value.size() >= 2) {
				LocalDate to = it.next();
				return Optional.of(path.between(from, to));
			} else {
				return Optional.of(path.goe(from));
			}
		});

		bindings.bind(model.shiftLeader).all((path, value) -> {
			QSquadMember squadMember = QSquadMember.squadMember;
			return Optional.of(JPAExpressions.selectFrom(squadMember).where(squadMember.in(model.squad),
					squadMember.operator.id.in(value), squadMember.role.code.eq(LogbookType.SHIFT_LEADER)).exists());
		});

		bindings.bind(model.doubleShiftWorker).all((path, value) -> {
			QSquadMember squadMember = QSquadMember.squadMember;
			return Optional.of(JPAExpressions.selectFrom(squadMember).where(squadMember.in(model.squad),
					squadMember.operator.id.in(value), squadMember.role.code.eq(LogbookType.DOUBLE_SHIFT_WORKER))
					.exists());
		});

		bindings.bind(model.controlRoomOperator).all((path, value) -> {
			QSquadMember squadMember = QSquadMember.squadMember;
			return Optional.of(JPAExpressions.selectFrom(squadMember).where(squadMember.in(model.squad),
					squadMember.operator.id.in(value), squadMember.role.code.eq(LogbookType.OPERATOR_CONTROL_ROOM))
					.exists());
		});

		bindings.bind(model.externalOperator).all((path, value) -> {
			QSquadMember squadMember = QSquadMember.squadMember;
			return Optional.of(JPAExpressions.selectFrom(squadMember).where(squadMember.in(model.squad),
					squadMember.operator.id.in(value), squadMember.role.code.eq(LogbookType.OPERATOR_EXTERNAL))
					.exists());
		});

		bindings.bind(model.supervisor).all((path, value) -> {
			QSquadMember squadMember = QSquadMember.squadMember;
			return Optional
					.of(JPAExpressions
							.selectFrom(squadMember).where(squadMember.in(model.squad),
									squadMember.operator.id.in(value), squadMember.role.code.eq(LogbookType.SUPERVISOR))
							.exists());
		});

	}
}
