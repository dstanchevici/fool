package fool
// A game of fool.

data class MyPlayer(val name: String, val numbers: IntArray)


/*
private fun findFirstPlayer2(players: List<MyPlayer>): MyPlayer?  =
    players.sortedBy { it.numbers.minOf { it } } . firstOrNull()



private fun findFirstPlayer(players: List<MyPlayer>): MyPlayer?  {
    var lowestNumber = 0
    var firstPLayer: MyPlayer? = null

    for(p in players) {
        val minNumber = p.numbers.minOf { it }
        if(minNumber < lowestNumber) {
            lowestNumber = minNumber
            firstPLayer = p
        }
    }

    return firstPLayer
}

 */


fun main(){
    // Get players.
    val players = listOf(
        Player("Sam"),
        Player("Hal")
    )

    // Start game.
    val game = Game(players)
    game.play()

}