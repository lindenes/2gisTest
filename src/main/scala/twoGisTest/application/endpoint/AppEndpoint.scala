package twoGisTest.application.endpoint

import twoGisTest.application.dto.ServerTitleDTO
import twoGisTest.application.dto.error.ServerTitleError
import zio.http.*
import zio.http.endpoint.*
import zio.http.codec.{HttpCodec, PathCodec}

object AppEndpoint {

  val parseEndpoint =
    Endpoint(RoutePattern.GET / "parsePage")
      .query(HttpCodec.query[List[String]]("url"))
      .out[List[ServerTitleDTO]]
      .outError[ServerTitleError](Status.InternalServerError)

}
