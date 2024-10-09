package twoGisTest.domain.model

object error {
  sealed trait DomainError{
    val message:String
    val ex:Throwable
  }
  case class RequestError(message:String, ex:Throwable) extends DomainError
  case class ContentError(message:String, ex:Throwable) extends DomainError
  case class BadStatusError(message:String, ex:Throwable) extends DomainError
  case class ParseBodyError(message:String, ex:Throwable) extends DomainError
}
