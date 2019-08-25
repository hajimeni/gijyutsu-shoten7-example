package features


class TodoRegistrationFeature extends FeatureSpecBase {

  feature("ユーザーとしてタスクを記録したい。") {
      scenario("ユーザーがタスクを1件記録する", featureTag) {
        Given("登録画面を表示する")
        go to s"http://localhost:$port/tasks/new"
        pageTitle must be (s"タスク登録ページ")

        When("'山田太郎' が 'ノック1000本' というタスクを登録する")
        textField("user").value = "山田太郎"
        textField("todo").value = "ノック1000本"
        submit()

        Then("'山田太郎' のタスクに 'ノック1000本' が表示される")
        currentUrl must be (s"http://localhost:$port/tasks")
//        pageSource.contains("ノック1000本") must be (true)
//
//        findAll(".training_list > .menu").toArray.head.text  must be (s"自重スクワット")
//        findAll(".training_list > .times").toArray.head.text  must be (s"20")
//        findAll(".training_list > .set_count").toArray.head.text  must be (s"3")
//
//        pending
      }
  }
}
