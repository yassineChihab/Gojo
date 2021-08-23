package com.majjane.chefmajjane.views.activities

import android.app.Dialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.majjane.chefmajjane.R
import com.squareup.picasso.Picasso
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist
import org.jsoup.select.Elements
import java.io.IOException

class Close : AppCompatActivity() {
    var webView:WebView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_close)
        webView=findViewById(R.id.webview)

        webViewSetup()
    }

    fun webViewSetup(){
        webView?.webViewClient= WebViewClient()
        webView?.apply {
            loadUrl("https://www.gojo.ma/Boutique/closedAppServiceInformation.php")
        }
    }

    fun br2nl(html: String?): String {

        val document = Jsoup.parse(html)
        document.outputSettings(
            Document.OutputSettings().prettyPrint(false)
        ) //makes html() preserve linebreaks and spacing
        document.select("br").append("\\n")
        document.select("p").prepend("\\n\\n")
        val s = document.html().replace("\\\\n".toRegex(), "\n")
        return Jsoup.clean(s, "", Whitelist.none(), Document.OutputSettings().prettyPrint(false))


    }
    lateinit var textView: TextView
    lateinit var image: ImageView
    private fun getHtmlFromWeb() {
        Thread(Runnable {
            var description: String = ""
            var link: String = ""
            var uri: Uri? = null
            val picasso = Picasso.get()
            var imgUrl: String? = null

            try {
                val doc: Document =
                    Jsoup.connect("https://www.gojo.ma/Boutique/closedAppServiceInformation.php")
                        .get()

                val elements: Elements = doc.getElementsByClass("html")
                //   val tille:Elements=doc.select("h2")
                imgUrl = doc.select("img").attr("src")
                elements.select("br").append("\\n");
                elements.select("p").prepend("\\n\\n");
                //  elements.select("h2").replaceAll("sis")
                description = br2nl(elements.text())


//                textView.text=description


            } catch (e: IOException) {

            }
            runOnUiThread {
                textView.text = description; picasso.load(imgUrl).into(image)
            }
        }).start()
    }
}