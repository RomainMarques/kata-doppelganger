package info.dmerej;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import static org.mockito.Mockito.*;

public class DiscountApplierTest {

  private static final PrintStream standardOut = System.out;
  private static final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
  private static List<User> users;

  @BeforeAll
  public static void startDiscountApplier(){
    User user1 = new User("didier", "didier@gmail.com");
    User user2 = new User("roger", "roger@gmail.com");

    users = List.of(user1, user2);
    System.setOut(new PrintStream(outputStreamCaptor));
  }

  @Test
  void should_notify_twice_when_applying_discount_for_two_users_v1() {
    // TODO: write a test to demonstrate the bug in DiscountApplier.applyV1()
    Notifier notifier = (user, message) -> System.out.println(user.name() + ", " + message);

    DiscountApplier discountApplier = new DiscountApplier(notifier);
    discountApplier.applyV1(10, users);
    Assertions.assertEquals("didier, You've got a new discount of 10%\n" +
            "roger, You've got a new discount of 10%", outputStreamCaptor.toString().trim());
  }

  @Test
  void should_notify_twice_when_applying_discount_for_two_users_v2() {
    // TODO: write a test to demonstrate the bug in DiscountApplier.applyV2()
    Notifier notifier = (user, message) -> System.out.println(user.name() + ", " + message);

    DiscountApplier discountApplier = new DiscountApplier(notifier);
    discountApplier.applyV2(10, users);
    Assertions.assertEquals("didier, You've got a new discount of 10%\n" +
            "roger, You've got a new discount of 10%", outputStreamCaptor.toString().trim());
  }

  @Test
  void should_notify_twice_when_applying_discount_for_two_users_v1_mockito() {
    // TODO: rewrite the test using Mockito
    Notifier notifier = Mockito.mock(Notifier.class);
    DiscountApplier discountApplier = new DiscountApplier(notifier);
    discountApplier.applyV1(10, users);
    Mockito.verify(notifier, times(2)).notify(any(), any());

  }

  @Test
  void should_notify_twice_when_applying_discount_for_two_users_v2_mockito() {
    // TODO: rewrite the test using Mockito
    Notifier notifierMock = mock(Notifier.class);
    DiscountApplier discountApplier = new DiscountApplier(notifierMock);

    discountApplier.applyV2(10, users);

    ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

    verify(notifierMock, times(2)).notify(userCaptor.capture(), any());

    Assertions.assertEquals(users.get(0), userCaptor.getAllValues().get(0));
    Assertions.assertEquals(users.get(1), userCaptor.getAllValues().get(1));
  }
}
