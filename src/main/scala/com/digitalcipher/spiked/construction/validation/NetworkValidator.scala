package com.digitalcipher.spiked.construction.validation

import com.digitalcipher.spiked.construction.description.NetworkDescription

object NetworkValidator {
  def validateConnection(description: NetworkDescription): ValidationResult = {
    val neuronIds = description.neurons.keySet
    val learningFunctions = description.learningFunctions.keySet

    val missing: Seq[String] = description.connections
      .filter(connection => !neuronIds.contains(connection.preSynapticNeuronId))
      .map(connection => s"Pre-synaptic neuron '${connection.preSynapticNeuronId}' not found in neurons; (" +
        s"prn=${connection.preSynapticNeuronId}, " +
        s"psn=${connection.postSynapticNeuronId}, " +
        s"cnw=${connection.initialWeight}, " +
        s"eqw=${connection.equilibriumWeight}, " +
        s"lrn=${connection.learningFunctionName}" +
        s")"
      ) ++
      description.connections
        .filter(connection => !neuronIds.contains(connection.postSynapticNeuronId))
        .map(connection => s"Post-synaptic neuron  '${connection.postSynapticNeuronId}' not found in neurons; (" +
          s"prn=${connection.preSynapticNeuronId}, " +
          s"psn=${connection.postSynapticNeuronId}, " +
          s"cnw=${connection.initialWeight}, " +
          s"eqw=${connection.equilibriumWeight}, " +
          s"lrn=${connection.learningFunctionName}" +
          s")"
        ) ++
      description.connections
        .filter(connection => !learningFunctions.contains(connection.learningFunctionName))
        .map(connection => s"Learning function '${connection.learningFunctionName}' not found in learning functions; (" +
          s"prn=${connection.preSynapticNeuronId}, " +
          s"psn=${connection.postSynapticNeuronId}, " +
          s"cnw=${connection.initialWeight}, " +
          s"eqw=${connection.equilibriumWeight}, " +
          s"lrn=${connection.learningFunctionName}" +
          s")")

    ValidationResult(missing)
  }

  case class ValidationResult(errors: Seq[String] = Seq.empty) {
    def success: Boolean = errors.isEmpty
    def failed: Boolean = errors.nonEmpty
  }
}
