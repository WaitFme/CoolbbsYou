package com.anpe.coolbbsyou.util

import org.jsoup.nodes.Document

class LoginUtils {
    companion object {
        fun Document.createRequestHash() = this.getElementsByTag("Body").attr("data-request-hash")

        fun createRandomNumber() = Math.random().toString().replace(".", "undefined")
    }
}