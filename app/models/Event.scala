package models

import play.api.db.DB
import play.api.Play.current
import scala.slick.driver.H2Driver.simple._
import scala.language.postfixOps

case class Event(id: Int, eventId: String, eventNm: String)

object Events {

  /** データベースコネクション */
  val database = Database.forDataSource(DB.getDataSource())

  /** EVENTテーブルの定義 */
  class EventTag(tag: Tag) extends Table[Event](tag, "EVENT") {
    def id      = column[Int]("ID", O.PrimaryKey, O AutoInc)
    def eventId = column[String]("EVENT_ID", O.Nullable, O DBType "varchar(10)")
    def eventNm = column[String]("EVENT_NM", O.Nullable, O DBType "varchar(100)")
    def * = (id, eventId, eventNm) <> (Event.tupled, Event.unapply)
  }

  /** クエリ */
  val events = TableQuery[EventTag]

  /** 登録 */
  def create(e: Event) = database.withTransaction { implicit session: Session =>
    events.insert(e)
  }

  /** 検索 */
  def find(eventId: String, eventNm: String): List[Event] = database.withTransaction { implicit session: Session =>
    var q = events.sortBy(_.eventNm)
    q = if (!(eventId.isEmpty)) q.filter(_.eventId === eventId) else q
    q = if (!(eventNm.isEmpty)) q.filter(_.eventNm like ("%" + eventNm + "%")) else q
    return q.invoker.list
  }

  /** ID検索 */
  def findById(id: Int): Event = database.withTransaction { implicit session: Session =>
    events.filter(_.id === id).first
  }

  /** 更新 */
  def update(e: Event) = database.withTransaction { implicit session: Session =>
    events.filter(_.id === e.id).update(e)
  }

  /** 削除 */
  def delete(id: Int) = database.withTransaction { implicit session: Session =>
    events.where(_.id === id).delete
  }

  /** テーブル作成 */
  def createTable = database.withSession { implicit session: Session =>
    events.ddl.create
  }

  /** テーブル削除 */
  def dropTable = database.withSession { implicit session: Session =>
    events.ddl.drop
  }
}