package genetipher

import geneticsearch.Types.FitnessOp


object Operators {

    def subsFitnessOp(ciphertext: String): FitnessOp[Char] = {
        genotype => {
            val plaintext = Util.buildPlaintext(ciphertext, genotype.elems)
            EnglishScore.score(plaintext)
        }
    }

}
