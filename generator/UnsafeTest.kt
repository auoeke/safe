import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable
import java.lang.annotation.RetentionPolicy

@Testable
class UnsafeTest {
    @Test
    fun test() {
        // println(Safe.allocateInstance<RetentionPolicy>())
    }
}
