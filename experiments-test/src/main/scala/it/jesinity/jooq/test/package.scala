package it.jesinity.jooq

import java.sql.{Connection, DriverManager, Statement}

/**
  * created by CGnal s.p.a
  */
package object test {

    case class SQLDocument(
                            pk: Long = 0L,
                            id: String = "unknown",
                            text: String,
                            publishdate: Long = 0L,
                            publishday: Int = 19701010,
                            sourcetype: String = "unknown",
                            title: String,
                            link: String,
                            sourcename: String = "unknown",
                            author: String = "unknown",
                            sourcedomain: String = "unknown",
                            sourcetopic: String = "unknown",
                            domain: String = "unknown",
                            topic: String = "unknown"
                          )

    /**
      *
      * @param user
      * @param password
      * @param url
      * @param businessLogic
      * @tparam T
      * @return
      */
    def withSQLConnection[T](user:String, password:String, url:String)(businessLogic: Connection => T ) : T = {

      var connection: Connection = null
      try {
        connection = DriverManager.getConnection(url, user, password)
        businessLogic(connection)
      } finally {
        if (connection != null) {
          connection.close()
        }
      }
    }

    def withStatement[T](statement:Statement)(businessLogic: Statement => T) : T = {
      try {
        businessLogic(statement)
      } finally {
        if (statement != null) {
          statement.close()
        }
      }
    }
  }