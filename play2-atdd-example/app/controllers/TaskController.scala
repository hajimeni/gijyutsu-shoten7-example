package controllers

import javax.inject.{Inject, Singleton}
import play.api.data.Form
import play.api.data.Forms._
import play.api.mvc.{AbstractController, ControllerComponents}
import services.TaskCreationService

case class TaskInput(user: String, todo: String)

@Singleton
class TaskController @Inject()(
  cc: ControllerComponents,
  service: TaskCreationService
) extends AbstractController(cc)
  with play.api.i18n.I18nSupport {

  val taskInputForm = Form(
    mapping(
      "user" -> text,
      "todo" -> text
    )(TaskInput.apply)(TaskInput.unapply)
  )

  def newPage() = Action { implicit request =>
    Ok(views.html.task.newPage(taskInputForm))
  }

  def create() = Action { implicit request =>
    taskInputForm.bindFromRequest.fold(
      errors => {
        BadRequest(views.html.task.newPage(errors))
      }
      ,
      taskInput => {
        service.create(TaskInput)
        Redirect(routes.TaskController.list())
      }
    )

    Redirect(routes.TaskController.list())
  }

  def list() = Action { implicit request =>
    Ok(views.html.task.list())
  }

}
