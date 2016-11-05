package com.seventh_root.guildfordgamejam.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.seventh_root.guildfordgamejam.component.*

class MovementSystem: IteratingSystem(Family.all(PositionComponent::class.java, VelocityComponent::class.java).get()) {

    val frictionFamily: Family = Family.all(PositionComponent::class.java, VelocityComponent::class.java, FrictionComponent::class.java).get()

    override fun processEntity(entity: Entity, deltaTime: Float) {
        position.get(entity).x += velocity.get(entity).x
        position.get(entity).y += velocity.get(entity).y
    }

}