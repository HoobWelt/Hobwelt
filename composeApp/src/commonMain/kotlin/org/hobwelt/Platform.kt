package org.hobwelt

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform