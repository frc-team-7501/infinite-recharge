package org.frc7501.utils;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;

public class ClosedLoopBoundaryController {
  protected double lowerBound;
  protected double upperBound;
  protected double correction;
  protected final DoubleSupplier inputSupplier;
  protected final DoubleConsumer outputConsumer;

  public ClosedLoopBoundaryController(double lowerBound, double upperBound, double correction, DoubleSupplier inputSupplier, DoubleConsumer outputConsumer) {
    this.lowerBound     = lowerBound;
    this.upperBound     = upperBound;
    this.correction     = correction;
    this.inputSupplier  = inputSupplier;
    this.outputConsumer = outputConsumer;
  }

  public boolean inRange() {
    var position = inputSupplier.getAsDouble();
    return position >= lowerBound && position <= upperBound;
  }

  public void execute() {
    var position = inputSupplier.getAsDouble();
    double output;
    if (position > upperBound)
      output = -correction;
    else if (position < lowerBound)
      output = correction;
    else
      output = 0;
    outputConsumer.accept(output);
  }
}
