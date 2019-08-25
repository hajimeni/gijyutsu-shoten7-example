package features.pages

import features.models.Training
import org.openqa.selenium.WebDriver
import org.scalatestplus.play.PortNumber
import org.scalatestplus.selenium.WebBrowser

class TrainingsPage(implicit val driver: WebDriver, portNumber: PortNumber) extends WebBrowser {
  def contains(training: Training): Boolean = {
    false
  }

}
