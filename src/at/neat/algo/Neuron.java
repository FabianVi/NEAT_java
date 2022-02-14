package at.neat.algo;

import java.util.ArrayList;
import java.util.HashMap;

public class Neuron {

    protected NeuronType neuronType;
    protected ActivationFunctions activationFunction;

    protected ArrayList<Neuron> connectedNeuronsPrev;

    protected ArrayList<Neuron> connectedNeuronsAfter;
    protected HashMap<Integer,Double> weights;

    protected double value;

    protected int timesFired;

    public Neuron(NeuronType neuronType, ActivationFunctions activationFunction) {
        this.neuronType = neuronType;
        this.activationFunction = activationFunction;

        connectedNeuronsPrev = new ArrayList<>();
        connectedNeuronsAfter = new ArrayList<>();

        weights = new HashMap<>();

        timesFired = 0;
    }

    public void fire() {
        fire(0.0d, 2);
    }

    public void fire(double in) {
        fire(in, 2);
        try {
            Thread.sleep(100);

        } catch (InterruptedException e) {};
    }

    public void fire(double in, int maxFired) {

        if(timesFired>=maxFired)
            return;

        timesFired++;

        if(neuronType!=NeuronType.Input)
            for(Neuron n : connectedNeuronsPrev)
                in += n.getWeightedValue(this);

        value = execute(in);

        // DEBUGGING
        System.out.printf("Fire of an %s value: %f \r\n",neuronType == NeuronType.Input ? "Input" : neuronType == NeuronType.Hidden ? "Hidden" : "Output",value);

        if(neuronType!=NeuronType.Output) {
            for(Neuron n : connectedNeuronsAfter)
                n.fire(0.0d,maxFired);
        }
    }

    private double execute(double in) {
        switch (activationFunction) {
            case Identity:
                return in;

            case BinaryStep:
                return in>=0 ? 1 : 0;

            default:
                return 0.0d;
        }
    }

    public double getValue() {
        return value;
    }

    public double getWeightedValue(Neuron n) {
        return value*weights.get(n.hashCode());
    }

    protected void addNeuronPrev(Neuron n) {
        connectedNeuronsPrev.add(n);
    }

    public void addNeuron(Neuron n) {
        this.addNeuron(n, Math.random());
    }

    public void addNeuron(Neuron n, double w) {
        n.addNeuronPrev(this);
        connectedNeuronsAfter.add(n);
        weights.put(n.hashCode(),w);
    }

    public void reset() {
        timesFired = 0;
        value = 0;
    }
}
