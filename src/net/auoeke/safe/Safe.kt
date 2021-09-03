package net.auoeke.safe

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles.Lookup
import java.lang.invoke.MethodType
import java.lang.reflect.Field
import java.nio.ByteBuffer
import sun.misc.Unsafe
import java.lang.invoke.MethodHandles
import java.security.ProtectionDomain

@Suppress("UNCHECKED_CAST")
object Safe {
    @JvmField
    val sunUnsafe: Unsafe = Unsafe::class.java.getDeclaredField("theUnsafe").apply {trySetAccessible()}[null] as Unsafe

    @JvmField
    val lookup: Lookup = sunUnsafe.getObject(Lookup::class.java, sunUnsafe.staticFieldOffset(Lookup::class.java.getDeclaredField("IMPL_LOOKUP"))) as Lookup

    @Suppress("ComplexRedundantLet") // redundan't
    @JvmField
    val unsafe: Any = Class.forName("jdk.internal.misc.Unsafe").let {unsafe -> lookup.findStaticGetter(unsafe, "theUnsafe", unsafe)()}

    @JvmStatic
    private val allocateInstance: MethodHandle = lookup.bind(unsafe, "allocateInstance", MethodType.methodType(Any::class.java, Class::class.java))

    @JvmStatic
    private val loadFence: MethodHandle = lookup.bind(unsafe, "loadFence", MethodType.methodType(Void.TYPE))

    @JvmStatic
    private val storeFence: MethodHandle = lookup.bind(unsafe, "storeFence", MethodType.methodType(Void.TYPE))

    @JvmStatic
    private val fullFence: MethodHandle = lookup.bind(unsafe, "fullFence", MethodType.methodType(Void.TYPE))

    @JvmStatic
    private val getReference: MethodHandle = lookup.bind(unsafe, "getReference", MethodType.methodType(Any::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putReference: MethodHandle = lookup.bind(unsafe, "putReference", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Any::class.java))

    @JvmStatic
    private val getBoolean: MethodHandle = lookup.bind(unsafe, "getBoolean", MethodType.methodType(Boolean::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putBoolean: MethodHandle = lookup.bind(unsafe, "putBoolean", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Boolean::class.java))

    @JvmStatic
    private val getByte: MethodHandle = lookup.bind(unsafe, "getByte", MethodType.methodType(Byte::class.java, Long::class.java))

    @JvmStatic
    private val getByte1: MethodHandle = lookup.bind(unsafe, "getByte", MethodType.methodType(Byte::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putByte: MethodHandle = lookup.bind(unsafe, "putByte", MethodType.methodType(Void.TYPE, Long::class.java, Byte::class.java))

    @JvmStatic
    private val putByte1: MethodHandle = lookup.bind(unsafe, "putByte", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Byte::class.java))

    @JvmStatic
    private val getShort: MethodHandle = lookup.bind(unsafe, "getShort", MethodType.methodType(Short::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val getShort1: MethodHandle = lookup.bind(unsafe, "getShort", MethodType.methodType(Short::class.java, Long::class.java))

    @JvmStatic
    private val putShort: MethodHandle = lookup.bind(unsafe, "putShort", MethodType.methodType(Void.TYPE, Long::class.java, Short::class.java))

    @JvmStatic
    private val putShort1: MethodHandle = lookup.bind(unsafe, "putShort", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Short::class.java))

    @JvmStatic
    private val getChar: MethodHandle = lookup.bind(unsafe, "getChar", MethodType.methodType(Char::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val getChar1: MethodHandle = lookup.bind(unsafe, "getChar", MethodType.methodType(Char::class.java, Long::class.java))

    @JvmStatic
    private val putChar: MethodHandle = lookup.bind(unsafe, "putChar", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Char::class.java))

    @JvmStatic
    private val putChar1: MethodHandle = lookup.bind(unsafe, "putChar", MethodType.methodType(Void.TYPE, Long::class.java, Char::class.java))

    @JvmStatic
    private val getInt: MethodHandle = lookup.bind(unsafe, "getInt", MethodType.methodType(Int::class.java, Long::class.java))

    @JvmStatic
    private val getInt1: MethodHandle = lookup.bind(unsafe, "getInt", MethodType.methodType(Int::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putInt: MethodHandle = lookup.bind(unsafe, "putInt", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Int::class.java))

    @JvmStatic
    private val putInt1: MethodHandle = lookup.bind(unsafe, "putInt", MethodType.methodType(Void.TYPE, Long::class.java, Int::class.java))

    @JvmStatic
    private val getLong: MethodHandle = lookup.bind(unsafe, "getLong", MethodType.methodType(Long::class.java, Long::class.java))

    @JvmStatic
    private val getLong1: MethodHandle = lookup.bind(unsafe, "getLong", MethodType.methodType(Long::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putLong: MethodHandle = lookup.bind(unsafe, "putLong", MethodType.methodType(Void.TYPE, Long::class.java, Long::class.java))

    @JvmStatic
    private val putLong1: MethodHandle = lookup.bind(unsafe, "putLong", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Long::class.java))

    @JvmStatic
    private val getFloat: MethodHandle = lookup.bind(unsafe, "getFloat", MethodType.methodType(Float::class.java, Long::class.java))

    @JvmStatic
    private val getFloat1: MethodHandle = lookup.bind(unsafe, "getFloat", MethodType.methodType(Float::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putFloat: MethodHandle = lookup.bind(unsafe, "putFloat", MethodType.methodType(Void.TYPE, Long::class.java, Float::class.java))

    @JvmStatic
    private val putFloat1: MethodHandle = lookup.bind(unsafe, "putFloat", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Float::class.java))

    @JvmStatic
    private val getDouble: MethodHandle = lookup.bind(unsafe, "getDouble", MethodType.methodType(Double::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val getDouble1: MethodHandle = lookup.bind(unsafe, "getDouble", MethodType.methodType(Double::class.java, Long::class.java))

    @JvmStatic
    private val putDouble: MethodHandle = lookup.bind(unsafe, "putDouble", MethodType.methodType(Void.TYPE, Long::class.java, Double::class.java))

    @JvmStatic
    private val putDouble1: MethodHandle = lookup.bind(unsafe, "putDouble", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Double::class.java))

    @JvmStatic
    private val getReferenceVolatile: MethodHandle = lookup.bind(unsafe, "getReferenceVolatile", MethodType.methodType(Any::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putReferenceVolatile: MethodHandle = lookup.bind(unsafe, "putReferenceVolatile", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Any::class.java))

    @JvmStatic
    private val getBooleanVolatile: MethodHandle = lookup.bind(unsafe, "getBooleanVolatile", MethodType.methodType(Boolean::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putBooleanVolatile: MethodHandle = lookup.bind(unsafe, "putBooleanVolatile", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Boolean::class.java))

    @JvmStatic
    private val getByteVolatile: MethodHandle = lookup.bind(unsafe, "getByteVolatile", MethodType.methodType(Byte::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putByteVolatile: MethodHandle = lookup.bind(unsafe, "putByteVolatile", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Byte::class.java))

    @JvmStatic
    private val getShortVolatile: MethodHandle = lookup.bind(unsafe, "getShortVolatile", MethodType.methodType(Short::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putShortVolatile: MethodHandle = lookup.bind(unsafe, "putShortVolatile", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Short::class.java))

    @JvmStatic
    private val getCharVolatile: MethodHandle = lookup.bind(unsafe, "getCharVolatile", MethodType.methodType(Char::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putCharVolatile: MethodHandle = lookup.bind(unsafe, "putCharVolatile", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Char::class.java))

    @JvmStatic
    private val getIntVolatile: MethodHandle = lookup.bind(unsafe, "getIntVolatile", MethodType.methodType(Int::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putIntVolatile: MethodHandle = lookup.bind(unsafe, "putIntVolatile", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Int::class.java))

    @JvmStatic
    private val getLongVolatile: MethodHandle = lookup.bind(unsafe, "getLongVolatile", MethodType.methodType(Long::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putLongVolatile: MethodHandle = lookup.bind(unsafe, "putLongVolatile", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Long::class.java))

    @JvmStatic
    private val getFloatVolatile: MethodHandle = lookup.bind(unsafe, "getFloatVolatile", MethodType.methodType(Float::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putFloatVolatile: MethodHandle = lookup.bind(unsafe, "putFloatVolatile", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Float::class.java))

    @JvmStatic
    private val getDoubleVolatile: MethodHandle = lookup.bind(unsafe, "getDoubleVolatile", MethodType.methodType(Double::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putDoubleVolatile: MethodHandle = lookup.bind(unsafe, "putDoubleVolatile", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Double::class.java))

    @JvmStatic
    private val park: MethodHandle = lookup.bind(unsafe, "park", MethodType.methodType(Void.TYPE, Boolean::class.java, Long::class.java))

    @JvmStatic
    private val unpark: MethodHandle = lookup.bind(unsafe, "unpark", MethodType.methodType(Void.TYPE, Any::class.java))

    @JvmStatic
    private val defineClass: MethodHandle = lookup.bind(
        unsafe,
        "defineClass",
        MethodType.methodType(Class::class.java, String::class.java, ByteArray::class.java, Int::class.java, Int::class.java, ClassLoader::class.java, ProtectionDomain::class.java)
    )

    @JvmStatic
    private val objectFieldOffset: MethodHandle = lookup.bind(unsafe, "objectFieldOffset", MethodType.methodType(Long::class.java, Field::class.java))

    @JvmStatic
    private val objectFieldOffset1: MethodHandle = lookup.bind(unsafe, "objectFieldOffset", MethodType.methodType(Long::class.java, Class::class.java, String::class.java))

    @JvmStatic
    private val defineClass0: MethodHandle = lookup.bind(
        unsafe,
        "defineClass0",
        MethodType.methodType(Class::class.java, String::class.java, ByteArray::class.java, Int::class.java, Int::class.java, ClassLoader::class.java, ProtectionDomain::class.java)
    )

    @JvmStatic
    private val ensureClassInitialized: MethodHandle = lookup.bind(unsafe, "ensureClassInitialized", MethodType.methodType(Void.TYPE, Class::class.java))

    @JvmStatic
    private val staticFieldBase: MethodHandle = lookup.bind(unsafe, "staticFieldBase", MethodType.methodType(Any::class.java, Field::class.java))

    @JvmStatic
    private val staticFieldOffset: MethodHandle = lookup.bind(unsafe, "staticFieldOffset", MethodType.methodType(Long::class.java, Field::class.java))

    @JvmStatic
    private val shouldBeInitialized: MethodHandle = lookup.bind(unsafe, "shouldBeInitialized", MethodType.methodType(Boolean::class.java, Class::class.java))

    @JvmStatic
    private val loadLoadFence: MethodHandle = lookup.bind(unsafe, "loadLoadFence", MethodType.methodType(Void.TYPE))

    @JvmStatic
    private val storeStoreFence: MethodHandle = lookup.bind(unsafe, "storeStoreFence", MethodType.methodType(Void.TYPE))

    @JvmStatic
    private val getAddress: MethodHandle = lookup.bind(unsafe, "getAddress", MethodType.methodType(Long::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val getAddress1: MethodHandle = lookup.bind(unsafe, "getAddress", MethodType.methodType(Long::class.java, Long::class.java))

    @JvmStatic
    private val putAddress: MethodHandle = lookup.bind(unsafe, "putAddress", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Long::class.java))

    @JvmStatic
    private val putAddress1: MethodHandle = lookup.bind(unsafe, "putAddress", MethodType.methodType(Void.TYPE, Long::class.java, Long::class.java))

    @JvmStatic
    private val freeMemory: MethodHandle = lookup.bind(unsafe, "freeMemory", MethodType.methodType(Void.TYPE, Long::class.java))

    @JvmStatic
    private val setMemory: MethodHandle = lookup.bind(unsafe, "setMemory", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Long::class.java, Byte::class.java))

    @JvmStatic
    private val setMemory1: MethodHandle = lookup.bind(unsafe, "setMemory", MethodType.methodType(Void.TYPE, Long::class.java, Long::class.java, Byte::class.java))

    @JvmStatic
    private val copyMemory: MethodHandle = lookup.bind(unsafe, "copyMemory", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Any::class.java, Long::class.java, Long::class.java))

    @JvmStatic
    private val copyMemory1: MethodHandle = lookup.bind(unsafe, "copyMemory", MethodType.methodType(Void.TYPE, Long::class.java, Long::class.java, Long::class.java))

    @JvmStatic
    private val copySwapMemory: MethodHandle =
        lookup.bind(unsafe, "copySwapMemory", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Any::class.java, Long::class.java, Long::class.java, Long::class.java))

    @JvmStatic
    private val copySwapMemory1: MethodHandle = lookup.bind(unsafe, "copySwapMemory", MethodType.methodType(Void.TYPE, Long::class.java, Long::class.java, Long::class.java, Long::class.java))

    @JvmStatic
    private val dataCacheLineAlignDown: MethodHandle = lookup.bind(unsafe, "dataCacheLineAlignDown", MethodType.methodType(Long::class.java, Long::class.java))

    @JvmStatic
    private val dataCacheLineFlushSize: MethodHandle = lookup.bind(unsafe, "dataCacheLineFlushSize", MethodType.methodType(Int::class.java))

    @JvmStatic
    private val arrayBaseOffset: MethodHandle = lookup.bind(unsafe, "arrayBaseOffset", MethodType.methodType(Int::class.java, Class::class.java))

    @JvmStatic
    private val arrayIndexScale: MethodHandle = lookup.bind(unsafe, "arrayIndexScale", MethodType.methodType(Int::class.java, Class::class.java))

    @JvmStatic
    private val getUncompressedObject: MethodHandle = lookup.bind(unsafe, "getUncompressedObject", MethodType.methodType(Any::class.java, Long::class.java))

    @JvmStatic
    private val allocateMemory: MethodHandle = lookup.bind(unsafe, "allocateMemory", MethodType.methodType(Long::class.java, Long::class.java))

    @JvmStatic
    private val reallocateMemory: MethodHandle = lookup.bind(unsafe, "reallocateMemory", MethodType.methodType(Long::class.java, Long::class.java, Long::class.java))

    @JvmStatic
    private val writebackMemory: MethodHandle = lookup.bind(unsafe, "writebackMemory", MethodType.methodType(Void.TYPE, Long::class.java, Long::class.java))

    @JvmStatic
    private val addressSize: MethodHandle = lookup.bind(unsafe, "addressSize", MethodType.methodType(Int::class.java))

    @JvmStatic
    private val pageSize: MethodHandle = lookup.bind(unsafe, "pageSize", MethodType.methodType(Int::class.java))

    @JvmStatic
    private val defineAnonymousClass: MethodHandle = lookup.bind(unsafe, "defineAnonymousClass", MethodType.methodType(Class::class.java, Class::class.java, ByteArray::class.java, Array::class.java))

    @JvmStatic
    private val allocateUninitializedArray: MethodHandle = lookup.bind(unsafe, "allocateUninitializedArray", MethodType.methodType(Any::class.java, Class::class.java, Int::class.java))

    @JvmStatic
    private val getLoadAverage: MethodHandle = lookup.bind(unsafe, "getLoadAverage", MethodType.methodType(Int::class.java, DoubleArray::class.java, Int::class.java))

    @JvmStatic
    private val isBigEndian: MethodHandle = lookup.bind(unsafe, "isBigEndian", MethodType.methodType(Boolean::class.java))

    @JvmStatic
    private val invokeCleaner: MethodHandle = lookup.bind(unsafe, "invokeCleaner", MethodType.methodType(Void.TYPE, ByteBuffer::class.java))

    @JvmStatic
    private val getObject: MethodHandle = lookup.bind(unsafe, "getObject", MethodType.methodType(Any::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val getObjectVolatile: MethodHandle = lookup.bind(unsafe, "getObjectVolatile", MethodType.methodType(Any::class.java, Any::class.java, Long::class.java))

    @JvmStatic
    private val putObject: MethodHandle = lookup.bind(unsafe, "putObject", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Any::class.java))

    @JvmStatic
    private val putObjectVolatile: MethodHandle = lookup.bind(unsafe, "putObjectVolatile", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Any::class.java))

    @JvmStatic
    fun <T> allocateInstance(type: Class<T>): T = this.allocateInstance.invoke(type) as T

    @JvmStatic
    inline fun <reified T> allocateInstance(): T = this.allocateInstance(T::class.java)

    @JvmStatic
    fun loadFence() {
        this.loadFence.invoke()
    }

    @JvmStatic
    fun storeFence() {
        this.storeFence.invoke()
    }

    @JvmStatic
    fun fullFence() {
        this.fullFence.invoke()
    }

    @JvmStatic
    fun getReference(any: Any?, long: Long): Any? = this.getReference.invoke(any, long)

    @JvmStatic
    fun putReference(any: Any?, long: Long, any1: Any?) {
        this.putReference.invoke(any, long, any1)
    }

    @JvmStatic
    fun getBoolean(any: Any?, long: Long): Boolean = this.getBoolean.invoke(any, long) as Boolean

    @JvmStatic
    fun putBoolean(any: Any?, long: Long, boolean: Boolean) {
        this.putBoolean.invoke(any, long, boolean)
    }

    @JvmStatic
    fun getByte(long: Long): Byte = this.getByte.invoke(long) as Byte

    @JvmStatic
    fun getByte(any: Any?, long: Long): Byte = this.getByte1.invoke(any, long) as Byte

    @JvmStatic
    fun putByte(long: Long, byte: Byte) {
        this.putByte.invoke(long, byte)
    }

    @JvmStatic
    fun putByte(any: Any?, long: Long, byte: Byte) {
        this.putByte1.invoke(any, long, byte)
    }

    @JvmStatic
    fun getShort(any: Any?, long: Long): Short = this.getShort.invoke(any, long) as Short

    @JvmStatic
    fun getShort(long: Long): Short = this.getShort1.invoke(long) as Short

    @JvmStatic
    fun putShort(long: Long, short: Short) {
        this.putShort.invoke(long, short)
    }

    @JvmStatic
    fun putShort(any: Any?, long: Long, short: Short) {
        this.putShort1.invoke(any, long, short)
    }

    @JvmStatic
    fun getChar(any: Any?, long: Long): Char = this.getChar.invoke(any, long) as Char

    @JvmStatic
    fun getChar(long: Long): Char = this.getChar1.invoke(long) as Char

    @JvmStatic
    fun putChar(any: Any?, long: Long, char: Char) {
        this.putChar.invoke(any, long, char)
    }

    @JvmStatic
    fun putChar(long: Long, char: Char) {
        this.putChar1.invoke(long, char)
    }

    @JvmStatic
    fun getInt(long: Long): Int = this.getInt.invoke(long) as Int

    @JvmStatic
    fun getInt(any: Any?, long: Long): Int = this.getInt1.invoke(any, long) as Int

    @JvmStatic
    fun putInt(any: Any?, long: Long, int: Int) {
        this.putInt.invoke(any, long, int)
    }

    @JvmStatic
    fun putInt(long: Long, int: Int) {
        this.putInt1.invoke(long, int)
    }

    @JvmStatic
    fun getLong(long: Long): Long = this.getLong.invoke(long) as Long

    @JvmStatic
    fun getLong(any: Any?, long: Long): Long = this.getLong1.invoke(any, long) as Long

    @JvmStatic
    fun putLong(long: Long, long1: Long) {
        this.putLong.invoke(long, long1)
    }

    @JvmStatic
    fun putLong(any: Any?, long: Long, long1: Long) {
        this.putLong1.invoke(any, long, long1)
    }

    @JvmStatic
    fun getFloat(long: Long): Float = this.getFloat.invoke(long) as Float

    @JvmStatic
    fun getFloat(any: Any?, long: Long): Float = this.getFloat1.invoke(any, long) as Float

    @JvmStatic
    fun putFloat(long: Long, float: Float) {
        this.putFloat.invoke(long, float)
    }

    @JvmStatic
    fun putFloat(any: Any?, long: Long, float: Float) {
        this.putFloat1.invoke(any, long, float)
    }

    @JvmStatic
    fun getDouble(any: Any?, long: Long): Double = this.getDouble.invoke(any, long) as Double

    @JvmStatic
    fun getDouble(long: Long): Double = this.getDouble1.invoke(long) as Double

    @JvmStatic
    fun putDouble(long: Long, double: Double) {
        this.putDouble.invoke(long, double)
    }

    @JvmStatic
    fun putDouble(any: Any?, long: Long, double: Double) {
        this.putDouble1.invoke(any, long, double)
    }

    @JvmStatic
    fun getReferenceVolatile(any: Any?, long: Long): Any? = this.getReferenceVolatile.invoke(any, long)

    @JvmStatic
    fun putReferenceVolatile(any: Any?, long: Long, any1: Any?) {
        this.putReferenceVolatile.invoke(any, long, any1)
    }

    @JvmStatic
    fun getBooleanVolatile(any: Any?, long: Long): Boolean = this.getBooleanVolatile.invoke(any, long) as Boolean

    @JvmStatic
    fun putBooleanVolatile(any: Any?, long: Long, boolean: Boolean) {
        this.putBooleanVolatile.invoke(any, long, boolean)
    }

    @JvmStatic
    fun getByteVolatile(any: Any?, long: Long): Byte = this.getByteVolatile.invoke(any, long) as Byte

    @JvmStatic
    fun putByteVolatile(any: Any?, long: Long, byte: Byte) {
        this.putByteVolatile.invoke(any, long, byte)
    }

    @JvmStatic
    fun getShortVolatile(any: Any?, long: Long): Short = this.getShortVolatile.invoke(any, long) as Short

    @JvmStatic
    fun putShortVolatile(any: Any?, long: Long, short: Short) {
        this.putShortVolatile.invoke(any, long, short)
    }

    @JvmStatic
    fun getCharVolatile(any: Any?, long: Long): Char = this.getCharVolatile.invoke(any, long) as Char

    @JvmStatic
    fun putCharVolatile(any: Any?, long: Long, char: Char) {
        this.putCharVolatile.invoke(any, long, char)
    }

    @JvmStatic
    fun getIntVolatile(any: Any?, long: Long): Int = this.getIntVolatile.invoke(any, long) as Int

    @JvmStatic
    fun putIntVolatile(any: Any?, long: Long, int: Int) {
        this.putIntVolatile.invoke(any, long, int)
    }

    @JvmStatic
    fun getLongVolatile(any: Any?, long: Long): Long = this.getLongVolatile.invoke(any, long) as Long

    @JvmStatic
    fun putLongVolatile(any: Any?, long: Long, long1: Long) {
        this.putLongVolatile.invoke(any, long, long1)
    }

    @JvmStatic
    fun getFloatVolatile(any: Any?, long: Long): Float = this.getFloatVolatile.invoke(any, long) as Float

    @JvmStatic
    fun putFloatVolatile(any: Any?, long: Long, float: Float) {
        this.putFloatVolatile.invoke(any, long, float)
    }

    @JvmStatic
    fun getDoubleVolatile(any: Any?, long: Long): Double = this.getDoubleVolatile.invoke(any, long) as Double

    @JvmStatic
    fun putDoubleVolatile(any: Any?, long: Long, double: Double) {
        this.putDoubleVolatile.invoke(any, long, double)
    }

    @JvmStatic
    fun park(boolean: Boolean, long: Long) {
        this.park.invoke(boolean, long)
    }

    @JvmStatic
    fun unpark(any: Any?) {
        this.unpark.invoke(any)
    }

    @JvmStatic
    fun defineClass(string: String?, byteArray: ByteArray?, int: Int, int1: Int, classLoader: ClassLoader?, protectionDomain: ProtectionDomain?): Class<*> {
        return this.defineClass.invoke(string, byteArray, int, int1, classLoader, protectionDomain) as Class<*>
    }

    @JvmStatic
    fun objectFieldOffset(field: Field?): Long = this.objectFieldOffset.invoke(field) as Long

    @JvmStatic
    fun objectFieldOffset(type: Class<*>?, string: String?): Long = this.objectFieldOffset1.invoke(type, string) as Long

    @JvmStatic
    fun defineClass0(string: String?, byteArray: ByteArray?, int: Int, int1: Int, classLoader: ClassLoader?, protectionDomain: ProtectionDomain?): Class<*> {
        return this.defineClass0.invoke(string, byteArray, int, int1, classLoader, protectionDomain) as Class<*>
    }

    @JvmStatic
    fun ensureClassInitialized(type: Class<*>?) {
        this.ensureClassInitialized.invoke(type)
    }

    @JvmStatic
    fun staticFieldBase(field: Field?): Any? = this.staticFieldBase.invoke(field)

    @JvmStatic
    fun staticFieldOffset(field: Field?): Long = this.staticFieldOffset.invoke(field) as Long

    @JvmStatic
    fun shouldBeInitialized(type: Class<*>?): Boolean = this.shouldBeInitialized.invoke(type) as Boolean

    @JvmStatic
    fun loadLoadFence() {
        this.loadLoadFence.invoke()
    }

    @JvmStatic
    fun storeStoreFence() {
        this.storeStoreFence.invoke()
    }

    @JvmStatic
    fun getAddress(any: Any?, long: Long): Long = this.getAddress.invoke(any, long) as Long

    @JvmStatic
    fun getAddress(long: Long): Long = this.getAddress1.invoke(long) as Long

    @JvmStatic
    fun putAddress(any: Any?, long: Long, long1: Long) {
        this.putAddress.invoke(any, long, long1)
    }

    @JvmStatic
    fun putAddress(long: Long, long1: Long) {
        this.putAddress1.invoke(long, long1)
    }

    @JvmStatic
    fun freeMemory(long: Long) {
        this.freeMemory.invoke(long)
    }

    @JvmStatic
    fun setMemory(any: Any?, long: Long, long1: Long, byte: Byte) {
        this.setMemory.invoke(any, long, long1, byte)
    }

    @JvmStatic
    fun setMemory(long: Long, long1: Long, byte: Byte) {
        this.setMemory1.invoke(long, long1, byte)
    }

    @JvmStatic
    fun copyMemory(any: Any?, long: Long, any1: Any?, long1: Long, long2: Long) {
        this.copyMemory.invoke(any, long, any1, long1, long2)
    }

    @JvmStatic
    fun copyMemory(long: Long, long1: Long, long2: Long) {
        this.copyMemory1.invoke(long, long1, long2)
    }

    @JvmStatic
    fun copySwapMemory(any: Any?, long: Long, any1: Any?, long1: Long, long2: Long, long3: Long) {
        this.copySwapMemory.invoke(any, long, any1, long1, long2, long3)
    }

    @JvmStatic
    fun copySwapMemory(long: Long, long1: Long, long2: Long, long3: Long) {
        this.copySwapMemory1.invoke(long, long1, long2, long3)
    }

    @JvmStatic
    fun dataCacheLineAlignDown(long: Long): Long = this.dataCacheLineAlignDown.invoke(long) as Long

    @JvmStatic
    fun dataCacheLineFlushSize(): Int = this.dataCacheLineFlushSize.invoke() as Int

    @JvmStatic
    fun arrayBaseOffset(type: Class<*>?): Int = this.arrayBaseOffset.invoke(type) as Int

    @JvmStatic
    fun arrayIndexScale(type: Class<*>?): Int = this.arrayIndexScale.invoke(type) as Int

    @JvmStatic
    fun getUncompressedObject(long: Long): Any? = this.getUncompressedObject.invoke(long)

    @JvmStatic
    fun allocateMemory(long: Long): Long = this.allocateMemory.invoke(long) as Long

    @JvmStatic
    fun reallocateMemory(long: Long, long1: Long): Long = this.reallocateMemory.invoke(long, long1) as Long

    @JvmStatic
    fun writebackMemory(long: Long, long1: Long) {
        this.writebackMemory.invoke(long, long1)
    }

    @JvmStatic
    fun addressSize(): Int = this.addressSize.invoke() as Int

    @JvmStatic
    fun pageSize(): Int = this.pageSize.invoke() as Int

    @JvmStatic
    fun defineAnonymousClass(type: Class<*>?, byteArray: ByteArray?, array: Array<*>?): Class<*>? = this.defineAnonymousClass.invoke(type, byteArray, array) as Class<*>?

    @JvmStatic
    fun allocateUninitializedArray(type: Class<*>?, int: Int): Any? = this.allocateUninitializedArray.invoke(type, int)

    @JvmStatic
    fun getLoadAverage(doubleArray: DoubleArray?, int: Int): Int = this.getLoadAverage.invoke(doubleArray, int) as Int

    @JvmStatic
    fun isBigEndian(): Boolean = this.isBigEndian.invoke() as Boolean

    @JvmStatic
    fun invokeCleaner(byteBuffer: ByteBuffer) {
        this.invokeCleaner.invoke(byteBuffer)
    }

    @JvmStatic
    fun getObject(any: Any?, long: Long): Any? = this.getObject.invoke(any, long)

    @JvmStatic
    fun getObjectVolatile(any: Any?, long: Long): Any? = this.getObjectVolatile.invoke(any, long)

    @JvmStatic
    fun putObject(any: Any?, long: Long, any1: Any?) {
        this.putObject.invoke(any, long, any1)
    }

    @JvmStatic
    fun putObjectVolatile(any: Any?, long: Long, any1: Any?) {
        this.putObjectVolatile.invoke(any, long, any1)
    }
}
