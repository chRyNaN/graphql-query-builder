# graphql-query-builder
A Mutliplatform Kotlin Library to facilitate the creation of type-safe GraphQL Query DSLs

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

  val id by scalar("id")
  
  val email by scalar("email")
  
  val token by scalar("token")
}

class UserBuilder : GraphQLQueryBuilder() {

  val id by scalar("id")
  
  val name by scalar("name")
  
  fun friends(count: Int, cursor: Cursor? = null) = scalarWithParameters(name = "friends", parameters = listOf(param(name = "count", value = count), param(name = "cursor", value = cursor)))
  
  fun account(builder: AccountBuilder.() -> Unit) = obj(name = "account", objectBuilder = AccountBuilder(), objectFieldBuilder = builder)
}

class Root : RootGraphQLQueryBuilder() {

  fun viewer(builder: UserBuilder.() -> Unit) = obj(name = "viewer", objectBuilder = UserBuilder(), objectFieldBuilder = builder)
}
```

Which allows you to create dynamic and type-safe queries like this:
```kotlin
query(Root()){
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
