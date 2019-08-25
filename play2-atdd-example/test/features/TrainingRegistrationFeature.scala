package features

import features.models.Training
import features.pages.SignInPage

class TrainingRegistrationFeature extends FeatureSpecBase {
  feature("トレーニングの記録") {
    scenario("ジム会員としてトレーニングを記録する", developingTag) {
      info("""
          | ジム会員として自分のトレーニングした結果を記録したい
        """.stripMargin)

      Given("ジム会員 hibiki としてアプリケーションを使う")
        val signInPage = new SignInPage
        val menuPage = signInPage.signIn("hibiki")

      And("トレーニングの内容を記録したい")
        val trainingsNewPage = menuPage.goToTrainingsNew()

      When("自重スクワット を 20 回 3 セット、 2019年08月03日 に実施したことを記録する")
        val trainingsPage = trainingsNewPage.record(Training("自重スクワット", 20, 3))

      Then("自分のトレーニング記録一覧に 自重スクワット を 20 回 3 セット 2019年08月03日 に実施 が表示される")
        trainingsPage.contains(Training("自重スクワット", 20, 3))

      //pending
    }
  }
}
