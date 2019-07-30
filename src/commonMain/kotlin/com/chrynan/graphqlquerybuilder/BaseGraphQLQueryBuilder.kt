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

    private val sb = StringBuilder()

    fun gqlParam(name: String, defaultValue: Any? = null, value: Any? = null) =
        GraphQLParameter(
            name = name,
            defaultValue = defaultValue,
            value = value
        )

    fun gqlScalarWithParams(name: String, parameters: List<GraphQLParameter>) {
        val fieldBuilder = ScalarGraphQLQueryFieldBuilder(name = name, parameters = parameters)
        fieldNames.add(name)
        sb.append(fieldBuilder.build())
    }

    fun <B : BaseGraphQLQueryBuilder> gqlObject(
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
    inner class gqlScalar(val name: String) : ReadOnlyProperty<BaseGraphQLQueryBuilder, Unit> {

        override fun getValue(thisRef: BaseGraphQLQueryBuilder, property: KProperty<*>) {
            val fieldBuilder = ScalarGraphQLQueryFieldBuilder(name = name)
            fieldNames.add(name)
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