package genetipher

import geneticsearch.Types.FitnessOp


object Operators {

    private val ASCII_UPPER_A = 65
    private val ASCII_LOWER_A = 97
    private val ASCII_UPPER_Z = 90

    def subsFitnessOp(cipherText: String): FitnessOp[Char] = {
        genotype => {
            val elems = genotype.elems
            val plaintext = cipherText.map { char: Char =>
                val ascii = char.toInt
                val idx = if (isUpperCase(char)) {
                    ascii - ASCII_UPPER_A
                } else {
                    ascii - ASCII_LOWER_A
                }

                elems(idx)
            }

            EnglishScore.score(plaintext)
        }
    }

    /**
      * Returns whether the char is uppercase or not
      * Pre: The character is a letter
      * @param char Character to be checked
      * @return Whether the char is uppercase or not
      */
    private def isUpperCase(char: Char): Boolean = {
        val ascii = char.toInt
        if (ascii >= ASCII_UPPER_A && ascii <= ASCII_UPPER_Z) {
            true
        } else {
            false
        }
    }

}
