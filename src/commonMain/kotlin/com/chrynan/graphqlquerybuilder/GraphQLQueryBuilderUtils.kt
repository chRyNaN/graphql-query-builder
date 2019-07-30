package com.chrynan.graphqlquerybuilder

fun <B : GraphQLQueryBuilder> fragment() =
    GraphQLQueryFragmentBuilder<B>()

operator fun <B : GraphQLQueryBuilder> B.rangeTo(fragment: GraphQLQueryFragment<B>) {
    fragment.builder.invoke(this)
}

fun <Q : RootGraphQLQueryBuilder> query(builder: Q, query: Q.() -> Unit): GraphQLQuery<Q> =
    GraphQLQuery(builder = builder, query = query)