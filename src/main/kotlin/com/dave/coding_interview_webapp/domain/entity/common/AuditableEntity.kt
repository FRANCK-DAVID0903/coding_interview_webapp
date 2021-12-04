package com.dave.coding_interview_webapp.domain.entity.common

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

/**
 * @author alexwilfriedo
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AuditableEntity<U> {

    @Column(name = "created_by")
    @CreatedBy
    protected open var createdBy: U? = null

    @Column(name = "updated_by")
    @LastModifiedBy
    protected open var updatedBy: U? = null

    @Column(name = "created_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @CreatedDate
    protected open var createdDate: LocalDateTime? = null

    @Column(name = "updated_date", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    @LastModifiedDate
    protected open var updatedDate: LocalDateTime? = null
}
