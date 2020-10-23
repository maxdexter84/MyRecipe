package com.maxdexter.myrecipe.model



data class Ingredient(val ingredient: String = "",
                      val quantity: String = "",
                      val measure: String = "",
                      val uuid: String = "",
                      var arrMeasure: Array<Any> = arrayOf("БАНКА",
                          "ГОЛОВКА",
                          "ГРАММ",
                          "ЗУБЧИК",
                          "КИЛОГРАММ",
                          "КУСОК",
                          "ЛИТР",
                          "МИЛЛИЛИТР",
                          "НА КОНЧИКЕ НОЖА",
                          "ПО ВКУСУ",
                          "ПУЧОК",
                          "СТАКАН",
                          "СТОЛОВАЯ ЛОЖКА",
                          "ЧАЙНАЯ ЛОЖКА",
                          "ШТУКА",
                          "ЩЕПОТКА",
                          "СТЕБЕЛЬ",
                          "ДЭШ",
                          "ВЕТОЧКА")) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Ingredient

        if (ingredient != other.ingredient) return false
        if (quantity != other.quantity) return false
        if (measure != other.measure) return false
        if (uuid != other.uuid) return false
        if (!arrMeasure.contentEquals(other.arrMeasure)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = ingredient.hashCode()
        result = 31 * result + quantity.hashCode()
        result = 31 * result + measure.hashCode()
        result = 31 * result + uuid.hashCode()
        result = 31 * result + arrMeasure.contentHashCode()
        return result
    }
}