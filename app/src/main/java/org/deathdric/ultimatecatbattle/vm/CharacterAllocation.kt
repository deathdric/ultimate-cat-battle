package org.deathdric.ultimatecatbattle.vm

import org.deathdric.ultimatecatbattle.model.PlayerId
import org.deathdric.ultimatecatbattle.model.PlayerType

data class CharacterAllocation(val playerId: PlayerId, val enable: Boolean, val playerType: PlayerType)

fun createCharacterAllocation(nbPlayers: Int) : MutableMap<PlayerId, CharacterAllocation> {
    return when(nbPlayers) {
        1 -> {
            mutableMapOf(
                Pair(PlayerId.CAT, CharacterAllocation(PlayerId.CAT, true, PlayerType.PLAYER1)),
                Pair(PlayerId.PENGUIN, CharacterAllocation(PlayerId.PENGUIN, true, PlayerType.COMPUTER)),
                Pair(PlayerId.RABBIT, CharacterAllocation(PlayerId.RABBIT, false, PlayerType.COMPUTER)),
                Pair(PlayerId.MOUSE, CharacterAllocation(PlayerId.MOUSE, false, PlayerType.COMPUTER))
            )
        }
        2 -> {
            mutableMapOf(
                Pair(PlayerId.CAT, CharacterAllocation(PlayerId.CAT, true, PlayerType.PLAYER1)),
                Pair(PlayerId.PENGUIN, CharacterAllocation(PlayerId.PENGUIN, true, PlayerType.PLAYER2)),
                Pair(PlayerId.RABBIT, CharacterAllocation(PlayerId.RABBIT, false, PlayerType.COMPUTER)),
                Pair(PlayerId.MOUSE, CharacterAllocation(PlayerId.MOUSE, false, PlayerType.COMPUTER))
            )
        }
        3 -> {
            mutableMapOf(
                Pair(PlayerId.CAT, CharacterAllocation(PlayerId.CAT, true, PlayerType.PLAYER1)),
                Pair(PlayerId.PENGUIN, CharacterAllocation(PlayerId.PENGUIN, true, PlayerType.PLAYER2)),
                Pair(PlayerId.RABBIT, CharacterAllocation(PlayerId.RABBIT, true, PlayerType.PLAYER3)),
                Pair(PlayerId.MOUSE, CharacterAllocation(PlayerId.MOUSE, false, PlayerType.COMPUTER))
            )
        }
        else -> {
            mutableMapOf(
                Pair(PlayerId.CAT, CharacterAllocation(PlayerId.CAT, true, PlayerType.PLAYER1)),
                Pair(PlayerId.PENGUIN, CharacterAllocation(PlayerId.PENGUIN, true, PlayerType.PLAYER2)),
                Pair(PlayerId.RABBIT, CharacterAllocation(PlayerId.RABBIT, true, PlayerType.PLAYER3)),
                Pair(PlayerId.MOUSE, CharacterAllocation(PlayerId.MOUSE, true, PlayerType.PLAYER4))
            )
        }
    }
}

data class CharacterAllocationState(
    val characterAllocation: CharacterAllocation,
    val canDisable: Boolean,
    val availablePlayers: List<PlayerType>
)

data class CharacterAllocationValidation(
    val validated: Boolean,
    val missingPlayerCharacters: PlayerType?
)

data class GlobalCharacterAllocationState(
    val characters: Map<PlayerId, CharacterAllocationState>,
    val validation: CharacterAllocationValidation
)

fun validateCharacterAllocation(allocations: Map<PlayerId, CharacterAllocation>, players: Int) : CharacterAllocationValidation {
    val player1Chars = allocations.values.stream().filter { it.enable && it.playerType == PlayerType.PLAYER1 }.count()
    val player2Chars = allocations.values.stream().filter { it.enable && it.playerType == PlayerType.PLAYER2 }.count()
    val player3Chars = allocations.values.stream().filter { it.enable && it.playerType == PlayerType.PLAYER3 }.count()
    val player4Chars = allocations.values.stream().filter { it.enable && it.playerType == PlayerType.PLAYER4 }.count()
    val computerChars = allocations.values.stream().filter { it.enable && it.playerType == PlayerType.COMPUTER }.count()
    if (player1Chars == 0L) {
        return CharacterAllocationValidation(false, missingPlayerCharacters = PlayerType.PLAYER1)
    }
    if (players == 1) {
        return if (computerChars == 0L)
            CharacterAllocationValidation(false, missingPlayerCharacters = PlayerType.COMPUTER)
        else CharacterAllocationValidation(true, null)
    }
    if (player2Chars == 0L) {
        return CharacterAllocationValidation(false, missingPlayerCharacters = PlayerType.PLAYER2)
    }

    if (players == 2) {
        return CharacterAllocationValidation(true, null)
    }

    if (player3Chars == 0L) {
        return CharacterAllocationValidation(false, missingPlayerCharacters = PlayerType.PLAYER3)
    }

    if (players == 3) {
        return CharacterAllocationValidation(true, null)
    }

    return if (player4Chars == 0L)
        CharacterAllocationValidation(false, missingPlayerCharacters = PlayerType.PLAYER4)
    else CharacterAllocationValidation(true, null)
}

fun createGlobalCharacterAllocationState(allocations: Map<PlayerId, CharacterAllocation>, players: Int) : GlobalCharacterAllocationState {
    val activeCharacters = allocations.values.stream().filter { it.enable }.count()
    val availablePlayers = when(players) {
        1 -> listOf(PlayerType.PLAYER1, PlayerType.COMPUTER)
        2 -> {
            if (activeCharacters == 2L) {
                listOf(PlayerType.PLAYER1, PlayerType.PLAYER2)
            } else {
                listOf(PlayerType.PLAYER1, PlayerType.PLAYER2, PlayerType.COMPUTER)
            }
        }
        3 -> {
            if (activeCharacters == 3L) {
                listOf(
                    PlayerType.PLAYER1,
                    PlayerType.PLAYER2,
                    PlayerType.PLAYER3
                )
            } else {
                listOf(
                    PlayerType.PLAYER1,
                    PlayerType.PLAYER2,
                    PlayerType.PLAYER3,
                    PlayerType.COMPUTER
                )
            }
        }
        else -> listOf(PlayerType.PLAYER1, PlayerType.PLAYER2, PlayerType.PLAYER3, PlayerType.PLAYER4)
    }

    val allocationStateMap = mapOf(
        Pair(PlayerId.CAT, CharacterAllocationState(allocations[PlayerId.CAT]!!, false, availablePlayers)),
        Pair(PlayerId.PENGUIN, CharacterAllocationState(allocations[PlayerId.PENGUIN]!!, false, availablePlayers)),
        Pair(PlayerId.RABBIT, CharacterAllocationState(allocations[PlayerId.RABBIT]!!, players < 3, availablePlayers)),
        Pair(PlayerId.MOUSE, CharacterAllocationState(allocations[PlayerId.MOUSE]!!, players < 4, availablePlayers))
    )

    return GlobalCharacterAllocationState(allocationStateMap,
        validateCharacterAllocation(allocations, players)
    )
}
