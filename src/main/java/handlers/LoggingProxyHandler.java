package handlers;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Date;

public class LoggingProxyHandler<T> implements InvocationHandler {
    private T target;
    private File file;

    public LoggingProxyHandler(T target, File file){
        this.target = target;
        this.file = file;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        var out = new FileWriter(file, true);
        out.write("method \"" + method.getName() + "\" " + new Date() + '\n');
        out.close();
        return method.invoke(target, args);
    }
}
