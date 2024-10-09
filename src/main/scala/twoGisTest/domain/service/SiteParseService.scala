package twoGisTest.domain.service
import twoGisTest.domain.model._
import twoGisTest.domain.model.error.*
import zio.*
import zio.http.*

class SiteParseService(client:Client) extends  SiteParse {
  /**
   * Функция с побочными эффектами
   * @param url - пример http://vk.com
   * @return body -> header
   */
  override def getSite(url: String): ZIO[Scope, DomainError, ParsedPage] = for{
    decodedUrl <- ZIO.fromEither(URL.decode(url)).mapError(ex => RequestError(ex.getMessage, ex))
    response <-
      client.request(Request.get(decodedUrl))
        .mapError(ex => RequestError(ex.getMessage, ex))
    result <- response.status match
      case Status.Ok =>for{
        contentType <- ZIO.fromOption(
          response.headers.get("Content-Type")
        ).orElseFail(
          ContentError("empty header content-type", new Exception("can not parse response, empty header content-type"))
        )
        _ <-
          ZIO.when(
            !contentType.split(";").contains("text/html")
          )(ZIO.fail(
            ContentError("wrong content type", new Exception("can not parse response, content type must be text/html")))
          )
      }yield ParsedPage(response.body.asStream, response.headers, url)
      case Status.Found => getSite(response.headers.get("Location").get)
      case _ => ZIO.fail(BadStatusError("status not Ok", new Exception("parsed status is not 200")))
  }yield result

  // Костыльненько получилось конечно поиск тега, извините )
  // вроде даже чистая функция получилась
  override def getTitle(data: ParsedPage): ZIO[Any, DomainError, ServerTitle] =
    data.body.split(_ == '>'.toInt)
      .runFold(ServerTitle("", "")) { case (res, next) =>
        if (next.asString.contains("</title"))
          res.copy(
            url = data.url,
            title = next.asString.split("(?i)</title").headOption.getOrElse("empty title"))
        else
          res
      }.mapError(ex => ParseBodyError(ex.getMessage, ex))
}

object SiteParseService {
  val live =
    ZLayer.fromZIO(
      ZIO.service[Client].map(client => new SiteParseService(client)))
}
