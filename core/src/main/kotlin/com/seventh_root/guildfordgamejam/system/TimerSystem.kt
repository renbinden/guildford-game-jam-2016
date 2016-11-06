package com.seventh_root.guildfordgamejam.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.seventh_root.guildfordgamejam.component.FinishComponent
import com.seventh_root.guildfordgamejam.component.TimerComponent
import com.seventh_root.guildfordgamejam.component.finish
import com.seventh_root.guildfordgamejam.component.timer

class TimerSystem: IteratingSystem(Family.all(TimerComponent::class.java).get()) {
    val finishFamily: Family = Family.all(FinishComponent::class.java).get()
    override fun processEntity(entity: Entity, deltaTime: Float) {
        val finishEntity = engine.getEntitiesFor(finishFamily).firstOrNull()
        if (finishEntity != null) {
            if (!finish.get(finishEntity).finished) {
                timer.get(entity).time += deltaTime
            }
        }
    }
}