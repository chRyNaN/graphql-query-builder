package com.chrynan.graphqlquerybuilder

data class GraphQLQuery<Q>(
    val builder: Q,
    val query: Q.() -> Unit
) where Q : BaseRootGraphQLQueryBuilder,
        Q : BaseGraphQLQueryBuilder {

    fun toDecodedString(): String {
        query.invoke(builder)

        return builder.build()
    }

    fun toEncodedString(): String {
        // TODO
        return toDecodedString()
    }
}