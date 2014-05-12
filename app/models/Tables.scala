package models
// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = scala.slick.driver.H2Driver
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: scala.slick.driver.JdbcProfile
  import profile.simple._
  import scala.slick.model.ForeignKeyAction
  // NOTE: GetResult mappers for plain SQL are only generated for tables where Slick knows how to map the types of all columns.
  import scala.slick.jdbc.{GetResult => GR}
  
  /** DDL for all tables. Call .create to execute. */
  lazy val ddl = Event.ddl
  
  /** Entity class storing rows of table Event
   *  @param id Database column ID AutoInc, PrimaryKey
   *  @param eventId Database column EVENT_ID 
   *  @param eventNm Database column EVENT_NM 
   *  @param eventDate Database column EVENT_DATE 
   *  @param homepage Database column HOMEPAGE  */
  case class EventRow(id: Int, eventId: String, eventNm: String, eventDate: Option[java.sql.Date], homepage: Option[String])
  /** GetResult implicit for fetching EventRow objects using plain SQL queries */
  implicit def GetResultEventRow(implicit e0: GR[Int], e1: GR[String], e2: GR[Option[java.sql.Date]], e3: GR[Option[String]]): GR[EventRow] = GR{
    prs => import prs._
    EventRow.tupled((<<[Int], <<[String], <<[String], <<?[java.sql.Date], <<?[String]))
  }
  /** Table description of table EVENT. Objects of this class serve as prototypes for rows in queries. */
  class Event(tag: Tag) extends Table[EventRow](tag, "EVENT") {
    def * = (id, eventId, eventNm, eventDate, homepage) <> (EventRow.tupled, EventRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = (id.?, eventId.?, eventNm.?, eventDate, homepage).shaped.<>({r=>import r._; _1.map(_=> EventRow.tupled((_1.get, _2.get, _3.get, _4, _5)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))
    
    /** Database column ID AutoInc, PrimaryKey */
    val id: Column[Int] = column[Int]("ID", O.AutoInc, O.PrimaryKey)
    /** Database column EVENT_ID  */
    val eventId: Column[String] = column[String]("EVENT_ID")
    /** Database column EVENT_NM  */
    val eventNm: Column[String] = column[String]("EVENT_NM")
    /** Database column EVENT_DATE  */
    val eventDate: Column[Option[java.sql.Date]] = column[Option[java.sql.Date]]("EVENT_DATE")
    /** Database column HOMEPAGE  */
    val homepage: Column[Option[String]] = column[Option[String]]("HOMEPAGE")
  }
  /** Collection-like TableQuery object for table Event */
  lazy val Event = new TableQuery(tag => new Event(tag))
}