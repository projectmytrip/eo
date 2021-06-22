package it.reply.compliance.gdpr.util.scheduler.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.hibernate.annotations.Where;

import lombok.Data;

@Data
@Entity
@Where(clause = "is_active=1")
public class JobTrigger {

    @EmbeddedId
    private Key key;
    @Column(name = "job_name")
    private String jobName;
    @Column(name = "job_group")
    private String jobGroup;
    @Column(name = "cron_expression")
    private String cronExpression;
    @Column(name = "repeat_interval")
    private Long repeatInterval;
    @Column(name = "is_cron_trigger")
    private Boolean isCronTrigger;

    private Key getKey() {
        if (key == null) {
            key = new Key();
        }
        return key;
    }

    public String getName() {
        return getKey().getName();
    }

    public String getGroup() {
        return getKey().getGroup();
    }

    public void setName(String name) {
        getKey().setName(name);
    }

    public void setGroup(String group) {
        getKey().setGroup(group);
    }

    public JobTrigger addNamePrefix(String prefix) {
        if (this.key != null && this.key.name != null) {
            this.key.name = prefix + this.key.name;
        }
        return this;
    }

    @Data
    @Embeddable
    public static class Key implements Serializable {

        private static final long serialVersionUID = -3228349060138590505L;
        @Column(name = "trigger_name")
        private String name;
        @Column(name = "trigger_group")
        private String group;
    }
}