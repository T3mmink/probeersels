/**
 * Created by temmink on 16-10-15.
 */

case class CallMessage(id: Long)
val callMessage = new CallMessage(1)

trait FncpRepositoryComponent {
  val fncpRepository: FncpRepository

  trait FncpRepository{
    def save(callMessage: CallMessage): Unit;
    def get(id:Long) : CallMessage
  }

}

trait FncpRepositoryComponentImpl extends FncpRepositoryComponent {
  val fncpRepository = new FncpRepository{
    def save(callMessage: CallMessage) = println("saving callMessage")
    def get(id: Long) = callMessage
  }
}

trait FncpServiceComponent{

  val fncpService: FncpService;

  trait FncpService{
    def saveCallMessage(callMessage:CallMessage)
    def getCallMessage(id: Long)
  }
}

trait FncpServiceComponentImpl extends FncpServiceComponent with FncpRepositoryComponent{
//  this: =>

  val fncpService = new FncpServiceImpl()

  class FncpServiceImpl extends FncpService{
    def saveCallMessage(callMessage:CallMessage) = {
      fncpRepository.save(callMessage)
    }

    def getCallMessage(id: Long) = {
      fncpRepository.get(id)
    }
  }
}

object FncpRegistry extends FncpServiceComponentImpl with FncpRepositoryComponentImpl{
  val service = fncpService
}

class FncpClient  {
  def createAndSaveCallMessage = {
    FncpRegistry.fncpService.saveCallMessage(callMessage)
    FncpRegistry.fncpService.getCallMessage(1)
  }
}
