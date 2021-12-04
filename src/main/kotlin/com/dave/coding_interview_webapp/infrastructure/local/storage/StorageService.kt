package com.dave.coding_interview_webapp.infrastructure.local.storage

import org.springframework.core.io.Resource
import org.springframework.core.io.UrlResource
import org.springframework.stereotype.Service
import org.springframework.util.FileSystemUtils
import org.springframework.util.StringUtils
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.net.MalformedURLException
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardCopyOption
import java.util.*
import java.util.stream.Stream


@Service
class StorageService : IManageStorage {

    companion object {
        val uploadDirectory: Path = Paths.get("upload")

        val uploadLocation: String by lazy {
            "file:${uploadDirectory.toFile().absolutePath}/"
        }
    }

    override fun init() {
        try {
            Files.createDirectories(uploadDirectory)
        } catch (e: IOException) {
            throw StorageException("Could not initialize storage", e)
        }

    }

    override fun store(file: MultipartFile, name: String): Pair<Boolean, String> {
        val nameParts = file.originalFilename?.let { StringUtils.cleanPath(it).split(".") }
        val filename = if (name.isEmpty()) "${nameParts?.get(0)}.${nameParts?.get(1)}" else "$name.${nameParts?.get(1)}"

        var error = true

        try {
            if (file.isEmpty) {
                throw StorageException("Failed to store empty file $filename")
            }
            if (filename.contains("..")) {
                // This is a security check
                throw StorageException(
                        "Cannot store file with relative path outside current directory $filename")
            }
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, uploadDirectory.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING)
                error = false
            }
        } catch (e: IOException) {
            throw StorageException("Failed to store file $filename", e)
        }

        return Pair(error, filename)
    }

    override fun storeMix(file: MultipartFile, name: String, folder: Path): Pair<Boolean, String> {
        val nameParts = file.originalFilename?.toLowerCase(Locale.FRENCH)?.let { StringUtils.cleanPath(it).split(".") }
        val filename = if (name.isEmpty()) "${nameParts?.get(0)}.${nameParts?.get(1)}" else "$name.${nameParts?.get(1)}"

        var error = true

        try {
            if (file.isEmpty) {
                throw StorageException("Failed to store empty file $filename")
            }
            if (filename.contains("..")) {
                // This is a security check
                throw StorageException(
                        "Cannot store file with relative path outside current directory $filename")
            }
            file.inputStream.use { inputStream ->
                Files.copy(inputStream, folder.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING)
                error = false
            }
        } catch (e: IOException) {
            throw StorageException("Failed to store file $filename", e)
        }

        return Pair(error, filename)
    }

    override fun loadAll(): Stream<Path> {
        try {
            return Files.walk(uploadDirectory, 1)
                    .filter { path -> path != uploadDirectory }
                    .map { it.relativize(uploadDirectory) }
        } catch (e: IOException) {
            throw StorageException("Failed to read stored files", e)
        }

    }

    override fun load(filename: String): Path = uploadDirectory.resolve(filename)

    override fun loadAsResource(filename: String): Resource {
        try {
            val file = load(filename)
            val resource = UrlResource(file.toUri())
            return if (resource.exists() || resource.isReadable) {
                resource
            } else {
                throw StorageFileNotFoundException(
                        "Could not read file: $filename")

            }
        } catch (e: MalformedURLException) {
            throw StorageFileNotFoundException("Could not read file: $filename", e)
        }

    }

    override fun deleteAll() {
        FileSystemUtils.deleteRecursively(uploadDirectory.toFile())
    }
}