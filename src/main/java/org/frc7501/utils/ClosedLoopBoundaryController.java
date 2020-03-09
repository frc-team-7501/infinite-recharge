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

  protected void execute() {
    var position = inputSupplier.getAsDouble();
    double error;
    if (position > upperBound)
      error = position - upperBound;
    else if (position < lowerBound)
      error = position - lowerBound;
    else
      error = 0;
    // TODO
  }
}
