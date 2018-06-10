package model

import javax.inject.{Inject, Singleton}

case class User(val no: Int, var id: String,var pw: String,var name: String,var name_en:String,var email: String,  var tel_no: String )

object UserMapper {
  def apply(userMap: Map[String, String]): User = {
    if(userMap.isEmpty) null
    else {
      var user_no = -1
      if(userMap.contains("user_no")) user_no = userMap("user_no").toInt

      new User(user_no, userMap("user_id"),userMap.getOrElse("user_pw",""), userMap("user_nm"),
                userMap("user_nm_en"), userMap("email"), userMap("tel_no") )
    }
  }

  def getNotEmpty(user: User): (Int, Map[String, String]) = {
    var validMap = Map[String, String]()
    if(!user.id.isEmpty) validMap += ("id" -> user.id)
    if(!user.pw.isEmpty) validMap += ("pw" -> user.pw)
    if(!user.name.isEmpty) validMap += ("name" -> user.name)
    if(!user.name_en.isEmpty) validMap += ("name_en" -> user.name_en)
    if(!user.email.isEmpty) validMap += ("email" -> user.email)
    if(!user.tel_no.isEmpty) validMap += ("tel_no" -> user.tel_no)

    (user.no, validMap)
  }
}

@Singleton
class Users @Inject()(userDAO: UserDAO) {

  def += (user: User): Unit = {
    userDAO.insert(user)
  }

  def *= (user: User): Unit = {
    userDAO.update(user)
  }

  def apply(user_no: Int): User = {
    userDAO.select(user_no)
  }

  def login(user_id: String, user_pw: String): User = {
    userDAO.secretSelect(user_id, user_pw)
  }
}
