package cache.homework;

/**
 * @author liupenghui
 * @date 2021/3/4 10:41 上午
 */
public class Test {

    private int result;

    public int getResult() {
        return result;
    }
    public synchronized void setResult(int result){
        this.result = result;
    }

    public static void main(String[] args){
        Test threadSafeCache =new Test();
        for(int i =0; i <8; i++) {
            new Thread(() -> {
                while(threadSafeCache.getResult() <100) {

                }
                System.out.println("我执行了");

            }).start();
        }try{
            Thread.sleep(1000);
        }catch(InterruptedException e) {
            e.printStackTrace();
        }
        threadSafeCache.setResult(200);
    }
}
