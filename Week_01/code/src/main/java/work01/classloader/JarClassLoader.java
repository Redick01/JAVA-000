package work01.classloader;


import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * @author liu_penghui
 * @date 2020/11/12.
 */
public class JarClassLoader {

    public static void main(String[] args) throws Exception {
        URL url = new URL("Hello.xar");

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[] { url }, Thread.currentThread()
                .getContextClassLoader());
        Class<?> clazz = urlClassLoader.loadClass("Hello");
        Object object = clazz.newInstance();
        Method method = clazz.getMethod("hello", null);
        method.invoke(object, null);
    }
}
