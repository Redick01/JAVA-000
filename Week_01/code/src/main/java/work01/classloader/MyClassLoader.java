package work01.classloader;

import java.io.*;
import java.lang.reflect.Method;

/**
 * @author liu_penghui
 * @date 2020/10/16.
 */
public class MyClassLoader extends ClassLoader {

    public static void main(String[] args) throws Exception {
        Class<?> aClass = new MyClassLoader().findClass("Hello");
        Object obj = aClass.newInstance();
        Method method = aClass.getMethod("hello");
        method.invoke(obj);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        byte[] bytes = new byte[0];
        try {
            String path = MyClassLoader.class.getResource("Hello.xlass").getPath();
            bytes = readBytes(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return defineClass(name, bytes, 0, bytes.length);
    }

    /**
     * 读取文件字节码
     * @return
     */
    private byte[] readBytes(String path) throws IOException {
        File file = new File(path);
        InputStream inputStream = null;
        //  字节码文件内容
        byte[] readBytes = new byte[(int)file.length()];
        try {
            byte[] bytes = new byte[(int)file.length()];
            inputStream = new FileInputStream(file);
            inputStream.read(bytes);
            for (int i = 0; i < bytes.length; i++) {
                readBytes[i] = (byte) (255 - bytes[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != inputStream) {
                inputStream.close();
            }
        }
        return readBytes;
    }
}
