package com.seventh_root.guildfordgamejam.component

import com.badlogic.ashley.core.ComponentMapper

val position: ComponentMapper<PositionComponent> = ComponentMapper.getFor(PositionComponent::class.java)
val velocity: ComponentMapper<VelocityComponent> = ComponentMapper.getFor(VelocityComponent::class.java)
val friction: ComponentMapper<FrictionComponent> = ComponentMapper.getFor(FrictionComponent::class.java)
val gravity: ComponentMapper<GravityComponent> = ComponentMapper.getFor(GravityComponent::class.java)
//val player: ComponentMapper<PlayerComponent> = ComponentMapper.getFor(PlayerComponent::class.java)
//val grapple: ComponentMapper<GrappleComponent> = ComponentMapper.getFor(GrappleComponent::class.java)
val color: ComponentMapper<ColorComponent> = ComponentMapper.getFor(ColorComponent::class.java)
val radius: ComponentMapper<RadiusComponent> = ComponentMapper.getFor(RadiusComponent::class.java)
