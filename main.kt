package fool
// A game of fool.

fun getDeck (): MutableList<Card> {
    val suits = listOf("Spades", "Clubs", "Hearts", "Diamonds")
    val deck = mutableListOf<Card>()

    for (s in suits){
        for (rank in 6..14){
            val card = Card(rank = rank, suit = s)
            deck.add(card)
        }
    }

    deck.shuffle()
    return deck
}

fun main(){
    // Get the deck.
    val deck = getDeck()

    // Get players.
    val player1 = Player("You")
    val player2 = Player("Hal")
    val players = listOf(player1, player2)

    // Start the game.
    val game = Game(deck, players)
    game.dealCards()
    game.printYourHandAndTrump()
}