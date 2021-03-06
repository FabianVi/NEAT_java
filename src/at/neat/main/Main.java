package at.neat.main;

import at.neat.algo.ActivationFunctions;
import at.neat.algo.Neuron;
import at.neat.algo.NeuronType;

public class Main {

    public static void main(String[] args) {

        Neuron in = new Neuron(NeuronType.Input, ActivationFunctions.Identity);

        Neuron hidden1 = new Neuron(NeuronType.Hidden, ActivationFunctions.BinaryStep);
        in.addNeuron(hidden1,2);
        Neuron hidden2 = new Neuron(NeuronType.Hidden, ActivationFunctions.LeakyReLU);
        in.addNeuron(hidden2,2);
        Neuron hidden3 = new Neuron(NeuronType.Hidden, ActivationFunctions.GELU);
        in.addNeuron(hidden3,2);

        Neuron output = new Neuron(NeuronType.Output, ActivationFunctions.Sigmoid);
        hidden1.addNeuron(output,1);
        hidden2.addNeuron(output,1);
        hidden3.addNeuron(output,1);

        hidden1.addNeuron(hidden2,2);
        hidden2.addNeuron(hidden1,2);

        in.fire(1.0d,50);
        System.out.printf("Final decision %f \r\n",output.getValue());
    }
}
