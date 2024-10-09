package twoGisTest
import zio.*
import zio.config.magnolia.deriveConfig
import zio.config.typesafe.TypesafeConfigProvider
import zio.http.Server

object ApplicationConfig {

  case class Api(host: String, port: Int)
  
  val httpConfig: ZLayer[Any, Config.Error, Server.Config] =
    ZLayer.fromZIO(
      TypesafeConfigProvider.fromResourcePath()
        .nested("api")
        .load(deriveConfig[Api])
        .map(config =>
          Server.Config.default.binding(config.host, config.port))
    )
    
}
