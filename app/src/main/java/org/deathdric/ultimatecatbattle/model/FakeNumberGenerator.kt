package org.deathdric.ultimatecatbattle.model

class FakeNumberGenerator(private val setNumbers: List<Int>) : NumberGenerator {
    private var curIndex = 0
    override fun roll(min: Int, max: Int): Int {
        return setNumbers[curIndex++]
    }
}