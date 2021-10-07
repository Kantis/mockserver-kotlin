package com.github.kantis

import org.mockserver.model.Header
import org.mockserver.model.Parameter

class RequestSpec {

   var method: HttpMethod = HttpMethod.GET
   var path: String = "/"
   var headers: MutableList<Header> = mutableListOf()
   var pathParameters: MutableList<Parameter> = mutableListOf()
   var queryStringParameters: MutableList<Parameter> = mutableListOf()

   fun withHeader(name: String, value: String) = headers.add(Header(name, value))
   fun withHeader(name: String, vararg value: String) = headers.add(Header(name, value.toList()))

   fun withPathParam(name: String, value: String) = pathParameters.add(Parameter(name, value))
   fun withPathParam(name: String, vararg value: String) = pathParameters.add(Parameter(name, value.toList()))

   fun withQueryParam(name: String, value: String) = queryStringParameters.add(Parameter(name, value))
   fun withQueryParam(name: String, vararg value: String) = queryStringParameters.add(Parameter(name, value.toList()))

   fun get(path: String) {
      this.method = HttpMethod.GET
      this.path = path
   }

   fun put(path: String) {
      this.method = HttpMethod.PUT
      this.path = path
   }

   fun post(path: String) {
      this.method = HttpMethod.POST
      this.path = path
   }

   fun patch(path: String) {
      this.method = HttpMethod.PATCH
      this.path = path
   }

   fun delete(path: String) {
      this.method = HttpMethod.DELETE
      this.path = path
   }
}
