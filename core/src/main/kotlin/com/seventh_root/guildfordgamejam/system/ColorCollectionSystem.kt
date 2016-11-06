package com.seventh_root.guildfordgamejam.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.seventh_root.guildfordgamejam.component.*

class ColorCollectionSystem: IteratingSystem(Family.all(GrappleComponent::class.java, ColorComponent::class.java, PositionComponent::class.java).get()) {
    val playerFamily: Family = Family.all(PlayerComponent::class.java, PositionComponent::class.java, CollectedColorsComponent::class.java).get()
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val player = engine.getEntitiesFor(playerFamily).firstOrNull()
        if (player != null) {
            if (position.get(player).x == position.get(entity).x && position.get(player).y == position.get(entity).y) {
                if (color.get(entity).color != Color.WHITE) {
                    if (!collectedColors.get(player).colors.contains(color.get(entity).color)) {
                        collectedColors.get(player).colors.add(color.get(entity).color)
                    }
                }
            }
        }
    }
}