import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-03-21 20:13
 **/


public class TestActiceMQ {
    @Test
    public void testQueueConsumer() throws Exception {
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activemq.xml");
        //等待
        System.in.read();
    }
}
