import net.auoeke.safe.Safe
import net.auoeke.safe.Safe.new
import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable
import java.lang.annotation.RetentionPolicy

@Testable
class UnsafeTest {
    @Test
    fun test() {
        assert(new<Int>() == 0)
    }
}
