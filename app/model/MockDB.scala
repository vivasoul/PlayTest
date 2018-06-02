package model

import scala.collection.mutable

class User(var id: String,var pw: String,var name: String,var name_en:String,var email: String,  var tel_no: String ) {
  override def toString()= {
    "{ id : "+id+" , pw : "+pw+" , name : "+name+" , name_en : "+email+" , tel_no : " + tel_no+" }"
  }

  def update(userMap: Map[String, String]): Unit = {
    if(userMap.contains("user_id")) this.id = userMap("user_id")
    if(userMap.contains("user_nm")) this.name = userMap("user_nm")
    if(userMap.contains("user_nm_en")) this.name_en = userMap("user_nm_en")
    if(userMap.contains("email")) this.email = userMap("email")
    if(userMap.contains("tel_no")) this.tel_no = userMap("tel_no")
  }
}

object User {
  def apply(userMap: Map[String, String]): User = {
    new User(userMap("user_id"),userMap("user_pw"), userMap("user_nm"),
              userMap("user_nm_en"), userMap("email"), userMap("tel_no") )
  }
}

object MockDB {
  var user = mutable.HashMap[String, User]()
}
