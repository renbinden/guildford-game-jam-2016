package com.seventh_root.guildfordgamejam.level

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.JsonReader
import com.seventh_root.guildfordgamejam.component.*

fun loadLevel(file: FileHandle): Level {
    val jsonReader = JsonReader()
    val jsonLevel = jsonReader.parse(file)
    val name = jsonLevel.get("name").asString()
    val entities = mutableListOf<Entity>()
    jsonLevel.get("entities").forEach { jsonEntity ->
        when (jsonEntity.get("type").asString()) {
            "Player" -> {
                entities.add(createPlayer(jsonEntity.get("x").asFloat(), jsonEntity.get("y").asFloat()))
            }
            "Grapple" -> {
                entities.add(createGrapple(
                        jsonEntity.get("x").asFloat(),
                        jsonEntity.get("y").asFloat(),
                        Color(
                                jsonEntity.get("red").asFloat(),
                                jsonEntity.get("green").asFloat(),
                                jsonEntity.get("blue").asFloat(),
                                255F
                        )
                ))
            }
            "Finish" -> {
                entities.add(createFinish(
                        jsonEntity.get("x").asFloat(),
                        jsonEntity.get("y").asFloat()
                ))
            }
        }
    }
    return Level(file, name, entities)
}

fun createGrapple(x: Float, y: Float, color: Color): Entity {
    val grapplePoint = Entity()
    grapplePoint.add(PositionComponent(x, y))
    grapplePoint.add(GrappleComponent())
    grapplePoint.add(RadiusComponent(4F))
    grapplePoint.add(ColorComponent(color))
    return grapplePoint
}

fun createPlayer(x: Float, y: Float): Entity {
    val player = Entity()
    player.add(PositionComponent(x, y))
    player.add(VelocityComponent(0.toFloat(), 0.toFloat()))
    player.add(FrictionComponent(0.2F))
    player.add(GravityComponent(x, y, 4F, 0.5F))
    player.add(ColorComponent(Color.WHITE))
    player.add(RadiusComponent(8F))
    player.add(CollectedColorsComponent(mutableListOf<Color>()))
    player.add(PlayerComponent())
    return player
}

fun createFinish(x: Float, y: Float): Entity {
    val finish = Entity()
    finish.add(PositionComponent(x, y))
    finish.add(ColorComponent(Color.WHITE))
    finish.add(RadiusComponent(32F))
    finish.add(FinishComponent())
    return finish
}