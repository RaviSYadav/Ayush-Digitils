package com.payment.ayushdigitils.vo

open class EnumCompanion<T, V>(private val valueMap: Map<T, V>) {
    fun fromInt(type: T) = valueMap[type]
}

/**
 * Mappings of section type to its integer variant
 * 1. Show list
 * 2. Episode List // mixed episodes
 * 3. Category List
 * 4. Channel List
 * 5. Shows in Category
 * 6. Shows in Channel
 * 7. Main Banner
 * 8. Banner
 */
enum class SectionType(val value: Int) {
    FINANCE(1),
    RECHARGE(2),
    TRAVELS(3),
    ChannelList(4),
    CategoryShows(5),
    ChannelShows(6),
    MainBanner(7),
    Banner(8),
    ContinuePlay(9),
    SPECIAL(11);

    companion object :
        EnumCompanion<Int, SectionType>(SectionType.values().associateBy(SectionType::value))
}