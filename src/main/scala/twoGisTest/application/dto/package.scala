package twoGisTest.application

import zio.schema.{DeriveSchema, Schema}

package object dto {

  case class ServerTitleDTO(url:String, title:String)
  
  object ServerTitleDTO{
    implicit val schema: Schema[ServerTitleDTO] = DeriveSchema.gen
  }
  
}
