package twoGisTest

import zio.*
import zio.http.*
import twoGisTest.application.getRoutes
import twoGisTest.domain.service.SiteParseService
import zio.http.netty.NettyConfig
import zio.http.netty.client.NettyClientDriver

object Main extends ZIOAppDefault {
  
  def run =
    (
      Server.install(getRoutes).flatMap(port => ZIO.log("server start on port " + port)) *> ZIO.never
    ).provide(
      Server.customized,
      ApplicationConfig.httpConfig,
      ZLayer.succeed(NettyConfig.default),
      Client.customized,
      NettyClientDriver.live,
      DnsResolver.default,
      ZLayer.succeed(ZClient.Config.default.ssl(ClientSSLConfig.Default)),
      Scope.default,
      SiteParseService.live
    )
    
}