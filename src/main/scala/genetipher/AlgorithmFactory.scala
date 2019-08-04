package genetipher

import geneticsearch.algorithm.GeneticAlgorithmBuilder


object AlgorithmFactory {

    val subsSolver = new GeneticAlgorithmBuilder[Char]()
            .withSelectionOp()
            .withFitnessOp()
            .withMutationOp()
            .build()

}
