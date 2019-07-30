package com.chrynan.graphqlquerybuilder

data class GraphQLQuery<Q : RootGraphQLQueryBuilder>(
    val builder: Q,
    val query: Q.() -> Unit
) {

    fun toGraphQLQueryString(): String {
        query.invoke(builder)

        return builder.build()
    }
}