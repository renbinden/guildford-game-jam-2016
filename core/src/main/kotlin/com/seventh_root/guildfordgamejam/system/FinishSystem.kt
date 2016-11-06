package com.seventh_root.guildfordgamejam.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.utils.Timer
import com.seventh_root.guildfordgamejam.GuildfordGameJam
import com.seventh_root.guildfordgamejam.component.*

class FinishSystem(val game: GuildfordGameJam): IteratingSystem(Family.all(PlayerComponent::class.java, VelocityComponent::class.java, GravityComponent::class.java, PositionComponent::class.java, CollectedColorsComponent::class.java).get()) {
    val grappleFamily: Family = Family.all(GrappleComponent::class.java, ColorComponent::class.java).get()
    val finishFamily: Family = Family.all(FinishComponent::class.java, PositionComponent::class.java).get()
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val finishEntity = engine.getEntitiesFor(finishFamily).firstOrNull()
        if (finishEntity != null) {
            if (!finish.get(finishEntity).finished) {
                if (position.get(finishEntity).x == position.get(entity).x && position.get(finishEntity).y == position.get(entity).y) {
                    val colorsNotCollected = mutableListOf<Color>()
                    for (grapple in engine.getEntitiesFor(grappleFamily).filter { grapple -> color.get(grapple).color != Color.WHITE }) {
                        colorsNotCollected.add(color.get(grapple).color)
                    }
                    colorsNotCollected.removeAll(collectedColors.get(entity).colors)
                    if (colorsNotCollected.isEmpty()) {
                        finish(finishEntity)
                        finish.get(finishEntity).finished = true
                    }
                }
            }
        }
    }

    fun finish(finish: Entity) {
        var delay = 0F
        for (grapple in engine.getEntitiesFor(grappleFamily).filter { grapple -> color.get(grapple).color != Color.WHITE }) {
            Timer.schedule(object: Timer.Task() {
                override fun run() {
                    val ef = Entity()
                    ef.add(RadiusComponent(1F))
                    ef.add(PositionComponent(position.get(finish).x, position.get(finish).y))
                    ef.add(RadiusScalingComponent(600F))
                    ef.add(FinishEffectComponent())
                    ef.add(ColorComponent(color.get(grapple).color))
                    engine.addEntity(ef)
                }
            }, delay)
            delay += 0.2F
        }
        delay += 1F
        Timer.schedule(object: Timer.Task() {
            override fun run() {
                game.menuScreen.reloadLevels()
                game.screen = game.menuScreen
            }
        }, delay)
    }
}