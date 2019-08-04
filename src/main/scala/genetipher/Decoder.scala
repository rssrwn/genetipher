package genetipher

import geneticsearch.Types.{MutationFunc, Population}
import geneticsearch.algorithm.GeneticAlgorithm
import geneticsearch.genotype.{Genotype, Sequence}

import scala.util.Random


class Decoder(cipherText: String, encoding: Option[String]) {

    // Parameters for the genetic algorithm
    private val lamdba = 5
    private val mu = 15
    private val charMutationProb = 0.1f

    def decode(): Option[String] = {
        if (encoding.isEmpty) {
            // TODO Find encoding
            println("Finding an encoding automatically is not currently supported, exiting...")
            None()

        } else {
            val algorithm: Option[GeneticAlgorithm[Char]] = encoding match {
                case "substitution" => Some(AlgorithmFactory.lamdbaMuSubsSolver(cipherText, lamdba, mu))
                case default =>
                    println(s"Cipher type $default is not supported, exiting...")
                    None()
            }

            if (algorithm.isEmpty) {
                None()
            } else {
                val bestEncodings = algorithm.get.run(randomPop(lamdba + mu))
                val possiblePlaintexts = bestEncodings.map(enc => Util.buildPlaintext(cipherText, enc.elems))

                Some(possiblePlaintexts(0))
            }
        }
    }

    private def randomPop(popSize: Int): Population[Char] = {
        val mutFunc = randCharMutation(charMutationProb)
        val pop = for (_ <- 0 until popSize) yield {
            val elems = Util.randCharSeq(Util.NUM_LETTERS)
            new Sequence[Char](elems, mutFunc, null)
        }
        pop.toVector
    }

    private def randCharMutation(mutProb: Float): MutationFunc[Sequence[Char]] = {
        genotype => {
            val elems = genotype.map { char =>
                val rand = Random.nextFloat()
                if (rand <= mutProb) {
                    Util.randChar()
                } else {
                    char
                }
            }
            genotype.withElems(elems)
        }
    }

}
