package com.chrynan.graphqlquerybuilder

data class GraphQLQueryFragment<B : GraphQLQueryBuilder>(val builder: B.() -> Unit)