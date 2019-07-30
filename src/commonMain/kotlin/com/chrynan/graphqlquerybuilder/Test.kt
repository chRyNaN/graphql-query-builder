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

    val id = gqlScalar(name = "id")

    fun viewer(builder: UserGraphQLQueryBuilder.() -> Unit) = gqlObject(
        name = "viewer",
        objectBuilder = UserGraphQLQueryBuilder(),
        objectFieldBuilder = builder
    )
}

class UserGraphQLQueryBuilder : GraphQLQueryBuilder() {

    val id by gqlScalar(name = "id")

    val description by gqlScalar(name = "description")

    val email by gqlScalar(name = "email")

    fun name(type: String) = gqlScalarWithParams(
        name = "name",
        parameters = listOf(gqlParam(name = "type", value = type))
    )

    fun avatar(builder: AvatarGraphQLQueryBuilder.() -> Unit) =
        gqlObject(
            name = "avatar",
            objectBuilder = AvatarGraphQLQueryBuilder(),
            objectFieldBuilder = builder
        )
}

class AvatarGraphQLQueryBuilder : GraphQLQueryBuilder() {

    val default by gqlScalar(name = "default")

    val extraAttr = gqlScalar(name = "extraAttr")

    val height = gqlScalar(name = "height")

    val width = gqlScalar(name = "width")
}