package com.thread.homework;

import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Homework03 {
    private static ExecutorService executorService;

    private final static AtomicInteger threadNumber = new AtomicInteger(1);

    private static final int core = Runtime.getRuntime().availableProcessors() * 2;
    private static int result = 0;

    private static volatile AtomicInteger semaphore = new AtomicInteger(0);

    public static void main(String[] args) {
        try {
            long start=System.currentTimeMillis();
            // 在这里创建一个线程或线程池，
            executorService = new ThreadPoolExecutor(core, core,
                    1000, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<Runnable>(1024),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(Thread.currentThread().getThreadGroup(), r, "homeWork03" + "-thread-" + threadNumber.getAndIncrement(), 0);
                            return t;
                        }
                    }, new ThreadPoolExecutor.CallerRunsPolicy());

            // 异步执行 下面方法

            fun_11();
            // 确保  拿到result 并输出
            System.out.println("异步计算结果为："+result);

            System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
            // 然后退出main线程
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 退出主线程
            executorService.shutdown();
        }
    }

    /**
     * 方法1 使用CountDownLatch
     * @return
     * @throws InterruptedException
     */
    public static int fun_01() throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        executorService.execute(() -> {
            // 返回值
            result = sum();
            countDownLatch.countDown();
        });
        countDownLatch.await();
        return result;
    }

    /**
     * 方法2 使用 CyclicBarrier 设置两个屏障，如果子线程未到达屏障主线程到达屏障，主线程等待子线程，因为这里是一个线程，所以得这么干，要至少两个线程相互等
     * @return
     * @throws Exception
     */
    public static int fun_02() throws Exception {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);

        executorService.execute(() -> {
            try {
                result = sum();
                cyclicBarrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        cyclicBarrier.await();
        return result;
    }

    /**
     * 方法3 使用 CompletableFuture
     * @return
     */
    public static int fun_03() {
        result = CompletableFuture.supplyAsync(()->{
            return sum();
        }).join();
        return result;
    }

    /**
     * 方法4 使用Future
     * @return
     * @throws Exception
     */
    public static int fun_04() throws Exception {
        Future<Integer> result1 = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        return result1.get();
    }

    /**
     * 方法5，使用FutureTask
     * @return
     * @throws Exception
     */
    public static int fun_05() throws Exception {
        FutureTask<Integer> futureTask = new FutureTask<Integer>(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                return sum();
            }
        });
        executorService.submit(futureTask);
        return futureTask.get();
    }

    /**
     * 方法6，使用Semaphore，1、在主线程中获取信号量；2、在任务线程中如果处理完释放信号量；3、在提交任务后再次获取信号量，此时只有任务线程释放了信号量主线程才能获取信号量
     * @return
     * @throws Exception
     */
    public static int fun_06() throws Exception {
        final Semaphore semaphore = new Semaphore(1);
        semaphore.acquire();
        executorService.execute(() -> {
            result = sum();
            semaphore.release();
        });
        semaphore.acquire();
        semaphore.release();
        return result;
    }

    /**
     * 方法7 使用阻塞队列LinkedBlockingQueue，1、任务线程将sum()结果添加到队列中；2、主线程take队列，当队列为空时候take阻塞；3、take不为空，不阻塞放行
     * @return
     * @throws Exception
     */
    public static int fun_07() throws Exception {
        LinkedBlockingQueue<Integer> queue = new LinkedBlockingQueue();
        executorService.execute(() -> {
            result = sum();
            queue.add(result);
        });
        queue.take();
        return result;
    }

    /**
     * 方法8 使用Map，或者其他的集合类也可以，该种方式相当于我们自己手动的阻塞主线程，子线程操作与主线程共享内存以达到退出阻塞主线程条件
     * @return
     */
    public static int fun_08() {
        Map<Integer, Integer> map = new ConcurrentHashMap<>();
        executorService.execute(() -> {
            result = sum();
            map.put(result, result);
        });
        while (map.size() == 0) {

        }
        return result;
    }

    /**
     * 方法9 自定义计数器，1、自己定义volatile修饰的计数器；2、当任务线程执行完+1；3、主线程由while阻塞；4、达到条件跳出while
     * @return
     */
    public static int fun_09() {
        executorService.execute(() -> {
            result = sum();
            semaphore.addAndGet(1);
        });
        while (semaphore.intValue() == 0) {

        }
        return result;
    }

    /**
     * 方法10 使用wait和notifyAll
     * @return
     * @throws Exception
     */
    public static int fun_10() throws Exception {
        Homework03 homework03 = new Homework03();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (homework03) {
                    System.out.println("获取到homework03锁，开始执行");
                }
            }
        });
        thread.start();
        executorService.execute(() -> {
            synchronized (homework03) {
                result = sum();
                try {
                    homework03.notifyAll();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        synchronized (homework03) {
            System.out.println("主线程获取锁");
            homework03.wait();
        }
        return result;
    }

    /**
     * 方法11 使用Condition
     * @return
     * @throws Exception
     */
    public static int fun_11() throws Exception {
        final Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    lock.lock();
                    System.out.println("获取到锁，开始执行");
                    lock.unlock();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });
        thread.start();
        executorService.execute(() -> {
            lock.lock();
            result = sum();
            condition.signalAll();
            lock.unlock();
        });
        lock.lock();
        condition.await();
        lock.unlock();
        return result;
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
