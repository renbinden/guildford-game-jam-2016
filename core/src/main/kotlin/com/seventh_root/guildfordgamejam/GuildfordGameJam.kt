package com.seventh_root.guildfordgamejam

import com.badlogic.gdx.Game
import com.badlogic.gdx.Gdx
import com.seventh_root.guildfordgamejam.level.loadLevel
import com.seventh_root.guildfordgamejam.screen.MainScreen

class GuildfordGameJam: Game() {

    lateinit var mainScreen: MainScreen

    override fun create() {
        mainScreen = MainScreen()
        mainScreen.displayLevel(loadLevel(Gdx.files.internal("level1.json")))
        setScreen(mainScreen)
    }

    override fun dispose() {
        mainScreen.dispose()
    }
}
