package com.seventh_root.guildfordgamejam.level

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.utils.JsonReader
import com.seventh_root.guildfordgamejam.component.*

fun loadLevel(file: FileHandle): Level {
    val jsonReader = JsonReader()
    val jsonLevel = jsonReader.parse(file)
    val entities = mutableListOf<Entity>()
    jsonLevel.get("entities").forEach { jsonEntity ->
        when (jsonEntity.get("type").asString()) {
            "Player" -> {
                entities.add(createPlayer(jsonEntity.get("x").asFloat(), jsonEntity.get("y").asFloat()))
            }
            "Grapple" -> {
                entities.add(createGrapple(jsonEntity.get("x").asFloat(), jsonEntity.get("y").asFloat()))
            }
        }
    }
    return Level(entities)
}

fun createGrapple(x: Float, y: Float): Entity {
    val grapplePoint = Entity()
    grapplePoint.add(PositionComponent(x, y))
    grapplePoint.add(GrappleComponent())
    return grapplePoint
}

fun createPlayer(x: Float, y: Float): Entity {
    val player = Entity()
    player.add(PositionComponent(x, y))
    player.add(VelocityComponent(0.toFloat(), 0.toFloat()))
    player.add(FrictionComponent(0.2F))
    player.add(GravityComponent(x, y, 0.5F))
    player.add(PlayerComponent(32F))
    return player
}