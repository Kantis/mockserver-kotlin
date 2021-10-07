package com.github.kantis

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.confirmVerified
import io.mockk.mockk
import io.mockk.verify
import org.mockserver.client.ForwardChainExpectation
import org.mockserver.model.HttpResponse
import org.mockserver.model.HttpStatusCode
import org.mockserver.model.MediaType

class RespondWithTests : FunSpec(
   {
      val chain = mockk<ForwardChainExpectation>(relaxed = true)

      afterEach {
         confirmVerified(chain)
         clearAllMocks()
      }

      test("defaults") {
         chain.respondWith {}

         verify {
            chain.respond(
               withArg<HttpResponse> {
                  it.body.contentType shouldBe MediaType.APPLICATION_JSON.toString()
                  it.body.value shouldBe ""
                  it.statusCode shouldBe HttpStatusCode.OK_200.code()
                  it.reasonPhrase shouldBe HttpStatusCode.OK_200.reasonPhrase()
               }
            )
         }
      }

      test("uses provided status") {
         chain.respondWith {
            statusCode = HttpStatusCode.BAD_REQUEST_400
         }

         verify {
            chain.respond(
               withArg<HttpResponse> {
                  it.statusCode shouldBe 400
                  it.reasonPhrase shouldBe "Bad Request"
               }
            )
         }
      }

      test("uses provided body") {
         chain.respondWith {
            body {
               content = "foo"
               contentType = MediaType.APPLICATION_BINARY
            }
         }

         verify {
            chain.respond(
               withArg<HttpResponse> {
                  it.body.value shouldBe "foo"
                  it.body.contentType shouldBe MediaType.APPLICATION_BINARY.toString()
               }
            )
         }
      }
   }
)
