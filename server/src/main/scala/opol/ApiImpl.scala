package opol

import opol.api.Api

import scala.concurrent.Future

class ApiImpl extends Api {

  override def unlock(pass: String): Future[Boolean] = {

    Future.successful(false)
  }
}
