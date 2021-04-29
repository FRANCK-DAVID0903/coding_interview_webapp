package com.b2i.tontine.domain.tontine_periodicity.worker

import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import com.b2i.tontine.domain.tontine_periodicity.port.TontinePeriodicityDomain
import com.b2i.tontine.utils.OperationResult
import org.springframework.stereotype.Service

@Service
class TontinePeriodicityWorker: TontinePeriodicityDomain {

    override fun saveTontinePeriodicity(tontinePeriodicity: TontinePeriodicity): OperationResult<TontinePeriodicity> {

        var errors: MutableMap<String, String> = mutableMapOf()
        var data: TontinePeriodicity? = null

        return OperationResult(data,errors)
    }
}