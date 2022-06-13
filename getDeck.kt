package fool

fun getDeck (): MutableList<Card> {
    val suits = listOf("Spades", "Clubs", "Hearts", "Diamonds")
    val deck = mutableListOf<Card>()

    for (s in suits){
        for (i in 6..14){
            val card = Card(rank = i, suit = s)
            deck.add(card)
        }
    }
    deck.shuffle()
    return deck
}