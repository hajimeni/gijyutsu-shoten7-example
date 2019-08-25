package features.pages

import features.models.Training
import org.openqa.selenium.WebDriver
import org.scalatestplus.play.PortNumber
import org.scalatestplus.selenium.WebBrowser

class TrainingsNewPage(implicit val driver: WebDriver, portNumber: PortNumber) extends WebBrowser {
  def record(training: Training): TrainingsPage = {
    new TrainingsPage
  }

}
