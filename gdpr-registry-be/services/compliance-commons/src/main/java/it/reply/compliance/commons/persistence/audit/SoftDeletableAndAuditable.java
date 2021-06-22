package it.reply.compliance.commons.persistence.audit;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class SoftDeletableAndAuditable extends Auditable<Long> {

    @JsonIgnore
    @Column(name = "is_deleted", columnDefinition = "NUMBER(1, 0) DEFAULT 0")
    private Boolean isDeleted = false;

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
        setIsDeletedCascade(isDeleted);
    }

    protected void setIsDeletedCascade(boolean isDeleted) {
    }
}
