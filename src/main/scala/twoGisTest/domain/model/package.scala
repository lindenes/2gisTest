package twoGisTest.domain

import zio.http.Headers
import zio.stream.ZStream

package object model {
  case class ParsedPage(body:ZStream[Any, Throwable, Byte], header:Headers, url:String)
  case class ServerTitle(url:String, title:String)
}
