package com.b2i.tontine.domain.tontine_periodicity.port

import com.b2i.tontine.domain.tontine_periodicity.entity.TontinePeriodicity
import com.b2i.tontine.utils.OperationResult

interface IManageTontinePeriodicity {

    fun saveTontinePeriodicity(tontinePeriodicity: TontinePeriodicity): OperationResult<TontinePeriodicity>
}