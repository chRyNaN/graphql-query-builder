package com.chrynan.graphqlquerybuilder

data class ScalarGraphQLQueryFieldBuilder(
    val name: String,
    val parameters: List<GraphQLParameter> = emptyList()
) {

    internal fun build(): String = buildString {
        append(name)

        if (parameters.isNotEmpty()) {
            append("(")

            parameters.forEachIndexed { index, parameter ->
                append("${parameter.name} = ${parameter.value}")

                if (index < parameters.size - 1) {
                    append(", ")
                }
            }

            append(")")
        }

        append("\n")
    }
}