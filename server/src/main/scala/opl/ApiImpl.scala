package opl

import opl.api.Api

import scala.concurrent.Future

class ApiImpl extends Api {

  override def unlock(pass: String): Future[Boolean] = {
    Future.successful(false)
  }
}