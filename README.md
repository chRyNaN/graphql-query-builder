# graphql-query-builder

A Mutliplatform Kotlin Library to facilitate the creation of type-safe GraphQL Request Query DSLs.

**Note:** This is an older library experiment that is no longer being maintained. Please refer to the newer library: [graphkl](https://github.com/chRyNaN/graphkl).

## Library Objective

This library helps create GraphQL request strings to send to a server. It does this by providing DSL Builder Classes which wrap a `StringBuilder` and handle building a dynamic GraphQL request string in proper format. These DSL Builder Classes can be extended from custom classes to create a simple DSL for request queries.

## Library Usage

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

type Query {
    viewer: User!
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

### Queries

To perform a Query, you need a Root Query Builder Object. This Object is similar to the GraphQL `Query` type. Then, to obtain a `GraphQLQuery` object instance, use the `query` function.

**GraphQL Definition:**
```graphql
type Query {
    viewer: User!
}
```

**Kotlin Definition:**
```kotlin
class Root : RootGraphQLQueryBuilder() {

    fun viewer(builder: UserBuilder.() -> Unit) = gqlObject(name = "viewer", objectBuilder = UserBuilder(), objectFieldBuilder = builder)
}
```

**Kotlin Query:**
```kotlin
val graphQLQuery = query(Root()) {
    viewer {
        id
        name
    }
}
```

You could even create a wrapper function around the `query` function to simplify it's syntax:
```kotlin
fun query(query: Root.() -> Unit) = query(builder = Root(), query = query)

...

val graphQLQuery = query {
    viewer {
        id
        name
    }
}
```

### Mutations

To perform a Mutation, you need a Root Mutation Builder Object. This Object is similar to the GraphQL `Mutation` type. Then, to obtain a `GraphQLQuery` object instance, use the `mutation` function.

**GraphQL Definition:**
```graphql
type Mutation {
    updateName(name: String!): User!
}
```

**Kotlin Definition:**
```kotlin
class Mutation : RootGraphQLMutationBuilder() {

    fun updateName(name: String, builder: UserBuilder.() -> Unit) = gqlObject(name = "updateName", parameters = listOf(gqlParam(name = "name", value = name)), objectBuilder = UserBuilder(), objectFieldBuilder = builder)
}
```

**Kotlin Query:**
```kotlin
val graphQLMutation = mutation(Mutation()) {
    updateName(name = "New Name") {
        id
        name
    }
}
```

You could even create a wrapper function around the `mutation` function to simplify it's syntax:
```kotlin
fun mutation(query: Mutation.() -> Unit) = mutation(builder = Mutation(), query = query)

...

val graphQLMutation = mutation {
    updateName(name = "New Name") {
        id
        name
    }
}
```

### Subscriptions

To perform a Subscription, you need a Root Subscription Builder Object. This Object is similar to the GraphQL `Subscription` type. Then, to obtain a `GraphQLQuery` object instance, use the `subscription` function.

**GraphQL Definition:**
```graphql
type Subscription {
    userChanges(id: String!): User!
}
```

**Kotlin Definition:**
```kotlin
class Subscription : RootGraphQLSubscriptionBuilder() {

    fun userChanges(id: String, builder: UserBuilder.() -> Unit) = gqlObject(name = "userChanges", parameters = listOf(gqlParam(name = "id", value = id)), objectBuilder = UserBuilder(), objectFieldBuilder = builder)
}
```

**Kotlin Query:**
```kotlin
val graphQLSubscription = subscription(Subscription()) {
    userChanges(id = "Some ID") {
        id
        name
    }
}
```

You could even create a wrapper function around the `subscription` function to simplify it's syntax:
```kotlin
fun subscription(query: Subscription.() -> Unit) = subscription(builder = Subscription(), query = query)

...

val graphQLSubscription = subsciption {
    userChanges(id = "Some ID") {
        id
        name
    }
}
```

### Fragments

Basic GraphQL Fragments are supported by using the `fragment()` function:
```kotlin
val accountFragment = fragment<AccountBuilder>() on {
    email
    token
}

...

val graphQLQuery = query(Root()) {
    viewer {
        id
        name
        account {
            this..accountFragment
        }
    }
}
```
