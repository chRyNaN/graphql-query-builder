package com.chrynan.graphqlquerybuilder

data class ObjectGraphQLQueryFieldBuilder<B : BaseGraphQLQueryBuilder>(
    val name: String,
    val parameters: List<GraphQLParameter> = emptyList(),
    val objectBuilder: B,
    val objectFieldBuilder: (B.() -> Unit),
    val indentLevel: Int = 1
) {

    internal fun build(): String = buildString {
        append(name)

        for (parameter in parameters) {
            append("${parameter.name} = ${parameter.value}")
        }

        append(" {\n")
        objectBuilder.indentLevel = indentLevel + 1
        objectFieldBuilder.invoke(objectBuilder)
        append(objectBuilder.build())
        for (i in 0 until indentLevel) {
            append("    ")
        }
        append("}\n")
    }
}