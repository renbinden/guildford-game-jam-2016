package com.seventh_root.guildfordgamejam.component

import com.badlogic.ashley.core.ComponentMapper

val position: ComponentMapper<PositionComponent> = ComponentMapper.getFor(PositionComponent::class.java)
val velocity: ComponentMapper<VelocityComponent> = ComponentMapper.getFor(VelocityComponent::class.java)
