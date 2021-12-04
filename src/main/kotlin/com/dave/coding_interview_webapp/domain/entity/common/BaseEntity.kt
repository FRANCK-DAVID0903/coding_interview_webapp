package com.dave.coding_interview_webapp.domain.entity.common

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import com.maxmind.geoip2.DatabaseReader
import com.maxmind.geoip2.exception.GeoIp2Exception
import com.maxmind.geoip2.record.Country
import org.slf4j.LoggerFactory
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.io.File
import java.io.IOException
import java.net.InetAddress
import java.util.*
import javax.persistence.*
import javax.servlet.http.HttpServletRequest

/**
 * @author alexwilfriedo
 */
@MappedSuperclass
@JsonIgnoreProperties(value = ["createdIp", "updatedIp", "createdCountry", "updatedCountry", "createdCountryISO", "updatedCountryISO"])
abstract class BaseEntity : AuditableEntity<String>() {

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = -1

    @field:Column(name = "created_ip")
    open var createdIp: String? = null

    @field:Column(name = "updated_ip")
    open var updatedIp: String? = null

    @field:Column(name = "created_country")
    open var createdCountry: String? = null

    @field:Column(name = "updated_country")
    open var updatedCountry: String? = null

    @field:Column(name = "created_country_iso")
    open var createdCountryISO: String? = null

    @field:Column(name = "updated_country_iso")
    open var updatedCountryISO: String? = null

    companion object {

        private val LOGGER = LoggerFactory.getLogger(BaseEntity::class.java)

        private var databaseReader: DatabaseReader? = null
    }

    fun toJSON(): String {
        try {
            val result = ObjectMapper().writeValueAsString(this)
            return result.replace("'", "\\\'")
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
        }

        return ""
    }

    private val clientCountry: Optional<Country>
        get() {
            var country: Country? = null

            if (databaseReader == null) {
                val resource = File("src/main/resources/geolite2-country.mmdb")
                try {
                    databaseReader = DatabaseReader.Builder(resource).build()
                } catch (e: IOException) {
                    LOGGER.error(
                        String.format(
                            "DatabaseReader Construction --> %s",
                            Arrays.toString(e.stackTrace)
                        )
                    )
                }

            }

            if (databaseReader != null) {
                try {
                    val countryResponse = databaseReader!!.country(InetAddress.getByName(createdIp))
                    country = countryResponse.country
                } catch (e: IOException) {
                    LOGGER.error(
                        String.format(
                            "IOException | GeoIp2Exception --> %s",
                            Arrays.toString(e.stackTrace)
                        )
                    )
                } catch (e: GeoIp2Exception) {
                    LOGGER.error(String.format("IOException | GeoIp2Exception --> %s", Arrays.toString(e.stackTrace)))
                }

            }

            return Optional.ofNullable(country)
        }

    @PrePersist
    protected open fun onPrePersist() {
        try {
            val requestOptional = Optional.of(
                (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes)
                    .request
            )

            requestOptional.ifPresent { request ->
                updatedIp = getClientIP(request)
                createdIp = updatedIp
                clientCountry
                    .ifPresent { country ->
                        updatedCountry = country.name
                        createdCountry = updatedCountry
                        updatedCountryISO = country.isoCode
                        createdCountryISO = updatedCountryISO
                    }
            }
        } catch (e: IllegalStateException) {
            LOGGER.error(
                String.format("IllegalStateException --> %s", Arrays.toString(e.stackTrace))
            )
        }

    }

    @PreUpdate
    protected open fun onPreUpdate() {
        try {
            val requestOptional = Optional.of(
                (RequestContextHolder.currentRequestAttributes() as ServletRequestAttributes)
                    .request
            )

            requestOptional.ifPresent { request ->
                updatedIp = request.remoteAddr
                clientCountry
                    .ifPresent { country ->
                        updatedCountry = country.name
                        updatedCountryISO = country.isoCode
                    }
            }
        } catch (e: IllegalStateException) {
            LOGGER.error("IllegalStateException / onPreUpdate", Arrays.toString(e.stackTrace))
        }

    }

    private fun getClientIP(request: HttpServletRequest): String {
        val xfHeader = request.getHeader("X-Forwarded-For") ?: return request.remoteAddr
        return xfHeader.split(",".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]
    }
}
