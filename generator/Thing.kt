import sun.misc.Unsafe
import java.lang.invoke.MethodHandles

object Thing {
    @JvmField
    val sunUnsafe: Unsafe = Unsafe::class.java.getDeclaredField("theUnsafe").apply {trySetAccessible()}[null] as Unsafe

    @JvmField
    val lookup: MethodHandles.Lookup = sunUnsafe.getObject(MethodHandles.Lookup::class.java, sunUnsafe.staticFieldOffset(MethodHandles.Lookup::class.java.getDeclaredField("IMPL_LOOKUP"))) as MethodHandles.Lookup

    @Suppress("ComplexRedundantLet") // redundan't
    @JvmField
    val unsafe: Any = Class.forName("jdk.internal.misc.Unsafe").let {unsafe -> lookup.findStaticGetter(unsafe, "theUnsafe", unsafe)()}

    // @JvmStatic
    // inline fun <reified T> allocateInstance(): T = this.allocateInstance(T::class.java) as T
}
