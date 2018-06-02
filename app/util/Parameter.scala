package util

import play.api.mvc.{AnyContent, Request}

object Parameter {
  def getFirst(request: Request[AnyContent])(key: String): String = {
    var queryMap:Map[String, Seq[String]] = Map()

    request.method match {
      case "GET" => queryMap = request.queryString
      case _ =>     queryMap = request.body.asFormUrlEncoded.get
    }

    queryMap.get(key) match {
      case Some(e) => e match {
        case h :: d => h
        case Seq(x) => x
        case _ => ""
      }
      case None  => ""
      case _ => ""
    }
  }
}
