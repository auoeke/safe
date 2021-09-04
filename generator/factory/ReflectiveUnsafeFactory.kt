package factory

import net.auoeke.safe.Safe
import sun.misc.Unsafe
import java.lang.invoke.MethodType

class ReflectiveUnsafeFactory : AbstractUnsafeFactory {
    override fun instantiate(): Any = Safe.lookup.findConstructor(Class.forName("jdk.internal.misc.Unsafe"), MethodType.methodType(Void.TYPE))()
    override fun instantiateSun(): Unsafe = Unsafe::class.java.getDeclaredConstructor().apply {trySetAccessible()}.newInstance()
}
