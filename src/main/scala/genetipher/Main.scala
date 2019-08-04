package genetipher

import scala.io.Source


object Main {


    def main(args: Array[String]): Unit = {
        val REQUIRED_ARGS = 1

        if (args.length <= REQUIRED_ARGS) {
            throw new IllegalArgumentException("At least one argument (a filename) is required")
        }

        val filename = args(1)
        println(s"Filename of $filename passed into decoder")
        val cipherText = readFile(filename)

        val encodingType = if (args.length > REQUIRED_ARGS + 1) {
            val cipherType = args(2)
            println(s"Cipher type $cipherType passed into decoder")
            Some(cipherType)
        } else {
            println("No cipher type given to decoder, will attempt to find the type of cipher")
            None
        }

        val decoded = new Decoder(cipherText, encodingType).decode()

        if (decoded.isDefined) {
            println("The decoded string is as follows: ")
            println(decoded)
        }
    }

    private def readFile(filename: String): String = {
        val bufferedSource = Source.fromFile(filename)
        val lines = bufferedSource.getLines()
        bufferedSource.close()

        lines.mkString(" ")
    }

}
