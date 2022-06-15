package fool

fun getDeck(players: List<Player>): MutableList<Card>{
    val suits = listOf("S", "C", "H", "D")
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
fun dealCards(deck: MutableList<Card>, players: List<Player>) {
    for (player in players){
        while (player.hand.size < 6){
            if (deck.isNotEmpty()){
                player.hand.add(deck.first())
                deck.removeFirst()
            } else {
                println("The deck is empty.")
            }
        }
    }
}

class Game(val players: List<Player>) {
    val deck = getDeck(players)

    val turnUp: Card
        get() {
            val turnUp = deck.first()
            deck.removeFirst()
            deck.add(turnUp)

            return turnUp
        }


    fun play(){
        dealCards(deck, players)

    }
}