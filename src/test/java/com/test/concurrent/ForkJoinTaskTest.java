package com.test.concurrent;

import com.weshare.sdk.util.*;
import org.junit.*;

import java.math.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Created by bj-s2-w1631 on 18-8-3.
 */
public class ForkJoinTaskTest {

    @Test
    public void testTask() {
        int now = Utility.getCurrentTimeStamp();
        int start = Utility.getDayStartTime(now);
        int end = Utility.getDayEndTime(now);
        long startTime = System.currentTimeMillis();
        ForkJoinPool pool = new ForkJoinPool();
        Task task = new Task(start, end);
        ForkJoinTask<BigDecimal> result = pool.submit(task);
        System.out.println(pool.toString());
        try {
            BigDecimal sum = result.get();
            System.out.println(pool.toString());
            System.out.println("sum = " + sum);
            System.out.println("time:" + (System.currentTimeMillis() - startTime));
            pool.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }


    public static class Task extends RecursiveTask<BigDecimal> {
        private int start;
        private int end;

        public Task(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public BigDecimal compute() {
            BigDecimal sum = new BigDecimal(0);
            if (end <= start) {
                return sum;
            }

            if (end - start < 3600) {
                sum = sum.add(new BigDecimal(100));
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {

                }
            } else {
                int re = (end - start) / 3600;
                int le = (end - start) % 3600;
                int num = le == 0 ? re : re + 1;
                System.out.println("num=" + num);
                List<Task> taskList = new ArrayList<>();

                int pos = start;
                for (int i = 0; i < num; i++) {
                    int posEnd = pos + 3600 - 1;
                    if (posEnd > end) {
                        posEnd = end;
                    }
                    Task subTask = new Task(pos, posEnd);
                    pos += 3600;
                    taskList.add(subTask);
                    subTask.fork();
                }

                for (Task task : taskList) {
                    sum = sum.add(task.join());
                }
            }
            return sum;
        }
    }
}
