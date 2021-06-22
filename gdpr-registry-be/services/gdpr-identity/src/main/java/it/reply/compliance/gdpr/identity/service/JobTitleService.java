package it.reply.compliance.gdpr.identity.service;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import it.reply.compliance.gdpr.identity.dto.JobTitleDto;
import it.reply.compliance.gdpr.identity.mapper.JobTitleMapper;
import it.reply.compliance.gdpr.identity.repository.JobTitleRepository;

@Service
public class JobTitleService {

    private final JobTitleRepository jobTitleRepository;
    private final JobTitleMapper jobTitleMapper;

    public JobTitleService(JobTitleRepository jobTitleRepository, JobTitleMapper jobTitleMapper) {
        this.jobTitleRepository = jobTitleRepository;
        this.jobTitleMapper = jobTitleMapper;
    }

    public Collection<JobTitleDto> getJobTitles() {
        return jobTitleRepository.findAll().stream().map(jobTitleMapper::fromEntity).collect(Collectors.toList());
    }
}
