package com.seventh_root.guildfordgamejam.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import com.seventh_root.guildfordgamejam.GuildfordGameJam
import java.awt.GraphicsEnvironment

fun main(arg: Array<String>) {
    val config = LwjglApplicationConfiguration()
    val graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().defaultScreenDevice
    config.width = graphicsDevice.displayMode.width
    config.height = graphicsDevice.displayMode.height
    config.fullscreen = true
//    config.width = 800
//    config.height = 600
    LwjglApplication(GuildfordGameJam(), config)
}
