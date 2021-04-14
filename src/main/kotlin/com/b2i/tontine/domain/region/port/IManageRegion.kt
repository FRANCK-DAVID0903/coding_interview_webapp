package com.b2i.tontine.domain.region.port

import com.b2i.tontine.domain.region.entity.Region
import com.b2i.tontine.utils.OperationResult
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:04
 */
interface IManageRegion {

    fun saveRegion(region: Region): OperationResult<Region>

    fun findByLabel(label:String): Optional<Region>

    fun findAllRegion(): List<Region>

    fun findById(id:Long): Optional<Region>

    fun countAllRegion(): Long

}