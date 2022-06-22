package fool
// A game of fool.

fun main(){
    // Get players.
    val players = arrayOf(
        Player("Sam"),
        Player("Hal")
    )

    // Start game.
    val game = Game(players)
    game()
}