import net.auoeke.safe.Safe
import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable

@Testable
class UnsafeTest {
    @Test
    fun test() {
        println(Safe.allocateInstance(UnsafeTest::class.java))
    }
}
