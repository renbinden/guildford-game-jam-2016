package com.seventh_root.guildfordgamejam

import com.badlogic.gdx.Game
import com.seventh_root.guildfordgamejam.screen.MainScreen

class GuildfordGameJam: Game() {

    lateinit var mainScreen: MainScreen

    override fun create() {
        mainScreen = MainScreen()
        setScreen(mainScreen)
    }

    override fun dispose() {
        mainScreen.dispose()
    }
}
