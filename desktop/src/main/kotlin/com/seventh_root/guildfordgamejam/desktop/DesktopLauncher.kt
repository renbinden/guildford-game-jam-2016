package com.seventh_root.guildfordgamejam.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.seventh_root.guildfordgamejam.GuildfordGameJam

fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration()
    config.width = 800
    config.height = 600
    LwjglApplication(GuildfordGameJam(), config)
}
