package com.seventh_root.guildfordgamejam

import com.badlogic.gdx.Game
import com.seventh_root.guildfordgamejam.screen.MainScreen
import com.seventh_root.guildfordgamejam.screen.MenuScreen

class GuildfordGameJam: Game() {

    lateinit var mainScreen: MainScreen
    lateinit var menuScreen: MenuScreen

    override fun create() {
        menuScreen = MenuScreen(this)
        mainScreen = MainScreen(this)
        setScreen(menuScreen)
    }

    override fun dispose() {
        mainScreen.dispose()
    }
}
