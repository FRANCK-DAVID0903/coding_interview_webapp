package com.b2i.tontine.utils.helper

import java.text.DecimalFormat

object CurrencyHelper {

    private const val CURRENCY = "XOF"

    private const val CURRENCY_FORMATTER = "#,###"

    fun format(price: Double?): String =
            "${DecimalFormat(CURRENCY_FORMATTER).format(price ?: 0.0).replace(
                    ",",
                    " "
            )} $CURRENCY"

    fun formatWithoutCurrency(price: Double?) =
            DecimalFormat(CURRENCY_FORMATTER).format(price ?: 0.0).replace(
                    ",",
                    " "
            )

    fun formatForReport(price: Double?) =
            DecimalFormat(CURRENCY_FORMATTER).format(price ?: 0.0).replace(
                    ",",
                    ""
            )
}