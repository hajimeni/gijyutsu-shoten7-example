package features.pages

import org.openqa.selenium.WebDriver
import org.scalatestplus.play.PortNumber
import org.scalatestplus.selenium.WebBrowser

class MenuPage(implicit val driver: WebDriver, portNumber: PortNumber) extends WebBrowser {

  def goToTrainingsNew(): TrainingsNewPage = {
//    click on linkText("トレーニングの記録")
    new TrainingsNewPage
  }

}
