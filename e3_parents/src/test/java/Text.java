/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-03-03 10:05
 **/


public class Text {

        public static int k = 0;
        public static Text t1 = new Text("t1");
        public static Text t2 = new Text("t2");
        public static int i = print("i");
        public static int n = 99;
        public int j = print("j");

        {
            print("构造块");
        }
        static {
            print("静态块");
        }

        public Text(String str) {
            System.out.println((++k) + ":" + str + "   i=" + i + "    n=" + n);
            ++i;
            ++n;
        }

        public static int print(String str) {
            System.out.println((++k) + ":" + str + "   i=" + i + "    n=" + n);
            ++n;
            return ++i;
        }

        public static void main(String args[]) {
            Text t = new Text("init");
        }

}
