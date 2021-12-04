package com.dave.coding_interview_webapp.infrastructure.local.storage

import com.dave.coding_interview_webapp.infrastructure.local.storage.StorageException

class StorageFileNotFoundException : StorageException {

    constructor(message: String) : super(message)

    constructor(message: String, cause: Throwable) : super(message, cause)
}