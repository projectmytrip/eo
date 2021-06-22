package it.reply.compliance.gdpr.report.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.reply.compliance.gdpr.report.dto.ActivityReportDto;
import it.reply.compliance.gdpr.report.dto.ActivityReportRequest;
import it.reply.compliance.gdpr.report.service.ActivityService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/registries")
class ReportController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/activities")
    public Page<ActivityReportDto> getActivityReport(ActivityReportRequest request,
            @PageableDefault Pageable pageable) {
        return activityService.getActivityReport(request, pageable);
    }
}
