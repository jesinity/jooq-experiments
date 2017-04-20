package it.jesinity.jooq.test

import org.jooq.{DSLContext, SQLDialect}
import org.jooq.conf.Settings
import org.jooq.impl.DSL
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, FunSuite}

import scala.io.Source
import it.jesinity.jooq.test._
import it.jesinity.jooq.schema.Tables._
import scala.collection.JavaConverters._


class JOOQDomainArchiverTest extends FunSuite with BeforeAndAfterAll with BeforeAndAfterEach {

  val schemaToString = Source.fromInputStream(classOf[JOOQDomainArchiverTest].getResourceAsStream("schema.ddl")).getLines().mkString("\n")

  test("an SQL document should be correctly archived and retrieved") {

    withSQLConnection("", "", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false") { conn =>

      conn.setAutoCommit(true)

      withStatement(conn.createStatement()) { statement => statement.executeUpdate(schemaToString) }

      val settings = new Settings().withRenderSchema(false)
      val dsl: DSLContext = DSL.using(conn, SQLDialect.H2, settings)

      dsl.insertInto(
        INPUTDOCUMENT,
        INPUTDOCUMENT.ID,
        INPUTDOCUMENT.LINK,
        INPUTDOCUMENT.TEXT,
        INPUTDOCUMENT.SOURCENAME,
        INPUTDOCUMENT.PUBLISHDATE,
        INPUTDOCUMENT.PUBLISHDAY
      ).values("http://ciao.com", "http://ciao.com", "text", "ciao.com", 20161212L, 20161212).execute()

      val data = dsl.select().from(INPUTDOCUMENT).fetchInto(classOf[SQLDocument]).asScala.toSet
      val res = SQLDocument(1L, "http://ciao.com", "text", 20161212, 20161212, "unknown", "unknown", "ciao.com", "unknown", "unknown", "unknown", "http://ciao.com", "unknown", "unknown")

      assertResult(Set(res))(data)

    }
  }

}