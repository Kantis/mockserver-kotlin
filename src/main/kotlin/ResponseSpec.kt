@file:Suppress("MemberVisibilityCanBePrivate", "unused")

package com.github.kantis

import org.mockserver.model.HttpStatusCode
import org.mockserver.model.MediaType

class ResponseSpec {
   data class Body(
      var content: String = "",
      var contentType: MediaType = MediaType.APPLICATION_JSON,
   )

   var body: Body = Body()
   var statusCode: HttpStatusCode = HttpStatusCode.OK_200

   // TODO: support for delay

   fun body(builder: Body.() -> Unit) {
      body = Body().apply(builder)
   }

   fun content(content: String) {
      body = Body(content)
   }

   fun content(contentProvider: () -> String) = content(contentProvider())

   fun contentOf(resourcePath: String, contentType: MediaType = MediaType.APPLICATION_JSON) {
      @Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
      body = Body(this::class.java.getResource(resourcePath).readText(), contentType)
   }
}
