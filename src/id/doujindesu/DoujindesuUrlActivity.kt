package eu.kanade.tachiyomi.extension.id.doujindesu

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import kotlin.system.exitProcess

class DoujindesuUrlActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pathSegments = intent?.data?.pathSegments
        if (pathSegments != null && pathSegments.size > 1) {
            val url = "/${pathSegments.joinToString("/")}"
            val mainIntent = Intent().apply {
                action = "MAIN"
                addCategory("CATEGORY_DEFAULT")
                putExtra("url", url)
            }
            startActivity(mainIntent)
        }
        finish()
        exitProcess(0)
    }
}

