package com.seventh_root.guildfordgamejam.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.seventh_root.guildfordgamejam.component.*

class FrictionSystem: IteratingSystem(Family.all(PositionComponent::class.java, VelocityComponent::class.java, FrictionComponent::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val velocityX = Math.abs(velocity.get(entity).x)
        val velocityY = Math.abs(velocity.get(entity).y)
        val xBias = velocityX / (velocityX + velocityY)
        val yBias = velocityY / (velocityX + velocityY)
        if (velocity.get(entity).x > 0) {
            velocity.get(entity).x = Math.max(0F, velocity.get(entity).x - (friction.get(entity).amount * xBias))
        } else if (velocity.get(entity).x < 0) {
            velocity.get(entity).x = Math.min(0F, velocity.get(entity).x + (friction.get(entity).amount * xBias))
        }
        if (velocity.get(entity).y > 0) {
            velocity.get(entity).y = Math.max(0F, velocity.get(entity).y - (friction.get(entity).amount * yBias))
        } else if (velocity.get(entity).y < 0) {
            velocity.get(entity).y = Math.min(0F, velocity.get(entity).y + (friction.get(entity).amount * yBias))
        }
    }

}