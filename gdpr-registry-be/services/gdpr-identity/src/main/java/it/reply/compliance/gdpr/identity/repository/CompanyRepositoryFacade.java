package it.reply.compliance.gdpr.identity.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import it.reply.compliance.gdpr.identity.model.Company;
import it.reply.compliance.gdpr.identity.model.CompanyDetails;

@Repository
public class CompanyRepositoryFacade {

    private final CompanyRepository companyRepository;
    private final CompanyDetailsRepository companyDetailsRepository;

    public CompanyRepositoryFacade(CompanyRepository companyRepository,
            CompanyDetailsRepository companyDetailsRepository) {
        this.companyRepository = companyRepository;
        this.companyDetailsRepository = companyDetailsRepository;
    }

    public Collection<Company> findAll(Specification<Company> specification, Sort sort) {
        return companyRepository.findAll(specification, sort);
    }

    public Optional<CompanyDetails> findOne(Specification<CompanyDetails> specification) {
        return companyDetailsRepository.findOne(specification);
    }
}
