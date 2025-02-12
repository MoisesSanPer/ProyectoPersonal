package  com.example.inventorypersonal.data.model

abstract class UniqueId(open val value: Any) {
    override fun equals(other: Any?): Boolean {
        if (this === other)
            return true

        other as UniqueId

        return value == other.value
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return "$value"
    }
}
