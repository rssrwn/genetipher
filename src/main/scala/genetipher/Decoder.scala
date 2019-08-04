package genetipher

import geneticsearch.Types.Population
import geneticsearch.algorithm.GeneticAlgorithm
import geneticsearch.genotype.Genotype


class Decoder(cipherText: String, encoding: Option[String]) {

    def decode(): Option[String] = {
        if (encoding.isEmpty) {
            // TODO Find encoding
            println("Finding an encoding automatically is not currently supported, exiting...")
            None()

        } else {
            val algorithm: Option[GeneticAlgorithm[Char]] = encoding match {
                case "substition" => Some(AlgorithmFactory.subsSolver)
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
