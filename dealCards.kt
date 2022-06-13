package fool

fun dealCards(deck: MutableList<Card>, yourHand: MutableList<Card>, opponentsHand: MutableList<Card>){
    while (yourHand.size < 6){
        if (deck.isNotEmpty()){
            yourHand.add(deck.first())
            deck.removeFirst()
        } else {
            println("The deck is empty.")
        }
    }

    while (opponentsHand.size < 6){
        if (deck.isNotEmpty()){
            opponentsHand.add(deck.first())
            deck.removeFirst()
        } else {
            println("The deck is empty.")
        }
    }

}