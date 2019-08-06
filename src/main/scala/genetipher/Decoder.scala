package genetipher

import geneticsearch.Types.{MutationFunc, Population}
import geneticsearch.algorithm.GeneticAlgorithm
import geneticsearch.genotype.Sequence

import scala.util.Random


class Decoder(ciphertext: String, encoding: Option[String]) {

    // Parameters for the genetic algorithm
    private val lamdba = 5
    private val mu = 95
    private val numCharSwaps = 3

    def decode(): Option[Seq[String]] = {
        if (encoding.isEmpty) {
            // TODO Find encoding
            println("Finding an encoding automatically is not currently supported, exiting...")
            None

        } else {
            val algorithm: Option[GeneticAlgorithm[Char]] = encoding.get match {
                case "substitution" => Some(AlgorithmFactory.lamdbaMuSubsSolver(ciphertext, lamdba, mu))
                case default =>
                    println(s"Cipher type $default is not supported, exiting...")
                    None
            }

            if (algorithm.isEmpty) {
                None
            } else {
                val bestEncodings = algorithm.get.run(randomPop(lamdba + mu))
                val possiblePlaintexts = bestEncodings.map(enc => Util.buildPlaintext(ciphertext, enc.elems))

                println("score " + EnglishScore.score(possiblePlaintexts.head))

                Some(possiblePlaintexts)
            }
        }
    }

    protected def randomPop(popSize: Int): Population[Char] = {
        val mutFunc = randCharSwapMutation(numCharSwaps)
        val pop = for (_ <- 0 until popSize) yield {
            val elems = Util.randDistinctCharSeq()
            new Sequence[Char](elems, mutFunc, null)
        }
        pop.toVector
    }

    protected def randCharSwapMutation(numSwaps: Int): MutationFunc[Sequence[Char]] = {
        genotype => {
            val elems = (0 until numSwaps).foldLeft(genotype.elems) { (curElems, _) =>
                val length = curElems.length

                val idx1 = Random.nextInt(length)
                val idx2 = Random.nextInt(length)

                val elem1 = curElems(idx1)
                val elem2 = curElems(idx2)

                curElems.updated(idx1, elem2).updated(idx2, elem1)
            }

            genotype.withElems(elems)
        }
    }

}
