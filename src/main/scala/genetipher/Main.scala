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

        val decoded = decode(cipherText, encodingType)

        if (decoded.isDefined) {
            println("The decoded string is as follows: ")
            println(decoded)
        }
    }

    private def decode(cipherText: String, encoding: Option[String]): Option[String] = {
        if (encoding.isEmpty) {
            // Find encoding
            println("Finding an encoding automatically is not currently supported, exiting...")
            None()

        } else {
            val algorithm: Option[] = encoding match {
                case "substition" => Some(null)
                case default =>
                    println(s"Cipher type $default is not supported, exiting...")
                    None()
            }

            if (algorithm.isEmpty) {
                None()
            } else {
                Some("")
            }
        }
    }

    private def readFile(filename: String): String = {
        val bufferedSource = Source.fromFile(filename)
        val lines = bufferedSource.getLines()
        bufferedSource.close()

        lines.mkString(" ")
    }

}
