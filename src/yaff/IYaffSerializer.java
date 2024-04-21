package yaff;

public interface IYaffSerializer<T> {
    byte[] serialize(T obj);

    T deserialize(byte[] data) throws YaffException;

    int getUniqueIdentifier();
}
