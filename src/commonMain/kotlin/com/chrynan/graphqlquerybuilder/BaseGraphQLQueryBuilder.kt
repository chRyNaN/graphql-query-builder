package com.chrynan.graphqlquerybuilder

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

@GraphQLQueryBuilderMarker
sealed class BaseGraphQLQueryBuilder(
    val queryType: GraphQLQueryType
) : GraphQLRootInfoProvider {

    override val isRoot: Boolean
        get() = false

    val isQuery
        get() = queryType == GraphQLQueryType.QUERY

    val isMutation
        get() = queryType == GraphQLQueryType.MUTATION

    val isSubscription
        get() = queryType == GraphQLQueryType.SUBSCRIPTION

    val fieldNames = mutableSetOf<String>()

    internal var indentLevel = 1

    private val sb = StringBuilder()

    protected fun gqlParam(name: String, defaultValue: Any? = null, value: Any? = null) =
        GraphQLParameter(
            name = name,
            defaultValue = defaultValue,
            value = value
        )

    protected fun gqlScalarWithParams(name: String, parameters: List<GraphQLParameter>) {
        val fieldBuilder = ScalarGraphQLQueryFieldBuilder(name = name, parameters = parameters)
        fieldNames.add(name)
        addIndents()
        sb.append(fieldBuilder.build())
    }

    protected fun <B : BaseGraphQLQueryBuilder> gqlObject(
        name: String,
        parameters: List<GraphQLParameter> = emptyList(),
        objectBuilder: B,
        objectFieldBuilder: B.() -> Unit
    ) {
        val fieldBuilder = ObjectGraphQLQueryFieldBuilder(
            name = name,
            parameters = parameters,
            objectBuilder = objectBuilder,
            objectFieldBuilder = objectFieldBuilder,
            indentLevel = indentLevel
        )
        fieldNames.add(name)
        addIndents()
        sb.append(fieldBuilder.build())
    }

    internal open fun build() = sb.toString()

    private fun addIndents() {
        for (i in 0 until indentLevel) {
            sb.append("    ")
        }
    }

    @Suppress("ClassName")
    inner class gqlScalar(val name: String) : ReadOnlyProperty<BaseGraphQLQueryBuilder, Unit> {

        override fun getValue(thisRef: BaseGraphQLQueryBuilder, property: KProperty<*>) {
            val fieldBuilder = ScalarGraphQLQueryFieldBuilder(name = name)
            fieldNames.add(name)
            addIndents()
            sb.append(fieldBuilder.build())
        }
    }
}

open class GraphQLSubscriptionBuilder :
    BaseGraphQLQueryBuilder(queryType = GraphQLQueryType.SUBSCRIPTION)

open class GraphQLMutationBuilder :
    BaseGraphQLQueryBuilder(queryType = GraphQLQueryType.MUTATION)

open class GraphQLQueryBuilder :
    BaseGraphQLQueryBuilder(queryType = GraphQLQueryType.QUERY)