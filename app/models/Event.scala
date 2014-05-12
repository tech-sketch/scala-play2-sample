package models

import play.api.db.DB
import play.api.Play.current
import scala.slick.driver.H2Driver.simple._
import java.sql.Date
import models.Tables._

object Events {

  /** データベースコネクション */
  val database = Database.forDataSource(DB.getDataSource())

  /** 登録 */
  def create(e: EventRow) = database.withTransaction { implicit session: Session =>
    Event.insert(e)
  }

  /** 検索 */
  def find(eventId: String, eventNm: String, eventDateFrom: Option[Date], eventDateTo: Option[Date]): List[EventRow] = database.withTransaction { implicit session: Session =>
    var q = Event.sortBy(_.eventNm)
    q = if (!(eventId.isEmpty)) q.filter(_.eventId === eventId) else q
    q = if (!(eventNm.isEmpty)) q.filter(_.eventNm like ("%" + eventNm + "%")) else q
    q = if (!(eventDateFrom.isEmpty)) q.filter(_.eventDate >= eventDateFrom.get) else q
    q = if (!(eventDateTo.isEmpty)) q.filter(_.eventDate <= eventDateTo.get) else q
    return q.invoker.list
  }

  /** テーブル作成 */
  def createTable = database.withSession { implicit session: Session =>
    ddl.create
  }

  /** テーブル削除 */
  def dropTable = database.withSession { implicit session: Session =>
    ddl.drop
  }

  /** ID検索 */
  def findById(id: Int): EventRow = database.withTransaction { implicit session: Session =>
    Event.filter(_.id === id).first
  }

  /** 更新 */
  def update(e: EventRow) = database.withTransaction { implicit session: Session =>
    Event.filter(_.id === e.id).update(e)
  }

  /** 削除 */
  def delete(id: Int) = database.withTransaction { implicit session: Session =>
    Event.where(_.id === id).delete
  }

}