package com.b2i.tontine.domain.tontine.worker

import com.b2i.tontine.domain.tontine.port.TontineDomain
import com.b2i.tontine.infrastructure.db.repository.TontineRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:41
 */
@Service
class TontineWorker: TontineDomain {

    @Autowired
    lateinit var tontineRepository: TontineRepository
}