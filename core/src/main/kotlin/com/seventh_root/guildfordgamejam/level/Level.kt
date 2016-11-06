package com.seventh_root.guildfordgamejam.level

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.files.FileHandle
import com.badlogic.gdx.graphics.Texture

class Level(val file: FileHandle, val buttonTexture: Texture, val name: String, val entities: List<Entity>) {
    fun dispose() {
        buttonTexture.dispose()
    }
}