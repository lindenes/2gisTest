package twoGisTest.application.service

import zio.*
import twoGisTest.application.dto.*
import twoGisTest.application.dto.error.ServerTitleError
import twoGisTest.domain.service.SiteParse
import zio.stream.ZSink

object AppHandler {

    def parseSiteData(urls:List[String]):ZIO[Scope & SiteParse, ServerTitleError, List[ServerTitleDTO]] = for{
        sites <- ZIO.foreachPar(urls)(SiteParse.getSite).mapError(er => ServerTitleError("get site info error", er.message, er.ex.getMessage))
        site <- ZIO.foreachPar(sites)(SiteParse.getTitle).mapError(er => ServerTitleError("parse body error", er.message, er.ex.getMessage))
    }yield site.map(s => ServerTitleDTO(s.url, s.title))

    
    
    
    
    
    //Пытался ниже все через потоки сделать, но как то не понял как грамотно реализовать прокидывания url для красивого респонса
    //Верхний метод по памяти вышел даже чуть лучше почему то по скорости верхний тоже быстрее

    //    def parseSiteData(urls:List[String]):ZIO[Scope & SiteParse, ServerTitleError, List[ServerTitleDTO]] = for{
    //        site <- ZStream.fromIterable(urls)
    //          .mapZIOPar(4)(SiteParse.getSite).mapError(er => ServerTitleError("get site info error", er.message, er.ex.getMessage))
    //          .flatMap(_.body)
    //          .split(_ == '>'.toInt)
    //          .runFold(List.empty[ServerTitleDTO]){case (res, next) =>
    //            if (next.asString.contains("</title"))
    //              ServerTitleDTO(url = "", title = next.asString.split("(?i)</title").headOption.getOrElse("empty title")) :: res
    //            else
    //              res
    //          }.mapError{
    //            case er:ServerTitleError => er
    //            case _ => ServerTitleError("get site info error", "work with data error", "")
    //          }
    //    }yield site
}
