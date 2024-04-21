package message;

import message.serializer.ObjectSerializer;
import message.serializer.StringSerializer;
import yaff.Yaff;

public class YaffMessage {

    public static Yaff YAFF_MESSAGE = initYaff();

    private static Yaff initYaff() {
        Yaff yaff = new Yaff();
        yaff.registerSerializer(Message.class, new ObjectSerializer());
        yaff.registerSerializer(Message.class, new StringSerializer());
        return yaff;
    }
}
