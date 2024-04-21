package message;

import message.serializer.StringSerializer;
import yaff.Yaff;

public class YaffMessage {

    public static final Yaff YAFF_MESSAGE = initYaff();

    private static Yaff initYaff() {
        Yaff yaff = new Yaff();
        yaff.registerContentType(Message.class, new StringSerializer());
        return yaff;
    }
}
