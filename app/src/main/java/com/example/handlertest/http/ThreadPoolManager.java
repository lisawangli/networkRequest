package com.example.handlertest.http;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolManager {
    private static ThreadPoolManager threadPoolManager = new ThreadPoolManager();

    public static ThreadPoolManager getInstance(){
        return threadPoolManager;
    }

    //创建队列，将网络请求的任务添加到队列中
    private LinkedBlockingQueue<Runnable> mQueue = new LinkedBlockingQueue<>();

    //将任务添加到队列中
    public void addTask(Runnable runnable){
        if (runnable!=null){
            try {
                mQueue.put(runnable);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //创建线程池
    public ThreadPoolExecutor mThreadPoolExecutor;

    private ThreadPoolManager(){
        mThreadPoolExecutor = new ThreadPoolExecutor(3, 4, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(4), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                //将拒绝的线程重新放回队列
                addTask(r);
            }
        });
        mThreadPoolExecutor.execute(coreThread);
    }
    //创建叫号员线程，不停的获取
    public Runnable coreThread = new Runnable() {
      Runnable runn = null;
        @Override
        public void run() {
            while (true){
                try {
                    runn = mQueue.take();
                    Log.e("ThreadPoolManager","不停的取任务");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mThreadPoolExecutor.execute(runn);
            }
        }
    };
}
