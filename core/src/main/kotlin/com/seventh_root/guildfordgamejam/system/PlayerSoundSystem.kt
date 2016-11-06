package com.seventh_root.guildfordgamejam.system

import com.badlogic.ashley.core.Entity
import com.badlogic.ashley.core.Family
import com.badlogic.ashley.systems.IteratingSystem
import com.badlogic.gdx.graphics.Color
import com.seventh_root.guildfordgamejam.GuildfordGameJam
import com.seventh_root.guildfordgamejam.component.*
import java.util.*

class PlayerSoundSystem(val game: GuildfordGameJam): IteratingSystem(Family.all(PlayerComponent::class.java, ColorComponent::class.java, CollectedColorsComponent::class.java, PositionComponent::class.java, VelocityComponent::class.java).get()) {
    override fun processEntity(entity: Entity, deltaTime: Float) {
        if (player.get(entity).orbiting) {
            if (velocity.get(entity).x == 0F && velocity.get(entity).y == 0F) {
                player.get(entity).orbiting = false
                game.mainScreen.orbitSound.stop()
                if (color.get(entity).color == Color.WHITE) {
                    game.mainScreen.popSound.play()
                } else {
                    val random = Random()
                    when (random.nextInt(4)) {
                        0 -> game.mainScreen.pluck1Sound.play()
                        1 -> game.mainScreen.pluck2Sound.play()
                        2 -> game.mainScreen.pluck3Sound.play()
                        3 -> game.mainScreen.pluck4Sound.play()
                    }
                }
            }
        } else {
            if (velocity.get(entity).x != 0F || velocity.get(entity).y != 0F) {
                player.get(entity).orbiting = true
                game.mainScreen.orbitSound.play()
            }
        }
    }

}