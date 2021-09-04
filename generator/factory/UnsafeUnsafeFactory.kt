package factory

import net.auoeke.safe.Safe
import net.auoeke.safe.Safe.new
import sun.misc.Unsafe

class UnsafeUnsafeFactory : AbstractUnsafeFactory {
    override fun instantiate(): Any = Class.forName("jdk.internal.misc.Unsafe").new
    override fun instantiateSun(): Unsafe = Unsafe::class.new
}
