package model

import scala.collection.mutable

class User(var id: String,var pw: String,var name: String,var name_en:String,var email: String,  var tel_no: String ) {
  override def toString()= {
    "{ id : "+id+" , pw : "+pw+" , name : "+name+" , name_en : "+email+" , tel_no : " + tel_no+" }"
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
