package nl.arnovanoort.deadsimpledi

/**
 * Created by temmink on 9/21/16.
 */

case class Statement(query: String){
  def setString(i: Int, pwd: String) = ???
  def executeUpdate = ???
  def executeQuery[A]: String = ???
  def close = ???

}
class Connection{
  def prepareStatement(s: String):Statement = ???
}


case class DB[A](g: Connection => A){
  def apply(c: Connection) = g(c)
  def map[B]    (f: A => B)    : DB[B] = DB(c =>f(g(c)))

  def flatMap[B](h: A => DB[B]): DB[B] = DB(c => h(g(c))(c))


}
object DB{
  def pure[A](a:A) = DB(c => a)
}

// did you forget to wrap return value for map and flatmap in a db instance
// in a db instance
class User {
  def setUserPwdOld(id:String, pwd: String): Connection => Unit = c => {
    val stmt:Statement = c.prepareStatement("kupdate users set pwd = ? where id = ?")
    stmt.setString(1,pwd)
    stmt.setString(2,pwd)
    stmt.executeUpdate
    stmt.close
  }


  def setUserPwd(id:String, pwd: String): DB[Boolean] = DB{ c =>
    val stmt:Statement = c.prepareStatement("update users set pwd = ? where id = ?")
    stmt.setString(1,pwd)
    stmt.setString(2,pwd)
    stmt.executeUpdate
    stmt.close
    true
  }

  def changePassword(userId: String, oldPwd:String, newPwd:String): DB[Boolean] = {
    for{
      password <- getUserPwd(userId)
      result   <- if(password == oldPwd){
        setUserPwd(userId, newPwd)
      }else{
        DB.pure(false)
      }
    }yield(result)
  }

  def getUserPwd[A](userId: String):DB[String] = DB(c => {
    val statement: Statement = c.prepareStatement("query to get userpwd")
    statement.executeQuery
  })

  val a: DB[Boolean] = setUserPwd("userId", "newPass" )
  a(new Connection)

  val intToString: Int=>String = i => i.toString

  val intDb:DB[Int] = DB{c=> 1 }
  val stringDb: DB[String] = intDb.map(intToString)
}

