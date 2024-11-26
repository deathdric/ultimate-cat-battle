package org.deathdric.ultimatecatbattle.model

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class TeamTest {
    @Test
    fun should_handle_damage_and_liveliness() {

        // Init team
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val penguin = Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        val team = Team(listOf(cat, penguin), TeamId.BALL_GREEN)
        assertTrue(team.isAlive)

        // Do non-lethal damage
        cat.applyDamage(100)
        penguin.applyDamage(150)
        assertTrue(team.isAlive)

        // Kill the cat (curiosity is bad), penguin is still on the ring
        cat.applyDamage(200)
        assertTrue(team.isAlive)

        // Kill the penguin, team has lost
        penguin.applyDamage(100)
        assertFalse(team.isAlive)
    }
}