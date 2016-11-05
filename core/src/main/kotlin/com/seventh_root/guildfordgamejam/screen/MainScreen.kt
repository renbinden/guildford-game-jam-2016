package com.seventh_root.guildfordgamejam.screen

import com.badlogic.ashley.core.Engine
import com.badlogic.ashley.core.Family
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.ScreenAdapter
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Filled
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType.Line
import com.badlogic.gdx.math.Vector3
import com.seventh_root.guildfordgamejam.component.*
import com.seventh_root.guildfordgamejam.level.Level
import com.seventh_root.guildfordgamejam.system.*

class MainScreen: ScreenAdapter() {

    val engine = Engine()
    val shapeRenderer = ShapeRenderer()
    val playerFamily: Family = Family.all(PlayerComponent::class.java).get()
    val grappleFamily: Family = Family.all(GrappleComponent::class.java).get()
    val camera = OrthographicCamera()

    init {
        camera.setToOrtho(true)
        engine.addSystem(MovementSystem())
        engine.addSystem(FrictionSystem())
        engine.addSystem(GravitySystem())
        engine.addSystem(PullSystem())
        engine.addSystem(PlayerSizeSystem())
    }

    override fun show() {
        Gdx.input.inputProcessor = object: InputAdapter() {
            override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                engine.getEntitiesFor(playerFamily).forEach { player ->
                    val startX = position.get(player).x
                    val startY = position.get(player).y
                    val endX = Gdx.input.x.toFloat() + camera.position.x - (Gdx.graphics.width / 2)
                    val endY = Gdx.input.y.toFloat() + camera.position.y - (Gdx.graphics.height / 2)
                    val h2 = 8F
                    val o1 = endY - startY
                    val a1 = endX - startX
                    val theta = Math.atan((o1 / a1).toDouble()) + if (endX < startX) Math.PI else 0.0
                    val a2 = Math.cos(theta) * h2
                    val o2 = Math.sin(theta) * h2
                    velocity.get(player).x = a2.toFloat()
                    velocity.get(player).y = o2.toFloat()
                }
                return true
            }
        }
    }

    override fun hide() {
        Gdx.input.inputProcessor = null
    }

    override fun render(delta: Float) {
        Gdx.gl.glClearColor(0.toFloat(), 0.toFloat(), 0.toFloat(), 0.toFloat())
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        engine.update(delta)
        val playerEntity = engine.getEntitiesFor(playerFamily).first()
        camera.position.set(Vector3(position.get(playerEntity).x, position.get(playerEntity).y, 0.toFloat()))
        camera.update()
        shapeRenderer.begin(Line)
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.color = Color.WHITE
        shapeRenderer.circle(position.get(playerEntity).x, position.get(playerEntity).y, player.get(playerEntity).radius)
        shapeRenderer.end()
        shapeRenderer.begin(Line)
        shapeRenderer.projectionMatrix = camera.combined
        shapeRenderer.color = Color.WHITE
        val startX = position.get(playerEntity).x
        val startY = position.get(playerEntity).y
        val endX = Gdx.input.x.toFloat() + camera.position.x - (Gdx.graphics.width / 2)
        val endY = Gdx.input.y.toFloat() + camera.position.y - (Gdx.graphics.height / 2)
        val h2 = 64F
        val o1 = endY - startY
        val a1 = endX - startX
        val theta = Math.atan((o1 / a1).toDouble()) + if (endX < startX) Math.PI else 0.0
        val a2 = Math.cos(theta) * h2
        val o2 = Math.sin(theta) * h2
        val x2 = startX + a2
        val y2 = startY + o2
        shapeRenderer.line(startX, startY, x2.toFloat(), y2.toFloat())
        shapeRenderer.end()
        engine.getEntitiesFor(grappleFamily).forEach { grapple ->
            shapeRenderer.color = Color.WHITE
            shapeRenderer.begin(Filled)
            shapeRenderer.circle(position.get(grapple).x, position.get(grapple).y, 4.toFloat())
            shapeRenderer.end()
        }
    }

    override fun dispose() {
        shapeRenderer.dispose()
    }

    fun displayLevel(level: Level) {
        engine.removeAllEntities()
        level.entities.forEach { entity -> engine.addEntity(entity) }
    }

}