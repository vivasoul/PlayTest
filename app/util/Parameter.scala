package util

import play.api.mvc.{AnyContent, Request}

object Parameter {
  def getFirst(request: Request[AnyContent])(key: String): String = {
    request.body.asFormUrlEncoded.get.get(key) match {
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
