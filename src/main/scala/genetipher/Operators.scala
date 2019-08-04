package genetipher

import geneticsearch.Types.FitnessOp


object Operators {

    def subsFitnessOp(ciphertext: String): FitnessOp[Char] = {
        genotype => {
            val plaintext = Util.buildPlaintext(ciphertext, genotype.elems)
            val score = EnglishScore.score(plaintext)

            println(score)

            score
        }
    }

}
