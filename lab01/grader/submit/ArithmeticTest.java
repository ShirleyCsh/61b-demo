import jh61b.grader.GradedTest;
import jh61b.grader.SecureAutograderRunner;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import static com.google.common.truth.Truth.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ArithmeticTest {

    /** Performs a few arbitrary tests to see if the product method is
     * correct */
    @Test
    @Order(0)
    @DisplayName("Test product correctness")
    @GradedTest(number = "1.1")
    public void testProduct() {
        assertThat(Arithmetic.product(5, 6)).isEqualTo(30);
        assertThat(Arithmetic.product(5, -6)).isEqualTo(-30);
        assertThat(Arithmetic.product(0, -6)).isEqualTo(0);
    }

    /** Performs a few arbitrary tests to see if the sum method is correct */
    @Test
    @Order(1)
    @DisplayName("Test sum correctness")
    @GradedTest(number = "1.2")
    public void testSum() {
        assertThat(Arithmetic.sum(5, 6)).isEqualTo(11);
        assertThat(Arithmetic.sum(5, -6)).isEqualTo(-1);
        assertThat(Arithmetic.sum(0, -6)).isEqualTo(-6);
        assertThat(Arithmetic.sum(6, -6)).isEqualTo(0);
    }

    public static void main(String[] args) {
        SecureAutograderRunner.run(args);
    }
}
