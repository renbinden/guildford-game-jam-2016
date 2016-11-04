package com.seventh_root.guildfordgamejam.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.seventh_root.guildfordgamejam.component.PositionComponent
import com.seventh_root.guildfordgamejam.component.VelocityComponent
import com.seventh_root.guildfordgamejam.component.position
import com.seventh_root.guildfordgamejam.component.velocity

class MovementSystem: IteratingSystem(Family.all(PositionComponent::class.java, VelocityComponent::class.java).get()) {

    override fun processEntity(entity: Entity, deltaTime: Float) {
        position.get(entity).x += velocity.get(entity).x
        position.get(entity).y += velocity.get(entity).y
    }

}