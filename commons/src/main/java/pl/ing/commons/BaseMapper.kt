package pl.ing.commons

interface BaseMapper<in A, out B> {

    fun map(type: A?): B
}