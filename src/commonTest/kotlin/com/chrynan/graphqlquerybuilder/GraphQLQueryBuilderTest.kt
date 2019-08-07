package com.chrynan.graphqlquerybuilder

import kotlin.test.Test
import kotlin.test.assertEquals

class GraphQLQueryBuilderTest {

    @Test
    fun queryShouldMapToDecodedStringCorrectly() {
        val comparisonFields = fragment<AvatarGraphQLQueryBuilder>() on {
            default
            extraAttr
            width
            height
        }

        val result = query {
            id

            viewer {
                id
                description
                email
                avatar {
                    this..comparisonFields
                }
            }
        }.toDecodedString()

        val expectedResult = """
            {
                id
                viewer {
                    id
                    description
                    email
                    avatar {
                        default
                        extraAttr
                        width
                        height
                    }
                }
            }
        """.trimIndent()

        println("\n")
        println("expectedResult = \n $expectedResult")
        println("\n")
        println("result = \n $result")

        assertEquals(expectedResult, result)
    }
}