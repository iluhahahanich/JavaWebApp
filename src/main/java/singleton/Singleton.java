package singleton;

public class Singleton {

    public static String daoType = "csv";

    public static String appDir = "../webapps/UPweb/";

    private Singleton() {

    }

    public static Singleton getInstance(){
        return Holder.inst;
    }

    private static class Holder {
        private static final Singleton inst = new Singleton();
    }

    public static String getDaoType() {
        return daoType;
    }

    public static String getAppDir() {
        return appDir;
    }
}
