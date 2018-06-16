package model

import play.api.db.Database
import javax.inject._

@Singleton
class UserDAO @Inject()(db: Database){

  //UPDATE
  def *=(user: User) :Unit = {
    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      val dataSet = UserMapper.getNotEmpty(user)
      val sql = "UPDATE tb_user SET "+
                 dataSet._2.map(x => x._1+"='"+x._2+"'").mkString(",")+
                 " WHERE no="+dataSet._1
      println(sql)
      stmt.executeUpdate(sql)
    } finally {
      conn.close()
    }
  }
  //INSERT
  def +=(user: User) :Unit = {
    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      val dataSet = UserMapper.getNotEmpty(user)
      val sql = "INSERT INTO tb_user ("+
                 dataSet._2.map(_._1).mkString(",")+
                 ") VALUES ("+dataSet._2.map("'"+_._2+"'").mkString(",")+")"
      println(sql)
      stmt.executeUpdate(sql)
    } finally {
      conn.close()
    }
  }
  //SELECT
  def apply(user_no: Int) :User = {
    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      val sql = s"SELECT * FROM tb_user WHERE no=${user_no}"
      val rs = stmt.executeQuery(sql)
      println(sql)
      var userMap = Map[String,String]()
      while (rs.next()) {
        userMap += ("user_no" -> rs.getString("no"))
        userMap += ("user_id" -> rs.getString("id"))
        userMap += ("user_nm" -> rs.getString("name"))
        userMap += ("user_nm_en" -> rs.getString("name_en"))
        userMap += ("email" -> rs.getString("email"))
        userMap += ("tel_no" -> rs.getString("tel_no"))
      }
      UserMapper(userMap)
    } finally {
      conn.close()
    }
  }
  //SELECT to login
  def login(user_id: String, user_pw: String) :User = {
    val conn = db.getConnection()
    try {
      val stmt = conn.createStatement
      val sql = s"SELECT * FROM tb_user WHERE id='${user_id}' AND pw='${user_pw}'"
      val rs = stmt.executeQuery(sql)
      println(sql)
      var userMap = Map[String,String]()
      while (rs.next()) {
        userMap += ("user_no" -> rs.getString("no"))
        userMap += ("user_id" -> rs.getString("id"))
        userMap += ("user_nm" -> rs.getString("name"))
        userMap += ("user_nm_en" -> rs.getString("name_en"))
        userMap += ("email" -> rs.getString("email"))
        userMap += ("tel_no" -> rs.getString("tel_no"))
      }
      UserMapper(userMap)
    } finally {
      conn.close()
    }
  }
}
