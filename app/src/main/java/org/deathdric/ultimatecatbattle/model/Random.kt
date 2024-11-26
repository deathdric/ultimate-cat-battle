package org.deathdric.ultimatecatbattle.model

import kotlin.random.Random

interface NumberGenerator {
    fun roll(min: Int, max: Int): Int
}

// Yes, this is the class responsible for f***ing RNG
// If you have any complaints about the results, send them
// to whoever implemented the Random class, not me.
// And don't blame your lack of luck on some perceived
// computer cheating/bias/... :)
class RandomNumberGenerator: NumberGenerator {

    override fun roll(min: Int, max: Int): Int {
        return Random.nextInt(min, max)
    }

}