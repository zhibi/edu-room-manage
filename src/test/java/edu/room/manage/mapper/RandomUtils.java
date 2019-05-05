package edu.room.manage.mapper;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

/**
 * @author 执笔
 * @date 2019/5/5 10:12
 */
public class RandomUtils {

    @Test
    public void test(){
        System.out.println(RandomStringUtils.randomNumeric(6));
    }
}
