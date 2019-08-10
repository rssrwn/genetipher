package genetipher

import geneticsearch.Types.{CompletionOp, FitnessOp, SelectionOp}
import geneticsearch.genotype.Genotype

import scala.util.Random


object Operators {

    def subsFitnessOp(ciphertext: String): FitnessOp[Char] = {
        genotype => {
            val plaintext = Util.buildPlaintext(ciphertext, genotype.elems)
            EnglishScore.avgNgramScore(plaintext)
        }
    }

    def avgNgramScoreCompletionOp(threshold: Double): CompletionOp[Char] = {
        evalPop => {
            evalPop.exists(evalGenotype => evalGenotype._2 >= threshold)
        }
    }

    def tournament[T](numToSelect: Int, elitism: Int = 0): SelectionOp[T] = {
        evalPop => {
            val popSize = evalPop.length
            val initPop = if (elitism > 0) {
                val bestElem = evalPop.sortWith(_._2 < _._2).head
                (for (_ <- 0 until elitism) yield bestElem).toVector
            } else {
                Vector.empty[(Genotype[T], Double)]
            }

            (0 until numToSelect).foldLeft(initPop) { case (pop, _) =>
                val elem1 = evalPop(Random.nextInt(popSize))
                val elem2 = evalPop(Random.nextInt(popSize))

                if (elem1._2 > elem2._2) {
                    pop :+ elem1
                } else {
                    pop :+ elem2
                }
            }
        }
    }

}
