package org.deathdric.ultimatecatbattle.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PlayerTest {

    @Test
    fun should_handle_damage_and_liveliness() {

        // Init player
        val player = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        assertTrue(player.isAlive)

        // Deal damage, full damage taken into account
        player.applyDamage(100)
        assertTrue(player.isAlive)
        assertEquals(100, player.hitPoints)

        // Deal overkill damage, game over
        player.applyDamage(120)
        assertFalse(player.isAlive)
        assertEquals(player.hitPoints, 0)
    }

    @Test
    fun should_handle_healing() {
        // Init player
        val player = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)

        // Do some damage first
        player.applyDamage(100)
        assertEquals(100, player.hitPoints)

        // Do some healing: should apply full amount
        player.applyHealing(50)
        assertEquals(150, player.hitPoints)

        // Overheal, do not go over the max HP
        player.applyHealing(75)
        assertEquals(200, player.hitPoints)
    }

    @Test
    fun should_handle_status_expiration_and_caps() {
        // Init player
        val player = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        var curTime = 0
        val attackEffect = StatusEffect(StatusEffectType.ATTACK, 25);

        // Fist attack buff
        player.addEffect(generateStatusModifier(listOf(attackEffect), 50));
        player.applyEffectExpiration(curTime)
        assertEquals(25, player.attack)

        // Second attack buff, should cumulate
        player.addEffect(generateStatusModifier(listOf(attackEffect), 75))
        assertEquals(50, player.attack)

        // Third attack buff : stat is capped so no effect
        player.addEffect(generateStatusModifier(listOf(attackEffect), 100))
        assertEquals(50, player.attack)

        // First buff expires : should still cumulate second and third
        curTime = 60
        player.applyEffectExpiration(curTime)
        assertEquals(50, player.attack)

        // Second buff expires : only third buff remains
        curTime = 80
        player.applyEffectExpiration(curTime)
        assertEquals(25, player.attack)

        // Third buff expires : back to normal
        curTime = 100
        player.applyEffectExpiration(curTime)
        assertEquals(0, player.attack)

        // First attack debuff
        val attackDebuff = StatusEffect(StatusEffectType.ATTACK, -25);
        player.addEffect(generateStatusModifier(listOf(attackDebuff), 150))
        assertEquals(-25, player.attack)

        // Second attack debuff, should cumulate
        player.addEffect(generateStatusModifier(listOf(attackDebuff), 175))
        assertEquals(-50, player.attack)

        // Third attack debuff, stat is floor-capped so no effect
        player.addEffect(generateStatusModifier(listOf(attackDebuff), 175))
        assertEquals(-50, player.attack)
    }

    @Test
    fun should_handle_all_stat_effects() {
        val player = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val statusModifier = generateStatusModifier(listOf(
            StatusEffect(StatusEffectType.ATTACK, 10),
            StatusEffect(StatusEffectType.DEFENSE, -10),
            StatusEffect(StatusEffectType.HIT, 20),
            StatusEffect(StatusEffectType.AVOID, 5),
            StatusEffect(StatusEffectType.CRITICAL, 15)
        ), 100)

        player.addEffect(statusModifier)
        assertEquals(10, player.attack)
        assertEquals(-10, player.defense)
        assertEquals(20, player.hit)
        assertEquals(5, player.avoid)
        assertEquals(15, player.critical)
    }

    @Test
    fun should_update_delay() {
        val player = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        player.applyDelay(5)
        assertEquals(5, player.nextTime)
        player.applyDelay(12)
        assertEquals(17, player.nextTime)
    }
}