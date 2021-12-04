package com.dave.coding_interview_webapp.infrastructure.db.repository

import com.dave.coding_interview_webapp.domain.ads.entity.Ads
import com.dave.coding_interview_webapp.domain.warning.entity.Warning
import org.springframework.data.jpa.repository.JpaRepository

interface WarningRepository: JpaRepository<Warning,Long> {

    fun findAllByAds(ads: Ads): MutableList<Warning>
}