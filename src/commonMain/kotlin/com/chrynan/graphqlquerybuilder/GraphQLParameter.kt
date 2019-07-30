package com.chrynan.graphqlquerybuilder

data class GraphQLParameter(
    val name: String,
    val defaultValue: Any? = null,
    val value: Any? = null
)