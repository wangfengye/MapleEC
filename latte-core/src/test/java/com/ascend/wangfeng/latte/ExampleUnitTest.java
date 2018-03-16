package com.ascend.wangfeng.latte;

import com.alibaba.fastjson.JSON;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testGson() throws Exception {
        Demo demo = new Demo(0.02);
        System.out.println(JSON.toJSONString(demo));

    }
    public class Demo{
        private double x;

        public Demo(double x) {
            this.x = x;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        @Override
        public String toString() {
            return "Demo{" +
                    "x=" + x +
                    '}';
        }
    }
}