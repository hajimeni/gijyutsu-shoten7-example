package features.pages

import org.openqa.selenium.WebDriver
import org.scalatestplus.play.PortNumber
import org.scalatestplus.selenium.WebBrowser

class SignInPage(implicit val driver: WebDriver, portNumber: PortNumber) extends WebBrowser {

  def signIn(userName: String): MenuPage = {
    new MenuPage
  }

  def open(): SignInPage = {
    go to s"http://localhost:${portNumber.value}/signIn"
    this
  }
}

