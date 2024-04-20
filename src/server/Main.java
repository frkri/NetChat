package server;

public class Main {

    public static void main(String[] args) {
        new Controller(args.length > 0 && args[0].equals("start"));
    }
}
