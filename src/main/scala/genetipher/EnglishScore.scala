package genetipher

import scala.io.Source


object EnglishScore {

    private val TRIGRAM_FILE = "resources/ngrams/english_trigrams.txt"

    private val NGRAM_LENGTH = 3

    private val (highestCount: Double, meanProb: Double, scoreMap: Map[String, Double]) = {
        println(s"Loading scores from ngram file: $TRIGRAM_FILE")

        val bufferedSource = Source.fromFile(TRIGRAM_FILE)
        val pairs = bufferedSource.getLines().map { line =>
            val strs = line.split(" ")
            val ngram = strs(0)
            val count = strs(1).toLong
            (ngram, count)
        }.toSeq

        //val ngramCount = pairs.map(_._2).sum[Long].toDouble

        val counts = pairs.map(_._2)
        val bestCount = counts.max.toDouble
        val meanProb = (counts.sum.toDouble / counts.length) / bestCount

        val scores = pairs.map { case(ngram, count) =>
            (ngram, math.log(count / bestCount))
        }.toMap

        println("Successfully loaded counts into the score map")

        bufferedSource.close()

        (bestCount, meanProb, scores)
    }

    // TODO assume no spaces, get better training data, use bigrams?
    def score(text: String): Double = {
        text.filter(Util.isLetter).sliding(NGRAM_LENGTH).map { strSeq =>
            val str = strSeq.mkString.toUpperCase
            if (scoreMap.contains(str)) {
                scoreMap(str)
            } else {
                // Give ngrams with 0 occurrences a lower score than 1 occurrence
                math.log(0.01 / highestCount)
            }
        }.sum
    }

    def avgNgramScore(text: String): Double = {
        val ngrams = text.filter(Util.isLetter).sliding(NGRAM_LENGTH).toSeq
        val totalScore = ngrams.map { strSeq =>
            val str = strSeq.mkString.toUpperCase
            if (scoreMap.contains(str)) {
                val score = scoreMap(str) - math.log(meanProb)
                score
            } else {
                // Give ngrams with 0 occurrences a lower score than 1 occurrence
                val score = math.log((0.01 / highestCount) / meanProb)
                score
            }
        }.sum

        totalScore / ngrams.length
    }

}
