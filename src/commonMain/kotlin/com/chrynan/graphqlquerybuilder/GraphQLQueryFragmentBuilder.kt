package com.chrynan.graphqlquerybuilder

class GraphQLQueryFragmentBuilder<B : BaseGraphQLQueryBuilder> {

    infix fun on(builder: B.() -> Unit): GraphQLQueryFragment<B> =
        GraphQLQueryFragment(builder = builder)
}