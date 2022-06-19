package fool

import kotlin.random.Random


class Game(val players: List<Player>) {
    private val deck = createDeck()

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
            for (card in p.hand){
                if (card.suit == trump && card.rank < lowestTrump){
                    lowestTrump = card.rank
                    attacker = p
                }
            }

        }
        println("Lowest trump in hands: $lowestTrump of $trump")
        return attacker
    }

    fun play(){
        dealCards()
        val turnUp = deck.last()
        println("Turnup: $turnUp")
        deck.forEach { println(it) }

        println("\n${players[0].name}'s Cards:")
        players[0].showHand()

        println("\n${players[1].name}'s Cards:")
        players[1].showHand()

        val attacker = findFirstAttacker(turnUp.suit)
        if (attacker != null)
            println("First attacker is ${attacker.name}")
        else
            println("No trumps in hands")

    }
}

// test
fun main(){
    // Get players.
    val sam = Player("Sam")
    val hal = Player("Hal")
    val players = listOf(sam, hal)

    // Start game.
    val game = Game(players)
    game.play()
}

/*
    private fun findFirstAttacker(trump: String): Player{
        val lowestTrumps = Array<Card?>(players.size) {null}

        for (i in players.indices){
            var playerLowestTrump: Card? = null

            for (card in players[i].hand){
                if (playerLowestTrump == null && card.suit == trump){
                    playerLowestTrump = card
                }

                if (playerLowestTrump != null && card.suit == trump){
                    if (card.rank < playerLowestTrump.rank)
                        playerLowestTrump = card
                }
            }

            lowestTrumps[i] = playerLowestTrump
        }

        // Print to check
        println("Lowest Trumps AFTER CALCULATION: ")
        lowestTrumps.forEach { print(it) }
        println("\n")
        // So far ok.

        var lowestRank = 14
        var indexOfSmallestTrump = 0
        var noTrumps = true
        for (i in 0 until lowestTrumps.size){
            if (lowestTrumps[i] == null){
                continue
            } else {
                noTrumps = false
                if (lowestTrumps[i]!!.rank <= lowestRank){
                    indexOfSmallestTrump = i
                    lowestRank = lowestTrumps[i]!!.rank
                }
            }

        }

        if (noTrumps){
            println("No trumps on hands found. The first attacker is chosen randomly.")
            return players[Random.nextInt(0, players.size)]
        }

        println("The smallest Trump at the start of game is: ${lowestTrumps[indexOfSmallestTrump]}")
        return players[indexOfSmallestTrump]
    }
 */