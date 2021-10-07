package com.github.kantis

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import io.kotest.property.Exhaustive
import io.kotest.property.checkAll
import io.kotest.property.exhaustive.enum
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.mockserver.client.MockServerClient
import org.mockserver.model.Header
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpRequest.request
import org.mockserver.model.Parameter

class OnRequestTests : FunSpec(
   {
      val client = mockk<MockServerClient>(relaxed = true)

      afterEach {
         confirmVerified(client)
         clearAllMocks()
      }

      test("defaults") {
         client.onRequest { }

         verify {
            client.`when`(
               request().withMethod("GET")
                  .withPath("/")
                  .withHeaders()
                  .withPathParameters()
                  .withQueryStringParameters()
            )
         }
      }

      context("uses provided method") {
         checkAll<HttpMethod>(Exhaustive.enum()) { method ->
            client.onRequest { this.method = method }

            verify {
               client.`when`(
                  withArg<HttpRequest> { it.method shouldBe method.toString() }
               )
            }
         }
      }

      test("uses the provided path") {
         client.onRequest {
            path = "/api"
         }

         verify {
            client.`when`(
               withArg<HttpRequest> { it.path shouldBe "/api" }
            )
         }
      }

      test("uses the provided path parameters") {
         client.onRequest {
            path = "/api/{userId}"
            withPathParam("userId", "1")
         }

         verify {
            client.`when`(
               withArg<HttpRequest> {
                  it.path shouldBe "/api/{userId}"
                  it.pathParameters.entries.shouldContainExactly(Parameter("userId", "1"))
               }
            )
         }
      }

      test("uses the provided headers") {
         client.onRequest {
            withHeader("a", "1")
            withHeader("b", "2")
         }

         verify {
            client.`when`(
               withArg<HttpRequest> {
                  it.headers.entries.shouldContainExactly(
                     Header("a", "1"),
                     Header("b", "2")
                  )
               }
            )
         }
      }
   }
)
