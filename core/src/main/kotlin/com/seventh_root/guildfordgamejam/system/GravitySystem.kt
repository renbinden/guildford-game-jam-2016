package com.seventh_root.guildfordgamejam.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.seventh_root.guildfordgamejam.component.*

class GravitySystem: IteratingSystem(Family.all(VelocityComponent::class.java, GravityComponent::class.java).get()) {
    
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val xDist = Math.abs(position.get(entity).x - gravity.get(entity).targetX)
        val yDist = Math.abs(position.get(entity).y - gravity.get(entity).targetY)
        val xBias = xDist / (xDist + yDist)
        val yBias = yDist / (xDist + yDist)
        if (Math.abs(position.get(entity).x - gravity.get(entity).targetX) > gravity.get(entity).targetRadius || Math.abs(position.get(entity).y - gravity.get(entity).targetY) > gravity.get(entity).targetRadius) {
            if (position.get(entity).x > gravity.get(entity).targetX) {
                velocity.get(entity).x -= xBias * gravity.get(entity).amount
            } else if (position.get(entity).x < gravity.get(entity).targetX) {
                velocity.get(entity).x += xBias * gravity.get(entity).amount
            }
            if (position.get(entity).y > gravity.get(entity).targetY) {
                velocity.get(entity).y -= yBias * gravity.get(entity).amount
            } else if (position.get(entity).y < gravity.get(entity).targetY) {
                velocity.get(entity).y += yBias * gravity.get(entity).amount
            }
        } else {
            velocity.get(entity).x = 0F
            velocity.get(entity).y = 0F
            position.get(entity).x = gravity.get(entity).targetX
            position.get(entity).y = gravity.get(entity).targetY
        }
    }

}