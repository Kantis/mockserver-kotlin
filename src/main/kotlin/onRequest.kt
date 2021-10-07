package com.github.kantis

import org.mockserver.client.ForwardChainExpectation
import org.mockserver.client.MockServerClient
import org.mockserver.model.HttpRequest.request

fun MockServerClient.onRequest(builder: RequestSpec.() -> Unit): ForwardChainExpectation =
   with(RequestSpec().apply(builder)) {
      `when`(
         request()
            .withPath(path)
            .withMethod(method.toString())
            .withHeaders(headers)
            .withPathParameters(pathParameters)
            .withQueryStringParameters(queryStringParameters)
      )
   }
