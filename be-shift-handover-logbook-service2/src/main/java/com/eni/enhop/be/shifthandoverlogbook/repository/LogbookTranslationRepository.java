package com.eni.enhop.be.shifthandoverlogbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.eni.enhop.be.shifthandoverlogbook.model.LogbookTranslation;

public interface LogbookTranslationRepository extends PagingAndSortingRepository<LogbookTranslation, Long> {

	List<LogbookTranslation> findByLanguage(String language);

	@Query("select un from LogbookTranslation un where un.code = :code and un.language = :lang")
	List<LogbookTranslation> findByCodeAndLanguage(@Param("code") String code, @Param("lang") String lang);
}
