package com.chrynan.graphqlquerybuilder

interface GraphQLRootInfoProvider {

    val isRoot: Boolean
}

interface BaseRootGraphQLQueryBuilder : GraphQLRootInfoProvider

open class RootGraphQLQueryBuilder : GraphQLQueryBuilder(),
    BaseRootGraphQLQueryBuilder {

    override val isRoot: Boolean
        get() = true

    override fun build() = buildString {
        append("{\n")
        append(super.build())
        append("}")
    }
}

open class RootGraphQLMutationBuilder : GraphQLMutationBuilder(),
    BaseRootGraphQLQueryBuilder {

    override val isRoot: Boolean
        get() = true
}

open class RootGraphQLSubscriptionBuilder : GraphQLSubscriptionBuilder(),
    BaseRootGraphQLQueryBuilder {

    override val isRoot: Boolean
        get() = true
}