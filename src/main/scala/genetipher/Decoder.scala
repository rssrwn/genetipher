package genetipher

import geneticsearch.Types.Population
import geneticsearch.algorithm.GeneticAlgorithm
import geneticsearch.genotype.Genotype


class Decoder(cipherText: String, encoding: Option[String]) {

    // Paramters for the genetic algorithm
    private val lamdba = 5
    private val mu = 15

    def decode(): Option[String] = {
        if (encoding.isEmpty) {
            // TODO Find encoding
            println("Finding an encoding automatically is not currently supported, exiting...")
            None()

        } else {
            val algorithm: Option[GeneticAlgorithm[Char]] = encoding match {
                case "substition" => Some(AlgorithmFactory.lamdbaMuSubsSolver(cipherText, lamdba, mu))
                case default =>
                    println(s"Cipher type $default is not supported, exiting...")
                    None()
            }

            if (algorithm.isEmpty) {
                None()
            } else {
                val bestEncodings = algorithm.get.run(randomPop(100))
                val possiblePlaintexts = bestEncodings.map(enc => buildPlaintext(cipherText, enc))

                Some(possiblePlaintexts(0))
            }
        }
    }

    private def buildPlaintext(cipherText: String, encoding: Genotype[Char]): String = {
        // TODO
        ""
    }

    private def randomPop(popSize: Int): Population[Char] = {
        // TODO
        null
    }

}
