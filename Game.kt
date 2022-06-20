package fool

import kotlin.random.Random

class Game(private val players: List<Player>) {
    private fun createDeck(): MutableList<Card>{
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

    private fun dealCards() {
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

    private fun findFirstAttacker(trump: String): Player?{
        var lowestTrump = 14
        var attacker: Player? = null

        for (p in players){
            val pTrumps = p.hand.filter { it.suit == trump }
            pTrumps.forEach{
                if (it.rank < lowestTrump){
                    lowestTrump = it.rank
                    attacker = p
                }
            }
        }
        return attacker
    }

    private val deck = createDeck()

    fun play(){
        dealCards()
        val turnUp = deck.last()

        val firstAttacker = findFirstAttacker(turnUp.suit)
        if (firstAttacker != null)
            println("First attacker is ${firstAttacker.name}")
        else{
            val randomAttackerIndex = Random.nextInt(0, players.size)
            println("No trumps in hands. The first attacker is chosen randomly. It is ${players[randomAttackerIndex].name}")
        }

        val round = Round(players, turnUp)
    }
}

