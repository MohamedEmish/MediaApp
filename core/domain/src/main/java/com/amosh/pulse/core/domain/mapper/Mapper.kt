package com.amosh.pulse.core.domain.mapper

/**
 * Mapper interface
 */
interface Mapper<I, O> {

    fun from(i: I, parentType: String? = null): O

    fun to(o: O): I

    fun fromList(list: List<I>?, parentType: String? = null): List<O> {
        return list?.mapNotNull { from(it, parentType) } ?: listOf()
    }

    fun toList(list: List<O>?): List<I> {
        return list?.mapNotNull { to(it) } ?: listOf()
    }
}