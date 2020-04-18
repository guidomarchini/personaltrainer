package unq.edu.remotetrainer.persistence

interface RemoteTrainerDatabase {
    fun <T> transaction(inTransaction: () -> T): T
}