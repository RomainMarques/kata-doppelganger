package info.dmerej;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SafeCalculatorTest {
  @Test
  void should_not_throw_when_authorized() {
    // TODO: write a test to demonstrate the bug in SafeCalculator.add()
    Authorizer authorizer = () -> true;
    int left = 1;
    int right = 2;

    SafeCalculator calculator = new SafeCalculator(authorizer);
    int result = calculator.add(left, right);
    Assertions.assertEquals(left + right, result);
  }
}
