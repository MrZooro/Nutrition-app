import org.jetbrains.exposed.sql.Database

object DatabaseFactory {

    fun init() {
        Database.connect(
            url = "jdbc:postgresql://localhost:5432/postgres?currentSchema=nutrition_app",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "mysecretpassword"
        )
    }
}