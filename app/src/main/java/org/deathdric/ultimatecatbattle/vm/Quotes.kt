package org.deathdric.ultimatecatbattle.vm

import org.deathdric.ultimatecatbattle.R
import org.deathdric.ultimatecatbattle.model.NumberGenerator
import org.deathdric.ultimatecatbattle.model.RandomNumberGenerator

open class QuoteGenerator(private val allCitations: List<Int>) {
    fun pickRandomQuote(numberGenerator: NumberGenerator = RandomNumberGenerator()) : Int {
        val chosenIndex = numberGenerator.roll(0, this.allCitations.size)
        return this.allCitations[chosenIndex]
    }
}

object StartTurnQuotes : QuoteGenerator(
    listOf(
        R.string.start_turn_quote_1,
        R.string.start_turn_quote_2,
        R.string.start_turn_quote_3,
        R.string.start_turn_quote_4,
        R.string.start_turn_quote_5,
        R.string.start_turn_quote_6,
        R.string.start_turn_quote_7,
        R.string.start_turn_quote_8,
        R.string.start_turn_quote_9,
        R.string.start_turn_quote_10
    )
)

object GameOverQuotes : QuoteGenerator(
    listOf(
        R.string.game_over_quote_1,
        R.string.game_over_quote_2
    )
)

object AttackSingleQuotes : QuoteGenerator(
    listOf(
        R.string.attack_quote_shared_1,
        R.string.attack_quote_single_1,
        R.string.attack_quote_single_2,
        R.string.attack_quote_single_3,
        R.string.attack_quote_single_4,
        R.string.attack_quote_single_5,
        R.string.attack_quote_single_6

    )
)

object AttackMultiQuotes : QuoteGenerator (
    listOf(
        R.string.attack_quote_shared_1,
        R.string.attack_quote_multi_1,
        R.string.attack_quote_multi_2,
        R.string.attack_quote_multi_3,
        R.string.attack_quote_multi_4
    )
)

object SupportSelfQuotes : QuoteGenerator (
    listOf(
        R.string.support_quote_self_1,
        R.string.support_quote_self_2,
        R.string.support_quote_self_3
    )
)

object SupportAllyQuotes : QuoteGenerator (
    listOf(
        R.string.support_quote_ally_1,
        R.string.support_quote_ally_2,
        R.string.support_quote_ally_3
    )
)