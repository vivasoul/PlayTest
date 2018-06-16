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
class UserController @Inject()(userDAO: UserDAO, cc: ControllerComponents) extends AbstractController(cc) {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def view(page: String) = Action { implicit request: Request[AnyContent] => page match {
      case "read" =>
          println(request.session)
          request.session.get("user_no") match {
            case Some(x) => Ok(views.html.user.read(userDAO(x.toInt)))
            case _ => Unauthorized
          }
      case "update" =>
        request.session.get("user_no") match {
          case Some(x) => Ok(views.html.user.update(userDAO(x.toInt)))
          case _ => Unauthorized
        }
      case "create" =>
        //println(Users.user)
        Ok(views.html.user.create())
      case _ => NotFound
    }
  }



  def login() = Action { implicit request: Request[AnyContent] =>
    val id = Parameter.getFirst(request)("user_id")
    val pw = Parameter.getFirst(request)("user_pw")
    //request.queryString
    userDAO.login(id, pw) match {
      case x: User => {
        Redirect("/main").withSession (
          request.session + ("user_no" -> x.no.toString)
        )
      }
      case _ => Unauthorized
    }
  }

  def logout() = Action { implicit request: Request[AnyContent] =>
    Redirect("/").withNewSession
  }

  def create() = Action { implicit request: Request[AnyContent] =>

    val user = UserMapper {
      for ((k, arr) <- request.body.asFormUrlEncoded.get; v = arr match {
        case Seq(e) => e
        case Seq() => ""
      }) yield (k -> v)
    }
    userDAO += user
    Redirect("/main").withSession(
      request.session + ("user_no" -> user.no.toString)
    )
  }

  def update() = Action { implicit request: Request[AnyContent] =>
    val user = UserMapper {
      for ((k, arr) <- request.body.asFormUrlEncoded.get; v = arr match {
        case Seq(e) => e
        case Seq() => ""
      }) yield (k -> v)
    }

    userDAO *= user
    Ok
  }

  def delete() = Action { implicit request: Request[AnyContent] =>
    Ok
  }

  def read() = Action { implicit request: Request[AnyContent] =>
    Ok
  }
}
