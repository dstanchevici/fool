package fool
// A game of fool.

fun main(){
    // Get players.
    val sam = Player("Sam")
    val hal = Player("Hal")
    val players = listOf(sam, hal)

    // Start game.
    val game = Game(players)
    game.play()

}