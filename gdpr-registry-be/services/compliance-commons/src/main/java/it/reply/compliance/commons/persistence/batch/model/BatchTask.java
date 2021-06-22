package it.reply.compliance.commons.persistence.batch.model;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import it.reply.compliance.commons.persistence.ExceptionMessageHandler;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
public class BatchTask {

    public static final String PARTNER_DOCUMENTS_ALIGNMENT = "partner_documents_alignment";
    public static final String EMPLOYEE_DOCUMENTS_NOTIFICATION = "employee_documents_notification";
    public static final String PARTNER_ALIGNMENT = "partner_alignment";
    public static final String EMPLOYEE_ALIGNMENT = "employee_alignment";
    public static final String TAMTAMY_ALIGNMENT = "tamtamy_alignment";

    @Id
    private String id;
    @Enumerated(EnumType.STRING)
    private Status status;
    private ZonedDateTime startDateTime;
    private ZonedDateTime endDateTime;
    private ZonedDateTime lastSuccessfulRun;
    private String error;

    public BatchTask(String id) {
        this.id = id;
    }

    public void start() {
        this.status = Status.EXECUTING;
        this.startDateTime = ZonedDateTime.now();
    }

    public void end() {
        this.end(Status.DONE);
        this.error = null;
    }

    public void end(Throwable throwable) {
        this.end(Status.ERROR);
        this.error = ExceptionMessageHandler.extractMessage(throwable, 500);
    }

    private void end(Status status) {
        this.status = status;
        this.endDateTime = ZonedDateTime.now();
        if (status == Status.DONE) {
            this.lastSuccessfulRun = this.startDateTime;
        }
    }

    public enum Status {
        DONE,
        EXECUTING,
        ERROR
    }
}