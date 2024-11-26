package org.deathdric.ultimatecatbattle.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class AttackActionTest {

    @Test
    fun should_generate_preview() {
        val attackAction = AttackAction(
            id = AttackActionId.HEART_STRIKE,
            targetType = TargetType.ALL_ENEMIES,
            minDamage = 16,
            maxDamage = 24,
            hit = 50,
            critical = 30,
            delay = 10,
            coolDown = 0
        )
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        val penguin =
            Player(id = PlayerId.PENGUIN, maxHitPoints = 15, playerType = PlayerType.PLAYER2)
        val rabbit =
            Player(id = PlayerId.RABBIT, maxHitPoints = 20, playerType = PlayerType.PLAYER3)
        cat.addEffect(StatusModifier(25, 0, 25, 0, 10, 100))
        penguin.addEffect(StatusModifier(0, 50, 0, 25, 0, 100))
        rabbit.addEffect(StatusModifier(0, -50, 0, 0, 0, 100))
        val preview = attackAction.simulate(cat, listOf(penguin, rabbit))
        assertEquals(cat, preview.player)
        assertEquals(attackAction, preview.attackAction)
        assertEquals(2, preview.targetedPreviews.size)

        val penguinPreview = preview.targetedPreviews[0]
        assertEquals(12, penguinPreview.minDamage)
        assertEquals(18, penguinPreview.maxDamage)
        assertEquals(50, penguinPreview.hit)
        assertEquals(40, penguinPreview.critical)
        assertFalse(penguinPreview.minCanKill)
        assertTrue(penguinPreview.maxCanKill)

        val rabbitPreview = preview.targetedPreviews[1]
        assertEquals(28, rabbitPreview.minDamage)
        assertEquals(42, rabbitPreview.maxDamage)
        assertEquals(75, rabbitPreview.hit)
        assertEquals(40, rabbitPreview.critical)
        assertTrue(rabbitPreview.minCanKill)
        assertTrue(rabbitPreview.maxCanKill)
    }

    @Test
    fun handle_attack_with_cooldown() {
        val attackAction = AttackAction(
            id = AttackActionId.HEART_STRIKE,
            targetType = TargetType.ALL_ENEMIES,
            minDamage = 16,
            maxDamage = 24,
            hit = 50,
            critical = 30,
            delay = 10,
            coolDown = 50
        )
        val curTime = 20
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        cat.applyDelay(curTime)
        val penguin =
            Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        val rabbit =
            Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        cat.addEffect(StatusModifier(25, 0, 25, 0, 10, 100))
        penguin.addEffect(StatusModifier(0, 50, 0, 25, 0, 100))
        rabbit.addEffect(StatusModifier(0, -50, 0, 0, 0, 100))

        val rng = FakeNumberGenerator(
            listOf(
                80, // penguin hit fail
                50, // rabbit hit success
                30, // rabbit damage
                20, // rabbit crit success
                40, // mouse hit success
                25, // mouse damage
                70 // mouse crit fail
            )
        )
        val attackActionResult =
            attackAction.apply(curTime, cat, listOf(penguin, rabbit, mouse), rng)

        // Cat status
        assertEquals(30, cat.nextTime)
        assertEquals(70, attackAction.nextAvailable)

        assertEquals(3, attackActionResult.targetedResults.size)

        // Penguin status
        val penguinResult = attackActionResult.targetedResults[0]
        assertEquals(200, penguin.hitPoints)
        assertEquals(AttackSuccessMode.FAILURE, penguinResult.successMode)
        assertEquals(0, penguinResult.damage)
        assertEquals(penguin, penguinResult.target)

        // Rabbit status
        val rabbitResult = attackActionResult.targetedResults[1]
        assertEquals(155, rabbit.hitPoints)
        assertEquals(AttackSuccessMode.CRITICAL, rabbitResult.successMode)
        assertEquals(45, rabbitResult.damage)
        assertEquals(rabbit, rabbitResult.target)

        // Mouse status
        val mouseResult = attackActionResult.targetedResults[2]
        assertEquals(175, mouse.hitPoints)
        assertEquals(AttackSuccessMode.SUCCESS, mouseResult.successMode)
        assertEquals(25, mouseResult.damage)
        assertEquals(mouse, mouseResult.target)
    }

    @Test
    fun handle_attack_with_delay() {
        val attackAction = AttackAction(
            id = AttackActionId.HEART_STRIKE,
            targetType = TargetType.ALL_ENEMIES,
            minDamage = 16,
            maxDamage = 24,
            hit = 50,
            critical = 30,
            delay = 10,
            coolDown = 0,
            delayEffect = DelayEffect(30, 20)
        )
        val curTime = 20
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        cat.applyDelay(curTime)
        val penguin =
            Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        penguin.applyDelay(30)
        val rabbit =
            Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        rabbit.applyDelay(40)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        mouse.applyDelay(50)

        val rng = FakeNumberGenerator(
            listOf(
                30, // penguin hit success
                20, // penguin damage
                70, // penguin crit fail
                80, // penguin delay fail
                40, // rabbit hit success
                20, // rabbit damage
                20, // rabbit crit success
                20, // rabbit delay success
                40, // mouse hit success
                20, // mouse damage
                70, // mouse crit fail
                20  // mouse delay success
            )
        )

        val attackActionResult =
            attackAction.apply(curTime, cat, listOf(penguin, rabbit, mouse), rng)

        // Cat status
        assertEquals(30, cat.nextTime)
        assertEquals(20, attackAction.nextAvailable)

        // Penguin status
        val penguinResult = attackActionResult.targetedResults[0]
        assertEquals(180, penguin.hitPoints)
        assertEquals(AttackSuccessMode.SUCCESS, penguinResult.successMode)
        assertEquals(20, penguinResult.damage)
        assertEquals(0, penguinResult.delay)
        assertEquals(30, penguin.nextTime)

        // Rabbit status
        val rabbitResult = attackActionResult.targetedResults[1]
        assertEquals(170, rabbit.hitPoints)
        assertEquals(AttackSuccessMode.CRITICAL, rabbitResult.successMode)
        assertEquals(30, rabbitResult.damage)
        assertEquals(30, rabbitResult.delay)
        assertEquals(70, rabbit.nextTime)

        // Mouse status
        val mouseResult = attackActionResult.targetedResults[2]
        assertEquals(180, mouse.hitPoints)
        assertEquals(AttackSuccessMode.SUCCESS, mouseResult.successMode)
        assertEquals(20, mouseResult.damage)
        assertEquals(mouse, mouseResult.target)
    }

    @Test
    fun handle_attack_with_status() {
        val attackAction = AttackAction(
            id = AttackActionId.HEART_STRIKE,
            targetType = TargetType.ALL_ENEMIES,
            minDamage = 16,
            maxDamage = 24,
            hit = 50,
            critical = 30,
            delay = 10,
            coolDown = 0,
            statusEffect = AttackStatusEffect(50, 100, listOf(
                StatusEffect(StatusEffectType.AVOID, -10),
                StatusEffect(StatusEffectType.DEFENSE, effectValue = -20)
            ))
        )
        val curTime = 20
        val cat = Player(id = PlayerId.CAT, maxHitPoints = 200, playerType = PlayerType.PLAYER1)
        cat.applyDelay(curTime)
        val penguin =
            Player(id = PlayerId.PENGUIN, maxHitPoints = 200, playerType = PlayerType.PLAYER2)
        penguin.applyDelay(30)
        val rabbit =
            Player(id = PlayerId.RABBIT, maxHitPoints = 200, playerType = PlayerType.PLAYER3)
        rabbit.applyDelay(40)
        val mouse = Player(id = PlayerId.MOUSE, maxHitPoints = 200, playerType = PlayerType.PLAYER4)
        mouse.applyDelay(50)

        val rng = FakeNumberGenerator(
            listOf(
                30, // penguin hit success
                20, // penguin damage
                70, // penguin crit fail
                80, // penguin status fail
                40, // rabbit hit success
                20, // rabbit damage
                20, // rabbit crit success
                20, // rabbit status success
                40, // mouse hit success
                20, // mouse damage
                70, // mouse crit fail
                20  // mouse status success
            )
        )

        val attackActionResult =
            attackAction.apply(curTime, cat, listOf(penguin, rabbit, mouse), rng)
        penguin.applyEffectExpiration(curTime)
        rabbit.applyEffectExpiration(curTime)
        mouse.applyEffectExpiration(curTime)

        // Cat status
        assertEquals(30, cat.nextTime)
        assertEquals(20, attackAction.nextAvailable)

        // Penguin status
        val penguinResult = attackActionResult.targetedResults[0]
        assertEquals(180, penguin.hitPoints)
        assertEquals(AttackSuccessMode.SUCCESS, penguinResult.successMode)
        assertEquals(20, penguinResult.damage)
        assertNull(penguinResult.statusEffect)
        assertEquals(0, penguin.defense)
        assertEquals(0, penguin.avoid)

        // Rabbit status
        val rabbitResult = attackActionResult.targetedResults[1]
        assertEquals(170, rabbit.hitPoints)
        assertEquals(AttackSuccessMode.CRITICAL, rabbitResult.successMode)
        assertEquals(30, rabbitResult.damage)
        assertNotNull(rabbitResult.statusEffect)
        assertEquals(-20, rabbitResult.statusEffect?.defense)
        assertEquals(-10, rabbitResult.statusEffect?.avoid)
        assertEquals(-20, rabbit.defense)
        assertEquals(-10, rabbit.avoid)
        assertEquals(170, rabbitResult.statusEffect?.expires)

        // Mouse status
        val mouseResult = attackActionResult.targetedResults[2]
        assertEquals(180, mouse.hitPoints)
        assertEquals(AttackSuccessMode.SUCCESS, mouseResult.successMode)
        assertEquals(20, mouseResult.damage)
        assertNotNull(mouseResult.statusEffect)
        assertEquals(-20, mouseResult.statusEffect?.defense)
        assertEquals(-10, mouseResult.statusEffect?.avoid)
        assertEquals(-20, mouse.defense)
        assertEquals(-10, mouse.avoid)
        assertEquals(120, mouseResult.statusEffect?.expires)
    }
}