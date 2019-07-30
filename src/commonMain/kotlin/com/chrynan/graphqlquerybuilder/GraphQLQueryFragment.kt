package com.chrynan.graphqlquerybuilder

data class GraphQLQueryFragment<B : BaseGraphQLQueryBuilder>(val builder: B.() -> Unit)