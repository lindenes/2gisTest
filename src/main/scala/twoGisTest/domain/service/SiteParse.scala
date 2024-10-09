package twoGisTest.domain.service

import twoGisTest.domain.model._
import twoGisTest.domain.model.error.DomainError
import zio.{Scope, ZIO}

trait SiteParse {
  def getSite(url: String):ZIO[Scope, DomainError, ParsedPage]
  def getTitle(data:ParsedPage):ZIO[Any, DomainError, ServerTitle]
}
object SiteParse{
  /**
   * Функция с побочными эффектами
   * @param url - пример http://vk.com
   * @return body -> header
   */
  def getSite(url:String) =
    ZIO.serviceWithZIO[SiteParse](_.getSite(url))
    
  def getTitle(data:ParsedPage) =
    ZIO.serviceWithZIO[SiteParse](_.getTitle(data))
}