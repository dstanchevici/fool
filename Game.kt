package fool

import kotlin.random.Random

class Game(private val players: Array<Player>) {
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

    private val deck = createDeck()
    private val turnUp = deck.last()

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

    private fun findFirstAttackerIndex(): Int?{
        val trump = turnUp.suit
        var lowestTrump = 14
        var attackerIndex: Int? = null

        for (i in players.indices){
            val pTrumps = players[i].hand.filter { it.suit == trump }
            pTrumps.forEach{
                if (it.rank < lowestTrump){
                    lowestTrump = it.rank
                    attackerIndex = i
                }
            }
        }
        return attackerIndex
    }

    private fun printTable(attacker: Player, defender: Player, playedCards: MutableList<Card>){
        repeat(29) {print("* ")}
        println()
        println()

        println("ATTACKER ${attacker.name}'s current hand:")
        attacker.showHand()
        println()

        println("DEFENDER ${defender.name}'s current hand:")
        defender.showHand()
        println()

        if (playedCards.isEmpty()){
            println("No cards have been played yet.")
        } else {
            println("ATTACKER PLAYS: \tDEFENDER COVERS WITH:")
            var counter = 1
            for (card in playedCards){
                if (counter % 2 != 0){
                    print("$card\t\t\t")
                    counter++
                } else{
                    print("$card\n")
                    counter++
                }

            }
        }

        println()
        repeat(10) {print("* ")}
        print("TURNUP: $turnUp ")
        repeat(11) {print("* ")}
        println()
        println()
    }

    private fun getInput(player: Player): Card {
        println("${player.name}, choose a card from your hand by entering its index number: ")
        var cardNumberString = readln()

        // cardNumberInt !in 1..player.hand.lastIndex+1
        while (cardNumberString.contains("""\D""".toRegex()) || cardNumberString.trim().toInt() !in 1..player.hand.lastIndex+1){
            println("Please double check and try again.")
            cardNumberString = readln()
        }

        return player.hand[cardNumberString.trim().toInt() - 1]
    }

    private fun playRound(attackerIndex: Int): Int {
        val defenderIndex = if (attackerIndex < players.lastIndex) attackerIndex+1 else 0
        val attacker = players[attackerIndex]
        val defender = players[defenderIndex]
        val playedCards = mutableListOf<Card>()

        printTable(attacker, defender, playedCards)
        val attackerCard = getInput(attacker)
        playedCards.add(attackerCard)
        attacker.hand.remove(attackerCard)
        printTable(attacker, defender, playedCards)


        return 0
    }

    fun play(){
        dealCards()
        var firstAttackerIndex = findFirstAttackerIndex()
        if (firstAttackerIndex == null)
            firstAttackerIndex = Random.nextInt(0, players.size)

        var nextAttacker = playRound(firstAttackerIndex)

    }

    operator fun invoke() { play() }
}

