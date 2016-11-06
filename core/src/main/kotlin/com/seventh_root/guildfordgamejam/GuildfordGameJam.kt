package com.seventh_root.guildfordgamejam

import com.badlogic.gdx.Game
import com.kotcrab.vis.ui.VisUI
import com.seventh_root.guildfordgamejam.screen.HighScoreEntryScreen
import com.seventh_root.guildfordgamejam.screen.MainScreen
import com.seventh_root.guildfordgamejam.screen.MenuScreen

class GuildfordGameJam: Game() {

    lateinit var mainScreen: MainScreen
    lateinit var menuScreen: MenuScreen
    lateinit var highScoreEntryScreen: HighScoreEntryScreen

    override fun create() {
        VisUI.load()
        menuScreen = MenuScreen(this)
        mainScreen = MainScreen(this)
        highScoreEntryScreen = HighScoreEntryScreen(this)
        setScreen(menuScreen)
    }

    override fun dispose() {
        menuScreen.dispose()
        mainScreen.dispose()
        highScoreEntryScreen.dispose()
        VisUI.dispose()
    }
}
