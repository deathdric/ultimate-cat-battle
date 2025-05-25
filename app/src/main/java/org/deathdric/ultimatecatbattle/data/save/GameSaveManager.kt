package org.deathdric.ultimatecatbattle.data.save

import android.util.Log
import com.google.gson.GsonBuilder
import org.deathdric.ultimatecatbattle.model.game.Game
import org.deathdric.ultimatecatbattle.model.save.SavedGame
import java.io.File
import java.io.FileReader
import java.io.FileWriter

const val SAVE_FILE_NAME = "autosave.json"
const val GAME_SAVE_TAG = "GameSave"

class GameSaveManager (private val rootDir : File) {



    fun saveGame(game: Game) {
        Log.i(GAME_SAVE_TAG, "Saving game")
        val savedGame = GameSaveConverter.exportGameData(game)
        val path = fetchSaveDir()
        val autosaveFile = File(path, SAVE_FILE_NAME)
        if (autosaveFile.exists()) {
            autosaveFile.delete()
        }
        FileWriter(autosaveFile).use { fw ->
            GsonBuilder().create().toJson(savedGame, fw)
        }
    }

    fun hasSave() : Boolean {
        try {
            val path = fetchSaveDir()
            val autosaveFile = File(path, SAVE_FILE_NAME)
            return autosaveFile.exists()
        } catch (ex: Exception) {
            Log.e(GAME_SAVE_TAG, "Exception while checking save", ex)
            return false
        }
    }

    fun deleteSave() {
        try {
            val path = fetchSaveDir()
            val autosaveFile = File(path, SAVE_FILE_NAME)
            if (autosaveFile.exists()) {
                autosaveFile.delete()
            }
        } catch (ex: Exception) {
            Log.e(GAME_SAVE_TAG, "Exception while deleting save", ex)
        }
    }

    fun loadGame() : Game? {
        val path = fetchSaveDir()
        val autosaveFile = File(path, SAVE_FILE_NAME)
        if (!autosaveFile.exists()) {
            Log.i(GAME_SAVE_TAG, "No game to load")
            return null
        }
        Log.i(GAME_SAVE_TAG, "Loading game")
        try {
            FileReader(autosaveFile).use { fr ->
                val savedGame = GsonBuilder().create().fromJson(fr, SavedGame::class.java)
                val game = GameSaveConverter.importGameData(savedGame)
                return game
            }
        } catch (ex: Exception) {
            Log.e(GAME_SAVE_TAG, "Exception while loading game", ex)
            // Corrupt saved file
            try {
                Log.i(GAME_SAVE_TAG, "Exception while deleting corrupted save file", ex)
                autosaveFile.delete()
            } catch (ex2 : Exception) {
                // Ignore failed delete attempts
            }
            throw ex
        }

    }

    private fun fetchSaveDir() : File {
        val saveRootDir = File(rootDir, "Saves")
        saveRootDir.mkdirs()
        return saveRootDir
    }
}