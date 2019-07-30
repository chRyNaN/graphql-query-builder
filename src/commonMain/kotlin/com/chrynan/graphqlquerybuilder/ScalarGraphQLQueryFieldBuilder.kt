package com.chrynan.graphqlquerybuilder

data class ScalarGraphQLQueryFieldBuilder(
    val name: String,
    val parameters: List<GraphQLParameter> = emptyList()
) {

    internal fun build(): String = buildString {
        append(name)

        for (parameter in parameters) {
            append("${parameter.name} = ${parameter.value}")
        }
    }
}