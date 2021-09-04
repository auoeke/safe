package factory

class UnsafeFactoryBuilder {
    private lateinit var type: UnsafeFactoryType

    fun type(type: UnsafeFactoryType) = this.also {
        this.type = type
    }

    fun build(): AbstractUnsafeFactory = when (this.type) {
        UnsafeFactoryType.REFLECTIVE -> ReflectiveUnsafeFactory()
        UnsafeFactoryType.UNSAFE -> UnsafeUnsafeFactory()
    }
}
