package conebot.discord.utilites;

public class Enviroment {

    private Enviroment(){
        throw new IllegalStateException();
    }

    public static final String TOKEN;

    static {
        TOKEN = getEnv("TOKEN");
    }

    private static String getEnv(String name) {
        String value = System.getenv(name);
        if(value == null){
            throw new IllegalStateException("The environment variable " + name + " is not set");
        }
        return value;
    }
}
