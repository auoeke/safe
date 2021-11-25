package net.auoeke.safe

import java.lang.invoke.MethodHandle
import java.lang.invoke.MethodHandles.Lookup
import java.lang.invoke.MethodType
import java.lang.reflect.Field
import java.nio.ByteBuffer
import sun.misc.Unsafe
import java.security.ProtectionDomain
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST", "unused")
object Safe {
    @JvmField
    val sunUnsafe: Unsafe = Unsafe::class.java.getDeclaredField("theUnsafe").apply {trySetAccessible()}[null] as Unsafe

    @JvmField
    val lookup: Lookup = sunUnsafe.getObject(Lookup::class.java, sunUnsafe.staticFieldOffset(Lookup::class.java.getDeclaredField("IMPL_LOOKUP"))) as Lookup

    @Suppress("ComplexRedundantLet") // redundan't
    @JvmField
    val unsafe: Any = Class.forName("jdk.internal.misc.Unsafe").let {lookup.findStatic(it, "getUnsafe", MethodType.methodType(it))()}

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
    private val defineHiddenClass: MethodHandle = lookup.bind(unsafe, "defineAnonymousClass", MethodType.methodType(Class::class.java, Class::class.java, ByteArray::class.java, Array<Any?>::class.java))

    @JvmStatic
    private val objectFieldOffset: MethodHandle = lookup.bind(unsafe, "objectFieldOffset", MethodType.methodType(Long::class.java, Field::class.java))

    @JvmStatic
    private val objectFieldOffset1: MethodHandle = lookup.bind(unsafe, "objectFieldOffset", MethodType.methodType(Long::class.java, Class::class.java, String::class.java))

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
    private val copySwapMemory: MethodHandle = lookup.bind(unsafe, "copySwapMemory", MethodType.methodType(Void.TYPE, Any::class.java, Long::class.java, Any::class.java, Long::class.java, Long::class.java, Long::class.java))

    @JvmStatic
    private val copySwapMemory1: MethodHandle = lookup.bind(unsafe, "copySwapMemory", MethodType.methodType(Void.TYPE, Long::class.java, Long::class.java, Long::class.java, Long::class.java))

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
    private val allocateUninitializedArray: MethodHandle = lookup.bind(unsafe, "allocateUninitializedArray", MethodType.methodType(Any::class.java, Class::class.java, Int::class.java))

    @JvmStatic
    private val getLoadAverage: MethodHandle = lookup.bind(unsafe, "getLoadAverage", MethodType.methodType(Int::class.java, DoubleArray::class.java, Int::class.java))

    @JvmStatic
    private val isBigEndian: MethodHandle = lookup.bind(unsafe, "isBigEndian", MethodType.methodType(Boolean::class.java))

    @JvmStatic
    private val invokeCleaner: MethodHandle = lookup.bind(unsafe, "invokeCleaner", MethodType.methodType(Void.TYPE, ByteBuffer::class.java))

    @JvmStatic
    fun <T> allocateInstance(type: Class<T>): T = allocateInstance.invoke(type) as T

    @JvmStatic
    fun loadFence() {
        loadFence.invoke()
    }

    @JvmStatic
    fun storeFence() {
        storeFence.invoke()
    }

    @JvmStatic
    fun fullFence() {
        fullFence.invoke()
    }

    @JvmStatic
    fun getReference(any: Any?, offset: Long): Any? = getReference.invoke(any, offset)

    @JvmStatic
    fun putReference(any: Any?, offset: Long, any1: Any?) {
        putReference.invoke(any, offset, any1)
    }

    @JvmStatic
    fun getBoolean(any: Any?, offset: Long): Boolean = getBoolean.invoke(any, offset) as Boolean

    @JvmStatic
    fun putBoolean(any: Any?, offset: Long, boolean: Boolean) {
        putBoolean.invoke(any, offset, boolean)
    }

    @JvmStatic
    fun getByte(address: Long): Byte = getByte.invoke(address) as Byte

    @JvmStatic
    fun getByte(any: Any?, offset: Long): Byte = getByte1.invoke(any, offset) as Byte

    @JvmStatic
    fun putByte(address: Long, byte: Byte) {
        putByte.invoke(address, byte)
    }

    @JvmStatic
    fun putByte(any: Any?, offset: Long, byte: Byte) {
        putByte1.invoke(any, offset, byte)
    }

    @JvmStatic
    fun getShort(any: Any?, long: Long): Short = getShort.invoke(any, long) as Short

    @JvmStatic
    fun getShort(address: Long): Short = getShort1.invoke(address) as Short

    @JvmStatic
    fun putShort(address: Long, short: Short) {
        putShort.invoke(address, short)
    }

    @JvmStatic
    fun putShort(any: Any?, offset: Long, short: Short) {
        putShort1.invoke(any, offset, short)
    }

    @JvmStatic
    fun getChar(any: Any?, offset: Long): Char = getChar.invoke(any, offset) as Char

    @JvmStatic
    fun getChar(address: Long): Char = getChar1.invoke(address) as Char

    @JvmStatic
    fun putChar(any: Any?, offset: Long, char: Char) {
        putChar.invoke(any, offset, char)
    }

    @JvmStatic
    fun putChar(address: Long, char: Char) {
        putChar1.invoke(address, char)
    }

    @JvmStatic
    fun getInt(address: Long): Int = getInt.invoke(address) as Int

    @JvmStatic
    fun getInt(any: Any?, offset: Long): Int = getInt1.invoke(any, offset) as Int

    @JvmStatic
    fun putInt(any: Any?, offset: Long, int: Int) {
        putInt.invoke(any, offset, int)
    }

    @JvmStatic
    fun putInt(address: Long, int: Int) {
        putInt1.invoke(address, int)
    }

    @JvmStatic
    fun getLong(address: Long): Long = getLong.invoke(address) as Long

    @JvmStatic
    fun getLong(any: Any?, long: Long): Long = getLong1.invoke(any, long) as Long

    @JvmStatic
    fun putLong(address: Long, long: Long) {
        putLong.invoke(address, long)
    }

    @JvmStatic
    fun putLong(any: Any?, long: Long, long1: Long) {
        putLong1.invoke(any, long, long1)
    }

    @JvmStatic
    fun getFloat(address: Long): Float = getFloat.invoke(address) as Float

    @JvmStatic
    fun getFloat(any: Any?, offset: Long): Float = getFloat1.invoke(any, offset) as Float

    @JvmStatic
    fun putFloat(address: Long, float: Float) {
        putFloat.invoke(address, float)
    }

    @JvmStatic
    fun putFloat(any: Any?, offset: Long, float: Float) {
        putFloat1.invoke(any, offset, float)
    }

    @JvmStatic
    fun getDouble(any: Any?, long: Long): Double = getDouble.invoke(any, long) as Double

    @JvmStatic
    fun getDouble(address: Long): Double = getDouble1.invoke(address) as Double

    @JvmStatic
    fun putDouble(address: Long, double: Double) {
        putDouble.invoke(address, double)
    }

    @JvmStatic
    fun putDouble(any: Any?, long: Long, double: Double) {
        putDouble1.invoke(any, long, double)
    }

    @JvmStatic
    fun getReferenceVolatile(any: Any?, offset: Long): Any? = getReferenceVolatile.invoke(any, offset)

    @JvmStatic
    fun putReferenceVolatile(any: Any?, offset: Long, any1: Any?) {
        putReferenceVolatile.invoke(any, offset, any1)
    }

    @JvmStatic
    fun getBooleanVolatile(any: Any?, offset: Long): Boolean = getBooleanVolatile.invoke(any, offset) as Boolean

    @JvmStatic
    fun putBooleanVolatile(any: Any?, offset: Long, boolean: Boolean) {
        putBooleanVolatile.invoke(any, offset, boolean)
    }

    @JvmStatic
    fun getByteVolatile(any: Any?, offset: Long): Byte = getByteVolatile.invoke(any, offset) as Byte

    @JvmStatic
    fun putByteVolatile(any: Any?, offset: Long, byte: Byte) {
        putByteVolatile.invoke(any, offset, byte)
    }

    @JvmStatic
    fun getShortVolatile(any: Any?, offset: Long): Short = getShortVolatile.invoke(any, offset) as Short

    @JvmStatic
    fun putShortVolatile(any: Any?, offset: Long, short: Short) {
        putShortVolatile.invoke(any, offset, short)
    }

    @JvmStatic
    fun getCharVolatile(any: Any?, offset: Long): Char = getCharVolatile.invoke(any, offset) as Char

    @JvmStatic
    fun putCharVolatile(any: Any?, offset: Long, char: Char) {
        putCharVolatile.invoke(any, offset, char)
    }

    @JvmStatic
    fun getIntVolatile(any: Any?, offset: Long): Int = getIntVolatile.invoke(any, offset) as Int

    @JvmStatic
    fun putIntVolatile(any: Any?, offset: Long, int: Int) {
        putIntVolatile.invoke(any, offset, int)
    }

    @JvmStatic
    fun getLongVolatile(any: Any?, offset: Long): Long = getLongVolatile.invoke(any, offset) as Long

    @JvmStatic
    fun putLongVolatile(any: Any?, offset: Long, offset1: Long) {
        putLongVolatile.invoke(any, offset, offset1)
    }

    @JvmStatic
    fun getFloatVolatile(any: Any?, offset: Long): Float = getFloatVolatile.invoke(any, offset) as Float

    @JvmStatic
    fun putFloatVolatile(any: Any?, offset: Long, float: Float) {
        putFloatVolatile.invoke(any, offset, float)
    }

    @JvmStatic
    fun getDoubleVolatile(any: Any?, offset: Long): Double = getDoubleVolatile.invoke(any, offset) as Double

    @JvmStatic
    fun putDoubleVolatile(any: Any?, offset: Long, double: Double) {
        putDoubleVolatile.invoke(any, offset, double)
    }

    @JvmStatic
    fun park(absolute: Boolean, time: Long) {
        park.invoke(absolute, time)
    }

    @JvmStatic
    fun unpark(thread: Thread) {
        unpark.invoke(thread)
    }

    @JvmStatic
    fun defineClass(name: String?, bytecode: ByteArray, offset: Int, length: Int, loader: ClassLoader?, protectionDomain: ProtectionDomain?): Class<*> {
        return defineClass.invoke(name, bytecode, offset, length, loader, protectionDomain) as Class<*>
    }

    @JvmStatic
    fun defineHiddenClass(hostingClass: Class<*>, bytecodes: ByteArray, constPatches: Array<Any?>? = null): Class<*> = defineHiddenClass.invoke(hostingClass, bytecodes, constPatches) as Class<*>

    @JvmStatic
    fun objectFieldOffset(field: Field): Long = objectFieldOffset.invoke(field) as Long

    @JvmStatic
    fun objectFieldOffset(type: Class<*>, name: String): Long = objectFieldOffset1.invoke(type, name) as Long

    @JvmStatic
    fun ensureClassInitialized(type: Class<*>) {
        ensureClassInitialized.invoke(type)
    }

    @JvmStatic
    fun staticFieldBase(field: Field): Any? = staticFieldBase.invoke(field)

    @JvmStatic
    fun staticFieldOffset(field: Field): Long = staticFieldOffset.invoke(field) as Long

    @JvmStatic
    fun shouldBeInitialized(type: Class<*>): Boolean = shouldBeInitialized.invoke(type) as Boolean

    @JvmStatic
    fun loadLoadFence() {
        loadLoadFence.invoke()
    }

    @JvmStatic
    fun storeStoreFence() {
        storeStoreFence.invoke()
    }

    @JvmStatic
    fun getAddress(any: Any?, offset: Long): Long = getAddress.invoke(any, offset) as Long

    @JvmStatic
    fun getAddress(address: Long): Long = getAddress1.invoke(address) as Long

    @JvmStatic
    fun putAddress(any: Any?, offset: Long, x: Long) {
        putAddress.invoke(any, offset, x)
    }

    @JvmStatic
    fun putAddress(address: Long, x: Long) {
        putAddress1.invoke(address, x)
    }

    @JvmStatic
    fun freeMemory(address: Long) {
        freeMemory.invoke(address)
    }

    @JvmStatic
    fun setMemory(any: Any?, offset: Long, bytes: Long, value: Byte) {
        setMemory.invoke(any, offset, bytes, value)
    }

    @JvmStatic
    fun setMemory(address: Long, bytes: Long, value: Byte) {
        setMemory1.invoke(address, bytes, value)
    }

    @JvmStatic
    fun copyMemory(srcBase: Any?, srcOffset: Long, destBase: Any?, destOffset: Long, bytes: Long) {
        copyMemory.invoke(srcBase, srcOffset, destBase, destOffset, bytes)
    }

    @JvmStatic
    fun copyMemory(srcAddress: Long, destAddress: Long, bytes: Long) {
        copyMemory1.invoke(srcAddress, destAddress, bytes)
    }

    @JvmStatic
    fun copySwapMemory(srcBase: Any?, srcOffset: Long, destBase: Any?, destOffset: Long, bytes: Long, elemSize: Long) {
        copySwapMemory.invoke(srcBase, srcOffset, destBase, destOffset, bytes, elemSize)
    }

    @JvmStatic
    fun copySwapMemory(srcAddress: Long, destAddress: Long, bytes: Long, elemSize: Long) {
        copySwapMemory1.invoke(srcAddress, destAddress, bytes, elemSize)
    }

    @JvmStatic
    fun arrayBaseOffset(arrayClass: Class<*>): Int = arrayBaseOffset.invoke(arrayClass) as Int

    @JvmStatic
    fun arrayIndexScale(arrayClass: Class<*>): Int = arrayIndexScale.invoke(arrayClass) as Int

    @JvmStatic
    fun getUncompressedObject(address: Long): Any? = getUncompressedObject.invoke(address)

    @JvmStatic
    fun allocateMemory(bytes: Long): Long = allocateMemory.invoke(bytes) as Long

    @JvmStatic
    fun reallocateMemory(address: Long, bytes: Long): Long = reallocateMemory.invoke(address, bytes) as Long

    @JvmStatic
    fun writebackMemory(address: Long, length: Long) {
        writebackMemory.invoke(address, length)
    }

    @JvmStatic
    fun addressSize(): Int = addressSize.invoke() as Int

    @JvmStatic
    fun pageSize(): Int = pageSize.invoke() as Int

    @JvmStatic
    fun allocateUninitializedArray(componentType: Class<*>, length: Int): Any = allocateUninitializedArray.invoke(componentType, length)

    @JvmStatic
    fun getLoadAverage(averages: DoubleArray, samples: Int): Int = getLoadAverage.invoke(averages, samples) as Int

    @JvmStatic
    fun isBigEndian(): Boolean = isBigEndian.invoke() as Boolean

    @JvmStatic
    fun invokeCleaner(directBuffer: ByteBuffer) {
        invokeCleaner.invoke(directBuffer)
    }

    // extensions

    @JvmStatic
    inline fun <reified T> new(): T = allocateInstance(T::class.java)

    @JvmStatic
    inline val <T> Class<T>.new get() = allocateInstance(this)

    @JvmStatic
    inline val <T : Any> KClass<T>.new get() = allocateInstance(java)
}
