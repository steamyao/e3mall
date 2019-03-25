import cn.e3mall.redis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-02-14 17:54
 **/

public class TestJedis {
    @Test
    public void testJedis() throws Exception {
        // 第一步：创建一个Jedis对象。需要指定服务端的ip及端口。
        Jedis jedis = new Jedis("39.108.0.169", 6379);
        // 第二步：使用Jedis对象操作数据库，每个redis命令对应一个方法。
        String result = jedis.get("hello");
        // 第三步：打印结果。
        System.out.println(result);
        // 第四步：关闭Jedis
        jedis.close();
    }

    /**
     * 使用连接池
     * @throws Exception
     */
    @Test
    public void testJedisPool() throws Exception {
        // 第一步：创建一个JedisPool对象。需要指定服务端的ip及端口。
        JedisPool jedisPool = new JedisPool("39.108.0.169", 6379);
        // 第二步：从JedisPool中获得Jedis对象。
        Jedis jedis = jedisPool.getResource();
        // 第三步：使用Jedis操作redis服务器。
        jedis.set("jedis", "test");
        String result = jedis.get("jedis");
        System.out.println(result);
        // 第四步：操作完毕后关闭jedis对象，连接池回收资源。
        jedis.close();
        // 第五步：关闭JedisPool对象。
        jedisPool.close();
    }

    /**
     * 连接集群版
     * @throws Exception
     */
    @Test
    public void testJedisCluster() throws Exception {
        // 第一步：使用JedisCluster对象。需要一个Set<HostAndPort>参数。Redis节点的列表。
        Set<HostAndPort> nodes = new HashSet<>();
        nodes.add(new HostAndPort("39.108.0.169", 7001));
        nodes.add(new HostAndPort("39.108.0.169", 7002));
        nodes.add(new HostAndPort("39.108.0.169", 7003));
        nodes.add(new HostAndPort("39.108.0.169", 7004));
        nodes.add(new HostAndPort("39.108.0.169", 7005));
        nodes.add(new HostAndPort("39.108.0.169", 7006));
        JedisCluster jedisCluster = new JedisCluster(nodes);
        // 第二步：直接使用JedisCluster对象操作redis。在系统中单例存在。
        jedisCluster.set("hello", "100");
        String result = jedisCluster.get("hello");
        // 第三步：打印结果
        System.out.println(result);
        // 第四步：系统关闭前，关闭JedisCluster对象。
        jedisCluster.close();
    }


   //测试配置文件
    @Test
    public void testJedisClient() throws Exception {
        //初始化Spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
        //从容器中获得JedisClient对象
        JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
        jedisClient.set("first", "100");
        String result = jedisClient.get("first");
        System.out.println(result);


    }
}
