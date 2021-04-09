package com.b2i.tontine.infrastructure.local.storage

import org.springframework.core.io.Resource
import org.springframework.web.multipart.MultipartFile
import java.nio.file.Path
import java.util.stream.Stream


interface IManageStorage {

    fun init()

    fun store(file: MultipartFile, name: String): Pair<Boolean, String>

    fun storeMix(file: MultipartFile, name: String,folder:Path): Pair<Boolean, String>


    fun loadAll(): Stream<Path>

    fun load(filename: String): Path

    fun loadAsResource(filename: String): Resource

    fun deleteAll()
}