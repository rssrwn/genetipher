package genetipher

import scala.io.Source


object Main {

    def main(args: Array[String]): Unit = {
        val REQUIRED_ARGS = 1

        if (args.length < REQUIRED_ARGS) {
            throw new IllegalArgumentException("At least one argument (a filename) is required")
        }

        val filename = args(0)
        println(s"Filename of $filename passed into decoder")

        val ciphertext = readFile(filename)

        val encodingType = if (args.length > REQUIRED_ARGS) {
            val cipherType = args(1)
            println(s"Cipher type $cipherType passed into decoder")
            Some(cipherType)
        } else {
            println("No cipher type given to decoder, will attempt to find the type of cipher")
            None
        }

        val decodedOpt = new Decoder(ciphertext, encodingType).decode()

        if (decodedOpt.isDefined) {
            val decoded = decodedOpt.get
            if (decoded.length == 1) {
                println("The decoded string is as follows: ")
                println(decoded.head)
            } else {
                println("The following are all the possible plaintext solutions found by the search...\n")
                for (plaintext <- decoded) {
                    println(plaintext)
                    println("\n*****************************\n")
                }
            }
        }
    }

    private def readFile(filename: String): String = {
        val bufferedSource = Source.fromFile(filename)
        val lines = bufferedSource.getLines()
        val str = lines.mkString(" ")
        bufferedSource.close()

        str
    }

}
