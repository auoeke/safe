import factory.UnsafeFactoryBuilder
import factory.UnsafeFactoryType
import net.auoeke.safe.Safe
import net.auoeke.safe.Safe.new
import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable
import sun.misc.Unsafe
import java.lang.annotation.RetentionPolicy

@Testable
class UnsafeTest {
    @Test
    fun test() {
        assert(new<Int>() == 0)

        assert(UnsafeFactoryBuilder().type(UnsafeFactoryType.REFLECTIVE).build().instantiate().javaClass == Class.forName("jdk.internal.misc.Unsafe"))
        assert(UnsafeFactoryBuilder().type(UnsafeFactoryType.REFLECTIVE).build().instantiateSun()::class == Unsafe::class)
        assert(UnsafeFactoryBuilder().type(UnsafeFactoryType.UNSAFE).build().instantiate().javaClass == Class.forName("jdk.internal.misc.Unsafe"))
        assert(UnsafeFactoryBuilder().type(UnsafeFactoryType.UNSAFE).build().instantiateSun()::class == Unsafe::class)
    }
}
