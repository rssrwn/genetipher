# Genetipher

A project for deciphering text using genetic search over possible encryptions. Currently only supports text encrypted using substitution ciphers.

The genetic search backend is implemented in another one of my [projects](https://github.com/rssrwn/genetic-search), the intention is that both projects will be developed and optimised in parallel.

### Overview

There are two key files in this project:
- Decoder: Implements the core decoding functionality by making use of the genetic algorithm defined in AlgorithmFactory.
- EnglishScore: Contains functions for giving a piece of text a score based on how similar to English it is likely to be. It does this by splitting text into tri-grams (sequences of 3 characters) and finding how many times those tri-grams appeared in a pre-processed corpus of text (the output of which can be found in the resources folder).

### Usage 

The project is made to be very simple to use. Clone the project and run `sbt "run <path to file with ciphertext> substitution"`. You can also build the jar using `sbt package` and then run. The "substitution" arg is simply to save the decoder from having to search for the type of cipher has been used. In the future I intend to allow this param to be optional, but this hasn't been implemented yet, so for now it's required.
