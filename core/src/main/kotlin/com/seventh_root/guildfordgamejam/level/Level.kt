package com.seventh_root.guildfordgamejam.level

import com.badlogic.ashley.core.Entity
import com.badlogic.gdx.files.FileHandle

class Level(val file: FileHandle, val name: String, val entities: List<Entity>)