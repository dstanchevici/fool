package fool

class Card (val rank: Int, val suit: String) {
    override fun toString(): String {
        return when (rank) {
            11 -> "[J of $suit]"
            12 -> "[Q of $suit]"
            13 -> "[K of $suit]"
            14 -> "[A of $suit]"
            else -> "[${rank} of $suit]"
        }
    }
}