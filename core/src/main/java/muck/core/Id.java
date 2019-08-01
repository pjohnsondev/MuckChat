package muck.core;

import java.io.Serializable;

/**
 * A typed Id.
 * <p>
 * This lets us keep our "Id strings" separate from our natural strings.
 * Which can be useful, if for instance we actually store Ids in the database using a
 * different type.
 * <p>
 * It also means we can use generics to prevent us assigning, say, the id of a Foo to
 * a variable asking for the id of a Bar.
 */
public class Id<T> implements Serializable {

    public final String id;

    /**
     * Required for Kryonet deserialisation
     */
    public Id() {
        this(java.util.UUID.randomUUID().toString());
    }

    public Id(String id) {
        this.id = id;
    }

    public static <TT> Id<TT> generate() {
        return new Id<>(java.util.UUID.randomUUID().toString());

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return (other instanceof Id && this.id == ((Id) other).id);
    }

    @Override
    public String toString() {
        return "Id(" + id + ")";
    }

}
