package com.seventh_root.guildfordgamejam.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.seventh_root.guildfordgamejam.component.*

class PlayerSizeSystem: IteratingSystem(Family.all(PlayerComponent::class.java, PositionComponent::class.java, RadiusComponent::class.java).get()) {
    val grappleFamily: Family = Family.all(GrappleComponent::class.java, PositionComponent::class.java).get()
    override fun processEntity(entity: Entity, deltaTime: Float) {
        var shortestDistSq: Float? = null
        for (grapple in engine.getEntitiesFor(grappleFamily)) {
            if (shortestDistSq == null) {
                val xDist = position.get(entity).x - position.get(grapple).x
                val yDist = position.get(entity).y - position.get(grapple).y
                shortestDistSq = (xDist * xDist) + (yDist * yDist)
            } else {
                val xDist = position.get(entity).x - position.get(grapple).x
                val yDist = position.get(entity).y - position.get(grapple).y
                val distSq = (xDist * xDist) + (yDist * yDist)
                if (distSq < shortestDistSq) {
                    shortestDistSq = distSq
                }
            }
        }
        if (shortestDistSq != null) {
            radius.get(entity).radius = Math.abs(32F - Math.sqrt(shortestDistSq.toDouble()).toFloat())
        }
    }
}