package opol.api

import scala.concurrent.Future

trait Api {

  def unlock(pass: String): Future[Boolean]
}