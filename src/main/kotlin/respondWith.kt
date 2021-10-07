package com.github.kantis

import org.mockserver.client.ForwardChainExpectation
import org.mockserver.mock.Expectation
import org.mockserver.model.HttpResponse

fun ForwardChainExpectation.respondWith(builder: ResponseSpec.() -> Unit): Array<Expectation> =
   with(ResponseSpec().apply(builder)) {
      respond(
         HttpResponse.response()
            .withStatusCode(statusCode.code())
            .withReasonPhrase(statusCode.reasonPhrase())
            .withBody(body.content, body.contentType)
      )
   }
