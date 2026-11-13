package plugin.integration.website

import com.rs2.Constants
import java.io.File
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

object RegisteredAccsWebsite {

    private var task: ScheduledFuture<*>? = null

    fun start() {
        if (!WebsiteConfig.registeredAccounts) {
            return
        }
        if (WebsiteConfig.password.isEmpty()) {
            WebsiteService.warnMissingPassword()
            return
        }

        stop()
        task = WebsiteService.getScheduler().scheduleAtFixedRate({
            try {
                update()
            } catch (e: Exception) {
                if (Constants.SERVER_DEBUG) {
                    e.printStackTrace()
                } else {
                    System.err.println("[WebsiteIntegration] Error in RegisteredAccsWebsite task: ${e.message}")
                }
            }
        }, 15, 90, TimeUnit.SECONDS)
    }

    fun stop() {
        task?.cancel(true)
        task = null
    }

    fun update() {
        val pass = WebsiteConfig.password
        if (pass.isEmpty()) return

        val charactersPath = Paths.get("data", "characters")
        val amount = if (Files.exists(charactersPath) && Files.isDirectory(charactersPath)) {
            try {
                val stream = Files.list(charactersPath)
                try {
                    stream.count().toInt()
                } finally {
                    stream.close()
                }
            } catch (e: Exception) {
                File("data/characters/").list()?.size ?: 0
            }
        } else {
            0
        }

        WebsiteService.sendGetRequest("accountsregistered.php", mapOf(
            "pass" to pass,
            "amount" to amount,
            "world" to Constants.WORLD
        ))
    }
}
