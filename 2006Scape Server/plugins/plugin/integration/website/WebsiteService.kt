package plugin.integration.website

import com.rs2.Constants
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService

object WebsiteService {

    private var scheduler: ScheduledExecutorService? = null
    private var warnedNoPassword = false

    @Synchronized
    fun getScheduler(): ScheduledExecutorService {
        val current = scheduler
        if (current == null || current.isShutdown) {
            val newExec = Executors.newSingleThreadScheduledExecutor { r ->
                Thread(r, "WebsiteIntegrationWorker").apply { isDaemon = true }
            }
            scheduler = newExec
            return newExec
        }
        return current
    }

    @Synchronized
    fun stop() {
        scheduler?.shutdownNow()
        scheduler = null
    }

    fun warnMissingPassword() {
        if (!warnedNoPassword) {
            warnedNoPassword = true
            println("No Website Password Set So Website Integration Tasks Stopped")
        }
    }

    fun sendGetRequest(endpoint: String, params: Map<String, Any>): Boolean {
        val baseUrl = Constants.WEBSITE_LINK.trimEnd('/')
        if (baseUrl.isEmpty()) return false

        val query = params.entries.joinToString("&") { (key, value) ->
            val encodedValue = URLEncoder.encode(value.toString(), StandardCharsets.UTF_8.name())
            "$key=$encodedValue"
        }

        val fullUrl = "$baseUrl/$endpoint?$query"
        return try {
            val url = URL(fullUrl)
            val connection = url.openConnection() as? HttpURLConnection ?: return false
            connection.apply {
                requestMethod = "GET"
                connectTimeout = 5000
                readTimeout = 5000
                setRequestProperty("User-Agent", "2006Scape-Server")
                inputStream.use { it.readBytes() }
            }
            true
        } catch (e: Exception) {
            if (Constants.SERVER_DEBUG) {
                e.printStackTrace()
            } else {
                System.err.println("[WebsiteIntegration] Failed to update $endpoint: ${e.message}")
            }
            false
        }
    }
}
