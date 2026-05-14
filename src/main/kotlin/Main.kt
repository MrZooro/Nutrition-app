import com.google.ortools.Loader
import dao.Dao
import io.github.smiley4.ktorswaggerui.SwaggerUI
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import orTools.MealPlanService

fun main() {
    embeddedServer(Netty, port = 9191, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    val dao = Dao()
    val calculation = Calculation()
    val mealPlanService = MealPlanService()
    Loader.loadNativeLibraries()

    configureSerialization()
    configureRouting(dao, calculation, mealPlanService)
    configureSwagger()
}

fun Application.configureSerialization() {
    install(ContentNegotiation) {
        json()
    }
}

fun Application.configureSwagger() {
    install(SwaggerUI) {

        swagger {
        }

        info {
            title = "Sample API"
            version = "1.0"
            description = "Swagger for Sample API"
        }
    }
}