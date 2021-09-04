package factory

import sun.misc.Unsafe

interface AbstractUnsafeFactory {
    fun instantiate(): Any
    fun instantiateSun(): Unsafe
}
