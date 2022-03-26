package com.lablabla.meow.data.util

import kotlin.reflect.KClass

object Utils {
    fun <T : Any> mapToObject(map: Map<String, Any>, clazz: KClass<T>) : T {
        //Get default constructor
        val constructor = clazz.constructors.first()

        //Map constructor parameters to map values
        val args = constructor
            .parameters
            .map { it to map.get(it.name) }
            .toMap()

        //return object from constructor call
        return constructor.callBy(args)
    }
}