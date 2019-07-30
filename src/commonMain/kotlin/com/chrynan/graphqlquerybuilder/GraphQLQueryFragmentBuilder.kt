package com.chrynan.graphqlquerybuilder

class GraphQLQueryFragmentBuilder<B : GraphQLQueryBuilder> {

    infix fun on(builder: B.() -> Unit): GraphQLQueryFragment<B> =
        GraphQLQueryFragment(builder = builder)
}