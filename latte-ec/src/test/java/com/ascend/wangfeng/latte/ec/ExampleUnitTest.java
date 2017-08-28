package com.ascend.wangfeng.latte.ec;

import org.junit.Test;

import java.text.MessageFormat;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

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

    /**
     *  测试for循环
     * @throws Exception
     */
    @Test
    public void testFor() throws Exception {
        ArrayList<String> list =new ArrayList<>();
        for (int i = 0; i < 1000000; i++) {
            list.add("test");
        }
        long start =System.currentTimeMillis();
        int size =list.size();
        for (int i = 0; i < size; i++) {
             list.set(i,"testafter");
        }
        long diff =System.currentTimeMillis()-start;
        System.out.println(MessageFormat.format("-----方案1:耗时{0}ms-----",diff));
        long start2 =System.currentTimeMillis();
        for (int i = 0; i < list.size(); i++) {
            list.set(i,"testafter");
        }
        long diff2 =System.currentTimeMillis()-start2;
        System.out.println(MessageFormat.format("-----方案2:耗时{0}ms-----",diff2));
    }

}