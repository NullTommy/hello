package MyJunit;

import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by panghaichen on 2018-06-29 14:13
 **/
public class TestJunit {

    /**
     * 只执行一次,在整个类执行之前执行
     */
    @BeforeClass
    public static void beforeClass(){
        System.out.println("BeforeClass");
    }

    /**
     * 只执行一次,在整个类执行之后执行
     */
    @AfterClass
    public static void afterClass(){
        System.out.println("AfterClass");
    }

    /**
     * 每个测试方法被执行前都被执行一次
     */
    @Before
    public void before() {
        System.out.println("in before");
    }

    /**
     * 每个测试方法被执行后都被执行一次
     */
    @After
    public void after() {
        System.out.println("in after");
    }

    @Test
    public void test() {
        Junit junit = new Junit();
        assertEquals(8, junit.add(3, 5));
    }

    @Test
    public void test2() {
        Junit junit = new Junit();
        assertEquals(8, junit.add(3, 5));
    }

    @Test
    @Ignore
    public void test3() {
        Junit junit = new Junit();
        assertEquals(8, junit.add(3, 5));
    }

}
