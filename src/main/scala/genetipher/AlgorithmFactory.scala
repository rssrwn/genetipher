package genetipher

import geneticsearch.algorithm.{GeneticAlgorithm, GeneticAlgorithmBuilder}
import geneticsearch.operators.Selection


object AlgorithmFactory {

    def lamdbaMuSubsSolver(lambda: Int, mu: Int): GeneticAlgorithm[Char] = new GeneticAlgorithmBuilder[Char]()
            .withFitnessOp()
            .withSelectionOp(Selection.selectBest(lambda))
            .withMutationOp()
            .build()

}
