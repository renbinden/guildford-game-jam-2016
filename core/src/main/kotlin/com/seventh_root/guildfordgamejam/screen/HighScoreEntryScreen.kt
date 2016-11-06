package com.seventh_root.guildfordgamejam.screen

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.scenes.scene2d.Actor
import com.badlogic.gdx.scenes.scene2d.Stage
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.scenes.scene2d.ui.TextField
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener
import com.kotcrab.vis.ui.VisUI
import com.seventh_root.guildfordgamejam.GuildfordGameJam

class HighScoreEntryScreen(game: GuildfordGameJam) : ScreenAdapter() {

    var level: String = ""
    var time: Float = 10000F

    val stage = Stage()

    init {
        val table = Table()
        table.center()
        table.add(Label("Name: ", VisUI.getSkin())).width(128F).left().padBottom(32F)
        val nameField = TextField("", VisUI.getSkin())
        table.add(nameField).width(256F).padBottom(32F)
        table.row()
        val saveButton = TextButton("Save", VisUI.getSkin())
        saveButton.addListener(object : ChangeListener() {
            override fun changed(event: ChangeEvent, actor: Actor) {
                game.menuScreen.highScores.addHighScore(level, nameField.text, time)
                game.screen = game.menuScreen
            }
        })
        table.add(saveButton).colspan(2).width(384F).center().padBottom(16F)
        table.setFillParent(true)
        stage.addActor(table)
    }

    override fun show() {
        Gdx.input.inputProcessor = stage
    }

    override fun hide() {
        Gdx.input.inputProcessor = null
    }


    override fun resize(width: Int, height: Int) {
        stage.viewport.update(width, height, true)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0F, 0F, 0F, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        stage.act(delta)
        stage.draw()
    }

    override fun dispose() {
        stage.dispose()
    }

}