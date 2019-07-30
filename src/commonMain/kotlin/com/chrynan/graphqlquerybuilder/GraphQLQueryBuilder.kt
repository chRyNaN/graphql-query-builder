package com.chrynan.graphqlquerybuilder

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@GraphQLQueryBuilderMarker
open class GraphQLQueryBuilder {

    private val sb = StringBuilder()

    private val fieldNames = mutableSetOf<String>()

    fun param(name: String, defaultValue: Any? = null, value: Any? = null) =
        GraphQLParameter(
            name = name,
            defaultValue = defaultValue,
            value = value
        )

    fun scalarWithParameters(name: String, parameters: List<GraphQLParameter>) {
        val fieldBuilder = ScalarGraphQLQueryFieldBuilder(name = name, parameters = parameters)
        fieldNames.add(name)
        sb.append(fieldBuilder.build())
    }

    fun <B : GraphQLQueryBuilder> obj(
        name: String,
        parameters: List<GraphQLParameter> = emptyList(),
        objectBuilder: B,
        objectFieldBuilder: B.() -> Unit
    ) {
        val fieldBuilder = ObjectGraphQLQueryFieldBuilder(
            name = name,
            parameters = parameters,
            objectBuilder = objectBuilder,
            objectFieldBuilder = objectFieldBuilder
        )
        fieldNames.add(name)
        sb.append(fieldBuilder.build())
    }

    internal fun build() = sb.toString()

    @Suppress("ClassName")
    inner class scalar(val name: String) : ReadOnlyProperty<GraphQLQueryBuilder, Unit> {

        override fun getValue(thisRef: GraphQLQueryBuilder, property: KProperty<*>) {
            val fieldBuilder = ScalarGraphQLQueryFieldBuilder(name = name)
            fieldNames.add(name)
            sb.append(fieldBuilder.build())
        }
    }
}