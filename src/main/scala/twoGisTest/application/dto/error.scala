package twoGisTest.application.dto

import zio.schema.{DeriveSchema, Schema}

object error {
  
  case class ServerTitleError(title:String, detail:String, serverError:String)
  
  object ServerTitleError{
    implicit val schema: Schema[ServerTitleError] = DeriveSchema.gen
  }
  
}
