package com.seventh_root.guildfordgamejam.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.seventh_root.guildfordgamejam.GuildfordGameJam

fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration()
    config.width = 1280
    config.height = 720
    config.fullscreen = false
    LwjglApplication(GuildfordGameJam(), config)
}
