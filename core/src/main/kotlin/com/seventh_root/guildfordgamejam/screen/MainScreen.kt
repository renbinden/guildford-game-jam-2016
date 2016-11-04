package com.seventh_root.guildfordgamejam.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Line
import com.seventh_root.guildfordgamejam.component.PositionComponent
import com.seventh_root.guildfordgamejam.component.VelocityComponent
import com.seventh_root.guildfordgamejam.component.position
import com.seventh_root.guildfordgamejam.system.MovementSystem

class MainScreen: ScreenAdapter() {

    val engine = Engine()
    val player = Entity()
    val shapeRenderer = ShapeRenderer()

    init {
        engine.addSystem(MovementSystem())
        player.add(PositionComponent(0.toFloat(), 0.toFloat()))
        player.add(VelocityComponent(0.toFloat(), 0.toFloat()))
        engine.addEntity(player)
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.toFloat(), 0.toFloat(), 0.toFloat(), 0.toFloat())
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        engine.update(delta)
        shapeRenderer.color = Color.WHITE
        shapeRenderer.begin(Line)
        shapeRenderer.circle(position.get(player).x, position.get(player).y, 64.toFloat())
        shapeRenderer.end()
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }

}