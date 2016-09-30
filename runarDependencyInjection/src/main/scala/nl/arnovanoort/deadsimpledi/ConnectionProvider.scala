package nl.arnovanoort.deadsimpledi

abstract class ConnectionProvider {
  def apply[A](f: DB[A]):A
  
}

class MKProvider(driver: String, url:String){
  new ConnectionProvider{

    def createNewConnection(driver: String, url: String): Connection = ???

    def apply[A](f: DB[A]) = {
      val c: Connection = createNewConnection(driver, url);
      f(c)
    } 
  }
}
