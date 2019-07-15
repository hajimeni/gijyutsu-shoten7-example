- brew のインストール
  `/usr/bin/ruby -e '$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)'`
- sbt のインストール
  `brew install sbt`
- javaのインストール
  `brew tap homebrew/cask-versions`
  `brew cask install java11`
  既に他のJavaが入っている場合はバージョン変更をする
  - `~/.bash_profile`
    ```
    if [ -f ~/.bashrc ] ; then
      . ~/.bashrc
      fi
    ```
  - ~/.bashrc
    ```
    export JAVA_HOME=`/usr/libexec/java_home -v '11'`
    PATH=${JAVA_HOME}/bin:${PATH}
    ```
  - バージョンの確認
    `java -version`
    ```
    java version '11.0.2' 2019-01-15 LTS
    Java(TM) SE Runtime Environment 18.9 (build 11.0.2+9-LTS)
    Java HotSpot(TM) 64-Bit Server VM 18.9 (build 11.0.2+9-LTS, mixed mode)
    ```
- project templateの作成
  `sbt new playframework/play-scala-seed.g8`
  ```
name [play-scala-seed]: play2-atdd-example
organization [com.example]: com.github.hajimeni
```
- 実行の確認　
  `sbt run`
    - `Welcomw to play` が表示されればOK
- 開発の準備
  - build.sbt
    `scalaVersion := '2.12.8'`
    ```
    libraryDependencies ++= Seq(
      'com.typesafe.play' %% 'play-slick' % '3.0.0',
      'org.xerial' % 'sqlite-jdbc' % '3.23.1',
      'org.flywaydb' %% 'flyway-play' % '5.3.2'
    )
    ```
  - ユーザーシナリオの確認

#####
- TODOアプリの要件
- MVP
  - ユーザーとしてタスクを記録したい。
    Scenario:
      Given 登録画面を表示する
      When '山田太郎' が 'ノック1000本' というタスクを登録する
      Then '山田太郎' のタスクに 'ノック1000本' が表示される
    Scenario:
      Given 登録画面を表示する
      When '山田太郎' が 'ノック1000本' というタスクを登録する
       And 登録画面を表示する
       And '山田太郎' が 'バッティング3時間' というタスクを登録する
      Then '山田太郎' のタスクに 'ノック1000本', 'バッティング3時間' が表示される
    Scenario:
      Given 登録画面を表示する
      When '初音ミク' が '歌唱練習2時間' というタスクを登録する
       And 登録画面を表示する
      When '山田太郎' が 'ノック1000本' というタスクを登録する
      Then '初音ミク' のタスクに '歌唱練習2時間' が表示される
       And '山田太郎' のタスクに 'ノック1000本' が表示される

    - TODO Data table Test
    - TODO Property bases Test
  - ユーザーとしてタスクを完了させたい。
    Scenario:
      Given '山田太郎' のタスクに 'ノック1000本', 'バッティング3時間' がある
      When 'ノック1000本' タスクの完了をする
      Then '山田太郎' のタスクに 'バッティング3時間' が表示される
    Scenario:
      Given '山田太郎' のタスクに 'ノック1000本', 'バッティング3時間' がある
       And '初音ミク' のタスクに '歌唱練習2時間' がある
      When '初音ミク' が登録した '歌唱練習2時間' タスクの完了をする
      Then '山田太郎' のタスクに 'ノック1000本', 'バッティング3時間' が表示される
- 追加
  - ユーザーとして自分のタスクは他人に見られたくない。
    Scenario:
      When '山田太郎' がユーザー名 'yamada' パスワード 'Abcdef123$' でユーザー登録する
      And '山田太郎' が パスワード 'Abcdef123$' でログインする
      Then 自分のタスク管理ページに '山田太郎' が表示される
    Scenario:
      When '山田太郎' が パスワード 'Abcdef123$' でユーザー登録する
      And '山田太郎' が パスワード 'Abcdef123$' でログインする

  - ユーザーとして自分のタスクの順番を並べ替えたい。
  - ユーザーとして自分のタスクを修正したい。
  - ユーザーとして自分のタスクの期限を設定したい

- シナリオをテストコードに落とし込む
  - テストコードの準備
    - 今回使うテストはブラウザーのモックを利用
    - ScalaTest
    - GivenWhenThen形式(FeatureSpec)
    - FeatureSpecBase クラスを作成

  - 1つ目のシナリオテスト
    ```
package features


class TodoRegistrationFeature extends FeatureSpecBase {

  feature("ユーザーとしてタスクを記録したい。") {
      scenario("ユーザーがタスクを1件記録する", featureTag) {
        Given("登録画面を表示する")
        When("'山田太郎' が 'ノック1000本' というタスクを登録する")
        Then("'山田太郎' のタスクに 'ノック1000本' が表示される")

        pending
      }
  }
}
    ```
  - pendingを書くことでまだ未実装であることを示す
- Givenの実装
  ```
  Given("登録画面を表示する")
  go to s"http://localhost:$port/tasks/create"
  pageTitle must be (s"タスク登録ページ")
  ```
- RedなのでGreenになる最低限の実装
  - routesファイルに追加
  ```
  GET     /tasks/create               controllers.TaskController.create
  ```
  - TaskController.create
  ```
  package controllers

import javax.inject.{Inject, Singleton}
import play.api.mvc.{AbstractController, ControllerComponents}

@Singleton
class TaskController @Inject()(cc: ControllerComponents) extends AbstractController(cc) {

  def create() = Action { implicit request: Request[AnyContent] =>
    Ok(
      """
        |<html>
        | <head>
        |   <title>タスク登録ページ</title>
        |   <body>
        |     OK
        |   </body>
        | </head>
        |</html>
      """.stripMargin).as(HTML)
  }

}
  ```
- `testOnly -- -n feature` で featureタグオンリーのテスト
- `sbt ~compile` で変更したらすぐコンパイル
- `sbt run` で画面を見つつ開発


ルール
```
Webページの場合、RESTと違い以下のようなインタフェースを提供すべきです。

役割	URL
一覧画面を表示	GET /books
詳細画面を表示	GET /books/1
追加formを表示	GET /books/new
追加アクション	POST /books
編集formを表示	GET /books/1/edit
編集アクション	POST /books/1
削除確認画面を表示（任意）	GET /books/1/delete
削除アクション	POST /books/1/delete
```
