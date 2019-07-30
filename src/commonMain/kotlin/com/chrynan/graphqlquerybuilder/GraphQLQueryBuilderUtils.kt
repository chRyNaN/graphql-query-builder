package com.chrynan.graphqlquerybuilder

fun <B : BaseGraphQLQueryBuilder> fragment() =
    GraphQLQueryFragmentBuilder<B>()

operator fun <B : BaseGraphQLQueryBuilder> B.rangeTo(fragment: GraphQLQueryFragment<B>) {
    fragment.builder.invoke(this)
}

fun <Q : RootGraphQLQueryBuilder> query(builder: Q, query: Q.() -> Unit): GraphQLQuery<Q> =
    GraphQLQuery(builder = builder, query = query)

fun <M : RootGraphQLMutationBuilder> mutation(builder: M, query: M.() -> Unit): GraphQLQuery<M> =
    GraphQLQuery(builder = builder, query = query)

fun <S : RootGraphQLSubscriptionBuilder> subscription(builder: S, query: S.() -> Unit): GraphQLQuery<S> =
    GraphQLQuery(builder = builder, query = query)