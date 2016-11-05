package com.seventh_root.guildfordgamejam.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.seventh_root.guildfordgamejam.component.*

class PlayerColorSystem: IteratingSystem(Family.all(PlayerComponent::class.java, ColorComponent::class.java, PositionComponent::class.java).get()) {
    val grappleFamily: Family = Family.all(GrappleComponent::class.java, PositionComponent::class.java, ColorComponent::class.java).get()
    override fun processEntity(entity: Entity, deltaTime: Float) {
        var closestGrapple: Entity? = null
        var shortestDistSq: Float? = null
        var secondClosestGrapple: Entity? = null
        var secondShortestDistSq: Float? = null
        for (grapple in engine.getEntitiesFor(grappleFamily)) {
            if (shortestDistSq == null) {
                closestGrapple = grapple
                val xDist = position.get(entity).x - position.get(grapple).x
                val yDist = position.get(entity).y - position.get(grapple).y
                shortestDistSq = (xDist * xDist) + (yDist * yDist)
            } else if (secondShortestDistSq == null) {
                secondClosestGrapple = grapple
                val xDist = position.get(entity).x - position.get(grapple).x
                val yDist = position.get(entity).y - position.get(grapple).y
                secondShortestDistSq = (xDist * xDist) + (yDist * yDist)
            } else {
                val xDist = position.get(entity).x - position.get(grapple).x
                val yDist = position.get(entity).y - position.get(grapple).y
                val distSq = (xDist * xDist) + (yDist * yDist)
                if (distSq < shortestDistSq) {
                    secondClosestGrapple = closestGrapple
                    secondShortestDistSq = shortestDistSq
                    closestGrapple = grapple
                    shortestDistSq = distSq
                } else if (distSq < secondShortestDistSq) {
                    secondClosestGrapple = grapple
                    secondShortestDistSq = distSq
                }
            }
        }
        if (closestGrapple != null && secondClosestGrapple != null && shortestDistSq != null && secondShortestDistSq != null) {
            val bias = shortestDistSq / (shortestDistSq + secondShortestDistSq)
            val playerColor = Color(
                    ((1 - bias) * color.get(closestGrapple).color.r) + (bias * color.get(secondClosestGrapple).color.r),
                    ((1 - bias) * color.get(closestGrapple).color.g) + (bias * color.get(secondClosestGrapple).color.g),
                    ((1 - bias) * color.get(closestGrapple).color.b) + (bias * color.get(secondClosestGrapple).color.b),
                    255F
            )
            color.get(entity).color = playerColor
        }
    }
}