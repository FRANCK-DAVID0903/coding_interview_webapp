package com.b2i.tontine.domain.region.worker

import com.b2i.tontine.domain.region.entity.Region
import com.b2i.tontine.domain.region.port.RegionDomain
import com.b2i.tontine.infrastructure.db.repository.RegionRepository
import com.b2i.tontine.utils.OperationResult
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/14
 * @Time: 14:00
 */
@Service
class RegionWorker : RegionDomain {

    @Autowired
    lateinit var regionRepository: RegionRepository

    override fun saveRegion(region: Region): OperationResult<Region> {

        val errors : MutableMap<String,String> = mutableMapOf()
        var data : Region? =null

        val regionName = regionRepository.findByLabel(region.label)

        if (regionName.isPresent){
            errors["name"] = "Une region avec ce nom existe déjà"
        }

        if (errors.isEmpty()){
            data = regionRepository.save(region)
        }

        return OperationResult(data, errors)
    }

    override fun findByLabel(label: String): Optional<Region> = regionRepository.findByLabel(label)

    override fun findAllRegion(): List<Region> = regionRepository.findAll()

    override fun findById(id: Long): Optional<Region> = regionRepository.findById(id)

    override fun countAllRegion(): Long {
        return  regionRepository.count()
    }
}