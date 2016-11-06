package com.seventh_root.guildfordgamejam.score

class HighScores(val highScores: MutableMap<String, MutableList<HighScore?>>) {

    fun getHighScores(level: String): MutableList<HighScore?> {
        var levelHighScores = highScores[level]
        if (levelHighScores == null) {
            levelHighScores = mutableListOf<HighScore?>(null, null, null)
            highScores[level] = levelHighScores
        }
        return levelHighScores
    }

    fun addHighScore(level: String, player: String, time: Float) {
        val levelHighScores = highScores[level]?:mutableListOf<HighScore?>(null, null, null)
        if (levelHighScores[0]?.time?:Float.MAX_VALUE > time) {
            levelHighScores[2] = levelHighScores[1]
            levelHighScores[1] = levelHighScores[0]
            levelHighScores[0] = HighScore(player, time)
        } else if (levelHighScores[1]?.time?:Float.MAX_VALUE > time) {
            levelHighScores[2] = levelHighScores[1]
            levelHighScores[1] = HighScore(player, time)
        } else if (levelHighScores[2]?.time?:Float.MAX_VALUE > time) {
            levelHighScores[2] = HighScore(player, time)
        }
    }
}