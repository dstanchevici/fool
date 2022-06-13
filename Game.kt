package fool

class Game(val deck: MutableList<Card>, val players: List<Player>) {

    // Maintain the number of cards for every player at least 6 cards
    fun dealCards() {
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
}