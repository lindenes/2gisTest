package twoGisTest.application.service

import twoGisTest.application.endpoint.AppEndpoint

object RouteImpl {
  
  val routeImpl = List(
    AppEndpoint.parseEndpoint implement AppHandler.parseSiteData
  )
  
}
