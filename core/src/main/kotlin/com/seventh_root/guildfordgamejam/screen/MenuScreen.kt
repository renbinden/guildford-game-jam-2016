package com.seventh_root.guildfordgamejam.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.seventh_root.guildfordgamejam.GuildfordGameJam
import com.seventh_root.guildfordgamejam.component.*
import com.seventh_root.guildfordgamejam.level.Level
import com.seventh_root.guildfordgamejam.level.loadLevel
import com.seventh_root.guildfordgamejam.score.HighScores
import com.seventh_root.guildfordgamejam.system.GravitySystem
import com.seventh_root.guildfordgamejam.system.MovementSystem

class MenuScreen(val game: GuildfordGameJam): ScreenAdapter() {

    val font = BitmapFont(Gdx.files.internal("m5x7.fnt"))
    var levels = listOf(
            loadLevel(Gdx.files.internal("level1.json")),
            loadLevel(Gdx.files.internal("level2.json")),
            loadLevel(Gdx.files.internal("lewis_face.json"))
    )
    val engine = Engine()
    val spriteBatch = SpriteBatch()
    val buttonTexture = Texture(Gdx.files.internal("btn.png"))
    val prevTexture = Texture(Gdx.files.internal("btn_left.png"))
    val nextTexture = Texture(Gdx.files.internal("btn_right.png"))
    val music: Music = Gdx.audio.newMusic(Gdx.files.internal("ambient_music.ogg"))
    val textureFamily: Family = Family.all(TextureComponent::class.java, PositionComponent::class.java).get()
    val levelButtonFamily: Family = Family.all(LevelComponent::class.java, TextureComponent::class.java, PositionComponent::class.java, VelocityComponent::class.java, GravityComponent::class.java).get()
    val prevButtonFamily: Family = Family.all(PreviousButtonComponent::class.java, TextureComponent::class.java, PositionComponent::class.java).get()
    val nextButtonFamily: Family = Family.all(NextButtonComponent::class.java, TextureComponent::class.java, PositionComponent::class.java).get()
    val highScores = HighScores(mutableMapOf())

    init {
        engine.addSystem(MovementSystem())
        engine.addSystem(GravitySystem())
        createButtons()
        music.isLooping = true
        music.play()
    }

    fun createButtons() {
        var x = (Gdx.graphics.width - buttonTexture.width) / 2F
        for (level in levels) {
            val levelButton = Entity()
            levelButton.add(TextureComponent(buttonTexture))
            levelButton.add(LevelComponent(level))
            levelButton.add(PositionComponent(x, (Gdx.graphics.height - buttonTexture.height) / 2F))
            levelButton.add(VelocityComponent(0F, 0F))
            levelButton.add(GravityComponent(x, (Gdx.graphics.height - buttonTexture.height) / 2F, 16F, 0.5F))
            engine.addEntity(levelButton)
            x += 256
        }
        val prevButton = Entity()
        prevButton.add(PreviousButtonComponent())
        prevButton.add(TextureComponent(prevTexture))
        prevButton.add(PositionComponent(((Gdx.graphics.width - buttonTexture.width) / 2F) - 96F, (Gdx.graphics.height - buttonTexture.height) / 2F))
        engine.addEntity(prevButton)
        val nextButton = Entity()
        nextButton.add(NextButtonComponent())
        nextButton.add(TextureComponent(nextTexture))
        nextButton.add(PositionComponent(((Gdx.graphics.width - buttonTexture.width) / 2F) + 96F, (Gdx.graphics.height - buttonTexture.height) / 2F))
        engine.addEntity(nextButton)
    }

    override fun show() {
        Gdx.input.inputProcessor = object: InputAdapter() {
            override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                val levelButton = engine.getEntitiesFor(levelButtonFamily)
                        .filter { entity ->
                            screenX > position.get(entity).x
                            && screenY > position.get(entity).y
                            && screenX < position.get(entity).x + texture.get(entity).texture.width
                            && screenY < position.get(entity).y + texture.get(entity).texture.height
                        }
                        .firstOrNull()
                if (levelButton != null) {
                    game.highScoreEntryScreen.level = level.get(levelButton).level.name
                    game.mainScreen.displayLevel(level.get(levelButton).level)
                    game.screen = game.mainScreen
                }
                val prevButton = engine.getEntitiesFor(prevButtonFamily)
                        .filter { entity ->
                            screenX > position.get(entity).x
                            && screenY > position.get(entity).y
                            && screenX < position.get(entity).x + texture.get(entity).texture.width
                            && screenY < position.get(entity).y + texture.get(entity).texture.height
                        }
                        .firstOrNull()
                if (prevButton != null) {
                    engine.getEntitiesFor(levelButtonFamily).forEach { entity -> gravity.get(entity).targetX += 256 }
                }
                val nextButton = engine.getEntitiesFor(nextButtonFamily)
                        .filter { entity ->
                            screenX > position.get(entity).x
                            && screenY > position.get(entity).y
                            && screenX < position.get(entity).x + texture.get(entity).texture.width
                            && screenY < position.get(entity).y + texture.get(entity).texture.height
                        }
                        .firstOrNull()
                if (nextButton != null) {
                    engine.getEntitiesFor(levelButtonFamily).forEach { entity -> gravity.get(entity).targetX -= 256 }
                }
                return true
            }
        }
    }

    override fun hide() {
        Gdx.input.inputProcessor = null
    }


    override fun render(delta: Float) {
        engine.update(delta)
        Gdx.gl.glClearColor(0F, 0F, 0F, 1F)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        spriteBatch.begin()
        engine.getEntitiesFor(textureFamily).forEach { entity ->
            spriteBatch.draw(texture.get(entity).texture, position.get(entity).x, position.get(entity).y)
        }
        engine.getEntitiesFor(levelButtonFamily).forEach { entity ->
            font.draw(spriteBatch, level.get(entity).level.name, position.get(entity).x, position.get(entity).y + 96)
            var y = position.get(entity).y + 192F
            var pos = 1
            for (highScore in highScores.getHighScores(level.get(entity).level.name)) {
                if (highScore != null) {
                    font.draw(spriteBatch, "$pos. ${highScore.name} - ${String.format("%.2f", highScore.time)}", position.get(entity).x, y)
                    y -= 32F
                    pos++
                }
            }
        }
        spriteBatch.end()
    }

    override fun dispose() {
        music.stop()
        font.dispose()
        spriteBatch.dispose()
        buttonTexture.dispose()
        prevTexture.dispose()
        nextTexture.dispose()
        music.dispose()
    }

    fun reloadLevels() {
        val levels: MutableList<Level> = mutableListOf()
        for (level in this.levels) {
            levels.add(loadLevel(level.file))
        }
        this.levels = levels
        engine.removeAllEntities()
        createButtons()
    }

}