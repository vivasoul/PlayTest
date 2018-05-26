package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import util._
import model._
/**
유저 가입			user.view/new 		GET
#유저 탈퇴			user.view/leave 	GET
유저 정보 수정		user.view/update	GET
유저 정보 보기		user.view/info		GET


유저 가입 기능 		user			POST		CREATE
유저 가입 탈퇴 기능 user			DELETE		DELETE
유저 정보 수정 기능	user 			PUT			UPDATE
유저 정보 보기 기능 user 			GET			READ
 */
@Singleton
class UserController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def view(page: String) = Action { implicit request: Request[AnyContent] => page match {
      case "read" => {
        val id = Parameter.getFirst(request)("user_id")
        val pw = Parameter.getFirst(request)("user_pw")
        //request.queryString
        if((MockDB.user.contains(id)) && (MockDB.user(id).pw == pw))
          Ok(views.html.user.read(MockDB.user(id)))
        else
          Unauthorized
      }
      case "update" => Ok(views.html.user.update())
      case "create" => {
        println(MockDB.user)
        Ok(views.html.user.create())
      }
      case _ => NotFound
    }
  }

  def create() = Action { implicit request: Request[AnyContent] =>

    val user = User {
      for ((k, arr) <- request.body.asFormUrlEncoded.get; v = arr match {
        case Seq(e) => e
        case Seq() => ""
      }) yield (k -> v)
    }
    MockDB.user += (user.id -> user)
    Ok(views.html.index())
  }

  def update() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.user.create())
  }

  def delete() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.user.create())
  }

  def read() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.user.create())
  }
}
