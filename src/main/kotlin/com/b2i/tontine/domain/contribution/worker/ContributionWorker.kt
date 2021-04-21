package com.b2i.tontine.domain.contribution.worker

import com.b2i.tontine.domain.contribution.port.ContributionDomain
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


/**
 * @Author: Ettien Kamenan
 * @Date: 2021/04/13
 * @Time: 11:24
 */
@Service
class ContributionWorker: ContributionDomain {

    @Autowired
    lateinit var contributionDomain: ContributionDomain
}