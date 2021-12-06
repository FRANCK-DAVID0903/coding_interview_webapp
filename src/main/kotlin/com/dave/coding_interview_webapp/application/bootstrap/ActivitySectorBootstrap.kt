package com.dave.coding_interview_webapp.application.bootstrap

import com.dave.coding_interview_webapp.domain.activity_sector.entity.ActivitySector
import com.dave.coding_interview_webapp.domain.activity_sector.port.ActivitySectorDomain

object ActivitySectorBootstrap {

    fun seed(activitySectorDomain: ActivitySectorDomain) {

        activitySectorDomain.let { activity ->
            if (activity.countAllActivities() == 0L) {

                val activitySec = ActivitySector()

                //Item 0
                activitySec.label = "Plombier"
                activity.saveActivitySector(activitySec)

                //Item 1
                activitySec.label = "Menuisier"
                activity.saveActivitySector(activitySec)

                //Item 2
                activitySec.label = "Carreleur"
                activity.saveActivitySector(activitySec)

                //Item 3
                activitySec.label = "Couturier"
                activity.saveActivitySector(activitySec)

                //Item 4
                activitySec.label = "Peintre"
                activity.saveActivitySector(activitySec)

                //Item 5
                activitySec.label = "Menagère"
                activity.saveActivitySector(activitySec)

                //Item 6
                activitySec.label = "Nettoyeur"
                activity.saveActivitySector(activitySec)

                //Item 7
                activitySec.label = "Cuisinier"
                activity.saveActivitySector(activitySec)

                //Item 8
                activitySec.label = "Jardinier"
                activity.saveActivitySector(activitySec)

                //Item 9
                activitySec.label = "Electricien"
                activity.saveActivitySector(activitySec)

                //Item 9
                activitySec.label = "Infirmier"
                activity.saveActivitySector(activitySec)

                //Item 9
                activitySec.label = "Autres"
                activity.saveActivitySector(activitySec)

            }
        }
    }
}

