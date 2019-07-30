package com.chrynan.graphqlquerybuilder

inline fun <reified Q : TestRoot> query(noinline query: TestRoot.() -> Unit) =
    query(builder = TestRoot(), query = query)

fun test(builder: AvatarGraphQLQueryBuilder) {
    val comparisonFields = fragment<AvatarGraphQLQueryBuilder>() on {
        default
        extraAttr
        width
        height
    }

    query<TestRoot> {
        id

        viewer {
            id
            description
            email
            avatar {
                this..comparisonFields
            }
        }
    }
}

class TestRoot : RootGraphQLQueryBuilder() {

    val id = scalar(name = "id")

    fun viewer(builder: UserGraphQLQueryBuilder.() -> Unit) = obj(
        name = "viewer",
        objectBuilder = UserGraphQLQueryBuilder(),
        objectFieldBuilder = builder
    )
}

class UserGraphQLQueryBuilder : GraphQLQueryBuilder() {

    val id by scalar(name = "id")

    val description by scalar(name = "description")

    val email by scalar(name = "email")

    fun name(type: String) = scalarWithParameters(
        name = "name",
        parameters = listOf(param(name = "type", value = type))
    )

    fun avatar(builder: AvatarGraphQLQueryBuilder.() -> Unit) =
        obj(
            name = "avatar",
            objectBuilder = AvatarGraphQLQueryBuilder(),
            objectFieldBuilder = builder
        )
}

class AvatarGraphQLQueryBuilder : GraphQLQueryBuilder() {

    val default by scalar(name = "default")

    val extraAttr = scalar(name = "extraAttr")

    val height = scalar(name = "height")

    val width = scalar(name = "width")
}