package twoGisTest

import twoGisTest.application.service.RouteImpl
import zio.http.Routes

package object application {

  def getRoutes = Routes.fromIterable(RouteImpl.routeImpl)

}
