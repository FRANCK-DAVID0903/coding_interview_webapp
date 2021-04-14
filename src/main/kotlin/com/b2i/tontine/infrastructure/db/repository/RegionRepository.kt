package com.b2i.tontine.infrastructure.db.repository

import com.b2i.tontine.domain.region.entity.Region
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:17
 */
interface RegionRepository : JpaRepository<Region, Long> {

    fun findByLabel(label : String) : Optional<Region>

}