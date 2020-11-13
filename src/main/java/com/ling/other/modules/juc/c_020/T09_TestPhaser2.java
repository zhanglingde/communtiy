package com.ling.other.modules.juc.c_020;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

/**
 * 分阶段运行：遗传算法类问题场景使用
 *
 * 婚礼现场：所有人到了才可以吃;所有人吃饭，才可以离开；最后只有
 *
 * 洞房，其他人被拦在上一层
 */
public class T09_TestPhaser2 {
    static Random r = new Random();
    static MarriagePhaser phaser = new MarriagePhaser();


    static void milliSleep(int milli) {
        try {
            TimeUnit.MILLISECONDS.sleep(milli);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        // 总共7个线程，每个阶段的数量
        phaser.bulkRegister(7);

        for (int i = 0; i < 5; i++) {
            new Thread(new Person("p" + i)).start();
        }
        new Thread(new Person("新郎")).start();
        new Thread(new Person("新娘")).start();

    }


    static class MarriagePhaser extends Phaser {

        /**
         * @param phase             表示第几个阶段，从0开始
         * @param registeredParties 几个人参加，一个阶段有几个线程
         * @return
         */
        @Override
        protected boolean onAdvance(int phase, int registeredParties) {

            // 分阶段运行，有几个case就有几个阶段；以返回true的case作为最后一个阶段
            switch (phase) {
                case 0:
                    System.out.println("所有人到齐了！" + registeredParties);
                    System.out.println();
                    return false;
                case 1:
                    System.out.println("所有人吃完了！" + registeredParties);
                    System.out.println();
                    return false;
                case 2:
                    System.out.println("所有人离开了！" + registeredParties);
                    System.out.println();
                    return false;
                case 3:
                    System.out.println("婚礼结束！新郎新娘抱抱！" + registeredParties);
                    // retrue true;栅栏结束了
                    return true;
                default:
                    return true;
            }
        }
    }


    static class Person implements Runnable {
        String name;

        public Person(String name) {
            this.name = name;
        }


        public void arrive() {

            milliSleep(r.nextInt(1000));
            System.out.println(name + "到达现场！");
            // 没有到bulkRegister的数量7就会阻塞住
            phaser.arriveAndAwaitAdvance();
        }

        public void eat() {
            milliSleep(r.nextInt(1000));
            System.out.printf("%s 吃完!\n", name);
            phaser.arriveAndAwaitAdvance();
        }

        public void leave() {
            milliSleep(r.nextInt(1000));
            System.out.println(name + " 离开!");
            phaser.arriveAndAwaitAdvance();
        }

        private void hug() {
            if (name.equals("新郎") || name.equals("新娘")) {
                milliSleep(r.nextInt(1000));
                System.out.println(name + " 洞房!");
                phaser.arriveAndAwaitAdvance();
            } else {
                // 到这个阶段，其他线程结束
                phaser.arriveAndDeregister();
                //phaser.register()
            }
        }

        @Override
        public void run() {
            arrive();
            eat();
            leave();
            hug();
        }
    }
}


