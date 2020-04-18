package unq.edu.remotetrainer.persistence

import org.jetbrains.exposed.sql.Database

class H2Database: RemoteTrainerDatabase {
    override fun <T> transaction(inTransaction: () -> T): T {
        Database.connect(url = "jdbc:h2:mem:test", driver = "org.h2.Driver")
        return transaction {
            inTransaction()
        }
    }
}