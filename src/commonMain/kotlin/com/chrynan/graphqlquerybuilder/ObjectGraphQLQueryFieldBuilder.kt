package com.chrynan.graphqlquerybuilder

data class ObjectGraphQLQueryFieldBuilder<B : GraphQLQueryBuilder>(
    val name: String,
    val parameters: List<GraphQLParameter> = emptyList(),
    val objectBuilder: B,
    val objectFieldBuilder: (B.() -> Unit)
) {

    internal fun build(): String = buildString {
        append(name)

        for (parameter in parameters) {
            append("${parameter.name} = ${parameter.value}")
        }

        append(" ")

        append("{")
        objectFieldBuilder.invoke(objectBuilder)
        append(objectBuilder.build())
        append("}")
    }
}