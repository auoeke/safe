import net.auoeke.extensions.contains
import net.auoeke.extensions.letIf
import org.junit.jupiter.api.Test
import org.junit.platform.commons.annotation.Testable
import sun.misc.Unsafe
import java.lang.IllegalArgumentException
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.nio.charset.StandardCharsets
import java.nio.file.StandardOpenOption
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.writeText

@Testable
class Generator {
    @Test
    fun generate() {
        val output = Path("src/net/auoeke/safe").createDirectories().resolve("Safe.kt")
        val string = StringBuilder("package net.auoeke.safe\n\n")
            .append("import java.lang.invoke.MethodHandle\n")
            .append("import java.lang.invoke.MethodHandles.Lookup\n")
            .append("import java.lang.invoke.MethodType\n")
            .append("import java.lang.reflect.Field\n")
            .append("import java.nio.ByteBuffer\n")
            .append("import sun.misc.Unsafe\n\n")
            .append("object Safe {\n")
        val fieldNames = HashSet<String>()
        val methods = ArrayList<String>()

        Class.forName("jdk.internal.misc.Unsafe").declaredMethods.filter {!(Modifier.isStatic(it.modifiers) || !Modifier.isPublic(it.modifiers) || it.name.contains(true, "release", "acquire", "compare", "opaque", "aligned", "getAnd", "throwException"))}.forEach {
            val fieldBaseName = it.name
            var fieldName = fieldBaseName
            var suffix = 1

            while (!fieldNames.add(fieldName)) {
                fieldName = fieldBaseName + suffix++
            }

            string.append("@JvmStatic\nprivate val $fieldName: MethodHandle = ${this.initializer(it)}\n\n")
            methods += this.declaration(it, fieldName)
        }

        methods.forEach(string::append)
        output.writeText(string.append("}").toString(), StandardCharsets.UTF_8, StandardOpenOption.CREATE_NEW)
    }

    private fun declaration(method: Method, fieldName: String): String {
        val invocation = "this.$fieldName.invoke(${this.parameters(method).keys.joinToString(", ")})"
        val end = when (method.returnType) {
            Void.TYPE -> " {$invocation}"
            else -> method.genericReturnType.kotlinType.let {returnType ->
                ": $returnType = $invocation".letIf(method.returnType.kotlin != Any::class) {"$it as $returnType"}
            }
        }

        return "@JvmStatic\nfun ${method.name}(${parameters(method).entries.joinToString(", ") {"${it.key}: ${it.value}"}})$end\n\n"
    }

    private fun parameters(method: Method): Map<String, String> {
        val parameters = LinkedHashMap<String, String>()

        method.parameters.forEach {
            val baseName = it.type.variableName
            var name = baseName
            var suffix = 1

            while (name in parameters) {
                name = baseName + suffix++
            }

            parameters[name] = it.parameterizedType.kotlinType
        }

        return parameters
    }

    private fun initializer(method: Method): String {
        return """lookup.bind(unsafe, "${method.name}", MethodType.methodType(${method.returnType.literal}, ${method.parameterTypes.joinToString(", ") {it.literal}}))"""
    }
}

private val Class<*>.literal get(): String = when (this) {
    Void.TYPE -> "Void.TYPE"
    else -> "${this.kotlinName}::class.java"
}

private val Class<*>.kotlinName get(): String = this.kotlin.simpleName!!

private val Type.kotlinType get(): String = when (this) {
    is ParameterizedType -> this.typeName.replace('?', '*') + "?"
    is Class<*> -> this.kotlinName.letIf(this.isArray && !this.componentType.isPrimitive) {"$it<*>"}.letIf(!this.isPrimitive) {"$it?"}
    else -> throw IllegalArgumentException()
}

private val Class<*>.variableName: String get() = when (val name = this.kotlinName.replaceFirstChar(Char::lowercase)) {
    "object" -> "obj"
    "class" -> "type"
    else -> name
}
