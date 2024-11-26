package org.deathdric.ultimatecatbattle.data

import org.deathdric.ultimatecatbattle.model.AttackActionId
import org.deathdric.ultimatecatbattle.model.PlayerId
import org.deathdric.ultimatecatbattle.model.PlayerTemplate
import org.deathdric.ultimatecatbattle.model.SupportActionId

object PlayerRepository {

    private val cat = PlayerTemplate (
        PlayerId.CAT,
        attackActions = listOf(
            AttackActionId.DRAGON_FIST,
            AttackActionId.FINGER_OF_DEATH,
            AttackActionId.GREAT_EXPLOSION,
            AttackActionId.SWEEPING_KICK,
            AttackActionId.HEART_STRIKE
        ),
        supportActions = listOf(
            SupportActionId.BERSERK,
            SupportActionId.GUARD,
            SupportActionId.SPEED
        )
    )

    private val penguin = PlayerTemplate (
        PlayerId.PENGUIN,
        attackActions = listOf(
            AttackActionId.DRAGON_FIST,
            AttackActionId.BEAK_STRIKE,
            AttackActionId.GREAT_EXPLOSION,
            AttackActionId.SWEEPING_KICK,
            AttackActionId.HEART_STRIKE
        ),
        supportActions = listOf(
            SupportActionId.BERSERK,
            SupportActionId.GUARD,
            SupportActionId.SPEED
        )
    )

    private val rabbit = PlayerTemplate (
        PlayerId.RABBIT,
        attackActions = listOf(
            AttackActionId.DRAGON_FIST,
            AttackActionId.FINGER_OF_DEATH,
            AttackActionId.GREAT_EXPLOSION,
            AttackActionId.SWEEPING_KICK,
            AttackActionId.HEART_STRIKE
        ),
        supportActions = listOf(
            SupportActionId.BERSERK,
            SupportActionId.GUARD,
            SupportActionId.SPEED
        )
    )

    private val mouse = PlayerTemplate (
        PlayerId.MOUSE,
        attackActions = listOf(
            AttackActionId.DRAGON_FIST,
            AttackActionId.FINGER_OF_DEATH,
            AttackActionId.GREAT_EXPLOSION,
            AttackActionId.SWEEPING_KICK,
            AttackActionId.HEART_STRIKE
        ),
        supportActions = listOf(
            SupportActionId.BERSERK,
            SupportActionId.GUARD,
            SupportActionId.SPEED
        )
    )

    fun getPlayer(id: PlayerId) : PlayerTemplate {
        return when(id) {
            PlayerId.CAT -> cat
            PlayerId.PENGUIN -> penguin
            PlayerId.RABBIT -> rabbit
            PlayerId.MOUSE -> mouse
        }
    }
}