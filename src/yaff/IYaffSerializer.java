package yaff;

public interface IYaffSerializer<T> {
    byte[] serialize(T obj) throws YaffException;

    T deserialize(byte[] data) throws YaffException;

    int getUniqueIdentifier();
}
