package org.deathdric.ultimatecatbattle.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class GameTest {

    @Test
    fun should_find_player_and_team() {
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val penguin = Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        val rabbit = Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        val firstTeam = Team(listOf(cat, penguin), TeamId.FISH_BLUE)
        val secondTeam = Team(listOf(rabbit, mouse), TeamId.BALL_GREEN)
        val game = Game(listOf(firstTeam, secondTeam))
        val foundPlayer = game.findPlayer(PlayerId.CAT)
        assertTrue(foundPlayer.isPresent)
        assertEquals(cat, foundPlayer.get())
        val foundTeam = game.findTeam(cat)
        assertTrue(foundTeam.isPresent)
        assertEquals(firstTeam, foundTeam.get())
    }

    @Test
    fun should_find_self() {
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val penguin = Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        val rabbit = Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        val firstTeam = Team(listOf(cat, penguin), TeamId.FISH_BLUE)
        val secondTeam = Team(listOf(rabbit, mouse), TeamId.BALL_GREEN)
        val game = Game(listOf(firstTeam, secondTeam))
        val targets = game.findTargets(PlayerId.CAT, TargetType.SELF)
        assertTrue(targets.contains(cat))
        assertEquals(1, targets.size)
        assertTrue(game.canAutoTarget(targets, TargetType.SELF))
    }

    @Test
    fun should_find_all_allies() {
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val penguin = Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        val rabbit = Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        val firstTeam = Team(listOf(cat, penguin), TeamId.FISH_BLUE)
        val secondTeam = Team(listOf(rabbit, mouse), TeamId.BALL_GREEN)
        val game = Game(listOf(firstTeam, secondTeam))
        val targets = game.findTargets(PlayerId.CAT, TargetType.ALL_ALLIES)
        assertTrue(targets.contains(cat))
        assertTrue(targets.contains(penguin))
        assertEquals(2, targets.size)
        assertTrue(game.canAutoTarget(targets, TargetType.ALL_ALLIES))
    }

    @Test
    fun should_find_one_ally_no_auto() {
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val penguin = Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        val rabbit = Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        val firstTeam = Team(listOf(cat, penguin), TeamId.FISH_BLUE)
        val secondTeam = Team(listOf(rabbit, mouse), TeamId.BALL_GREEN)
        val game = Game(listOf(firstTeam, secondTeam))
        val targets = game.findTargets(PlayerId.CAT, TargetType.ONE_ALLY)
        assertTrue(targets.contains(cat))
        assertTrue(targets.contains(penguin))
        assertEquals(2, targets.size)
        assertFalse(game.canAutoTarget(targets, TargetType.ONE_ALLY))
    }

    @Test
    fun should_find_all_enemies() {
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val penguin = Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        val rabbit = Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        val firstTeam = Team(listOf(cat, penguin), TeamId.FISH_BLUE)
        val secondTeam = Team(listOf(rabbit, mouse), TeamId.BALL_GREEN)
        val game = Game(listOf(firstTeam, secondTeam))
        val targets = game.findTargets(PlayerId.CAT, TargetType.ALL_ENEMIES)
        assertTrue(targets.contains(mouse))
        assertTrue(targets.contains(rabbit))
        assertEquals(2, targets.size)
        assertTrue(game.canAutoTarget(targets, TargetType.ALL_ENEMIES))
    }

    @Test
    fun should_find_one_enemy_no_auto() {
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val penguin = Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        val rabbit = Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        val firstTeam = Team(listOf(cat, penguin), TeamId.FISH_BLUE)
        val secondTeam = Team(listOf(rabbit, mouse), TeamId.BALL_GREEN)
        val game = Game(listOf(firstTeam, secondTeam))
        val targets = game.findTargets(PlayerId.CAT, TargetType.ONE_ENEMY)
        assertTrue(targets.contains(mouse))
        assertTrue(targets.contains(rabbit))
        assertEquals(2, targets.size)
        assertFalse(game.canAutoTarget(targets, TargetType.ONE_ENEMY))
    }

    @Test
    fun should_find_one_alive_enemy() {
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val penguin = Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        val rabbit = Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        val firstTeam = Team(listOf(cat, penguin), TeamId.FISH_BLUE)
        val secondTeam = Team(listOf(rabbit, mouse), TeamId.BALL_GREEN)
        val game = Game(listOf(firstTeam, secondTeam))

        // Kill one opponent, only one should remain
        mouse.applyDamage(200)
        val targets = game.findTargets(PlayerId.CAT, TargetType.ONE_ENEMY)
        assertTrue(targets.contains(rabbit))
        assertEquals(1, targets.size)
        assertTrue(game.canAutoTarget(targets, TargetType.ONE_ENEMY))
    }

    @Test
    fun should_find_all_others() {
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val penguin = Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        val rabbit = Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        val firstTeam = Team(listOf(cat, penguin), TeamId.FISH_BLUE)
        val secondTeam = Team(listOf(rabbit, mouse), TeamId.BALL_GREEN)
        val game = Game(listOf(firstTeam, secondTeam))
        val targets = game.findTargets(PlayerId.CAT, TargetType.ALL_OTHERS)
        assertTrue(targets.contains(mouse))
        assertTrue(targets.contains(rabbit))
        assertTrue(targets.contains(penguin))
        assertEquals(3, targets.size)
        assertTrue(game.canAutoTarget(targets, TargetType.ALL_OTHERS))
    }

    @Test
    fun should_handle_game_over_one_survivor() {
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val penguin = Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        val rabbit = Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        val firstTeam = Team(listOf(cat, penguin), TeamId.FISH_BLUE)
        val secondTeam = Team(listOf(rabbit), TeamId.BALL_GREEN)
        val thirdTeam = Team(listOf(mouse), TeamId.MOON_BROWN)
        val game = Game(listOf(firstTeam, secondTeam, thirdTeam))
        assertFalse(game.isGameOver)

        // Kill the rabbit, one less team
        rabbit.applyDamage(200)
        assertFalse(game.isGameOver)

        // Kill the cat, team 1 is still standing
        cat.applyDamage(200)
        assertFalse(game.isGameOver)

        // Kill the mouse, penguin lives
        mouse.applyDamage(200)
        assertTrue(game.isGameOver)
    }

    @Test
    fun should_handle_game_over_full_team_survives() {
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val penguin = Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        val rabbit = Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        val firstTeam = Team(listOf(cat, penguin), TeamId.FISH_BLUE)
        val secondTeam = Team(listOf(rabbit, mouse), TeamId.BALL_GREEN)
        val game = Game(listOf(firstTeam, secondTeam))
        assertFalse(game.isGameOver)

        // Kill the rabbit
        rabbit.applyDamage(200)
        assertFalse(game.isGameOver)

        // Kill the mouse
        mouse.applyDamage(200)
        assertTrue(game.isGameOver)
    }

    @Test
    fun should_compute_active_player() {
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val penguin = Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        val rabbit = Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        val firstTeam = Team(listOf(cat, penguin), TeamId.FISH_BLUE)
        val secondTeam = Team(listOf(rabbit, mouse), TeamId.BALL_GREEN)
        val game = Game(listOf(firstTeam, secondTeam))
        penguin.applyDelay(10)
        rabbit.applyDelay(20)
        mouse.applyDelay(20)
        assertNull(game.curActivePlayer)
        assertEquals(0, game.curTime)

        // Cat should play first
        assertTrue(game.updateActivePlayer())
        assertEquals(cat, game.curActivePlayer)
        assertEquals(0, game.curTime)

        // Small move, still cat's turn
        cat.applyDelay(5)
        assertFalse(game.updateActivePlayer())
        assertEquals(cat, game.curActivePlayer)
        assertEquals(5, game.curTime)

        // Small move and same delay as penguin, still cat's turn
        cat.applyDelay(5)
        assertFalse(game.updateActivePlayer())
        assertEquals(cat, game.curActivePlayer)
        assertEquals(10, game.curTime)

        // Larger move, penguin's turn
        // Small move and same delay as penguin, still cat's turn
        cat.applyDelay(30)
        assertTrue(game.updateActivePlayer())
        assertEquals(penguin, game.curActivePlayer)
        assertEquals(10, game.curTime)

        // Penguin uses big move, rabbit's turn
        penguin.applyDelay(30)
        assertTrue(game.updateActivePlayer())
        assertEquals(rabbit, game.curActivePlayer)
        assertEquals(20, game.curTime)
    }
}