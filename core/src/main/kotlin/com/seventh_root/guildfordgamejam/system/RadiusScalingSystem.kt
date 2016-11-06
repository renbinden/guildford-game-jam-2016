package com.seventh_root.guildfordgamejam.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.seventh_root.guildfordgamejam.component.RadiusComponent
import com.seventh_root.guildfordgamejam.component.RadiusScalingComponent
import com.seventh_root.guildfordgamejam.component.radius
import com.seventh_root.guildfordgamejam.component.radiusScaling

class RadiusScalingSystem: IteratingSystem(Family.all(RadiusScalingComponent::class.java, RadiusComponent::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        radius.get(entity).radius += deltaTime * radiusScaling.get(entity).rate
    }

}