# graphql-query-builder
A Mutliplatform Kotlin Library to facilitate the creation of type-safe GraphQL Query DSLs.

The following GraphQL Schema:
```graphql
type Account {
  id: ID!
  email: String!
  token: String!
}

type User {
  id: ID!
  name: String
  friends(count: Int!, cursor: Cursor): [String!]
  account: Account!
}
```

Could be written using this library like so:
```kotlin
typealias Cursor = String

class AccountBuilder : GraphQLQueryBuilder() {

  val id by gqlScalar("id")
  
  val email by gqlScalar("email")
  
  val token by gqlScalar("token")
}

class UserBuilder : GraphQLQueryBuilder() {

  val id by gqlScalar("id")
  
  val name by gqlScalar("name")
  
  fun friends(count: Int, cursor: Cursor? = null) = gqlScalarWithParams(name = "friends", parameters = listOf(gqlParam(name = "count", value = count), gqlParam(name = "cursor", value = cursor)))
  
  fun account(builder: AccountBuilder.() -> Unit) = gqlObject(name = "account", objectBuilder = AccountBuilder(), objectFieldBuilder = builder)
}

class Root : RootGraphQLQueryBuilder() {

  fun viewer(builder: UserBuilder.() -> Unit) = gqlObject(name = "viewer", objectBuilder = UserBuilder(), objectFieldBuilder = builder)
}
```

Which allows you to create dynamic and type-safe queries in Kotlin like this:
```kotlin
val graphQLQuery = query(Root()){
  viewer {
    id
    name
    friends(count = 10)
    account {
      id
      email
      token
    }
  }
}
```
