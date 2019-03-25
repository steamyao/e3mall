package Test.cn.e3mall.item.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: e3mall
 * @description:
 * @author: Mr.Yao
 * @create: 2019-03-23 11:19
 **/


public class TestFreemarker {

    @Test
    public void genFile() throws Exception {
        // 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 第二步：设置模板文件所在的路径。
        configuration.setDirectoryForTemplateLoading(new File("D:\\idea_space\\e3mall\\e3_item_web\\src\\main\\resources\\ftl"));
        // 第三步：设置模板文件使用的字符集。一般就是utf-8.
        configuration.setDefaultEncoding("utf-8");
        // 第四步：加载一个模板，创建一个模板对象。
        Template template = configuration.getTemplate("hellow.ftl");
        // 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
        Map dataModel = new HashMap<>();
        //向数据集中添加数据
        dataModel.put("hellow", "this is my first freemarker test.");
        // 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
        Writer out = new FileWriter(new File("D:/temp/freemarker/hellow.html"));
        // 第七步：调用模板对象的process方法输出文件。
        template.process(dataModel, out);
        // 第八步：关闭流。
        out.close();
    }

    @Test
    public void testStudent() throws Exception {
        // 第一步：创建一个Configuration对象，直接new一个对象。构造方法的参数就是freemarker对于的版本号。
        Configuration configuration = new Configuration(Configuration.getVersion());
        // 第二步：设置模板文件所在的路径。
        configuration.setDirectoryForTemplateLoading(new File("D:\\idea_space\\e3mall\\e3_item_web\\src\\main\\resources\\ftl"));
        // 第三步：设置模板文件使用的字符集。一般就是utf-8.
        configuration.setDefaultEncoding("utf-8");
        // 第四步：加载一个模板，创建一个模板对象。
        Template template = configuration.getTemplate("first.html");
        // 第五步：创建一个模板使用的数据集，可以是pojo也可以是map。一般是Map。
        Map dataModel = new HashMap<>();
        //向数据集中添加数据
        dataModel.put("title", "this is students freemarker test.");
        dataModel.put("stu",new Student(16,"小明",18,"中国"));

        List<Student> stuList = new ArrayList<Student>();
        stuList.add(new Student(1, "小明1", 18, "温都水城"));
        stuList.add(new Student(2, "小明2", 17, "温都水城"));
        stuList.add(new Student(3, "小明3", 16, "温都水城"));
        stuList.add(new Student(4, "小明4", 15, "温都水城"));
        stuList.add(new Student(5, "小明5", 14, "温都水城"));
        stuList.add(new Student(6, "小明6", 13, "温都水城"));
        stuList.add(new Student(7, "小明7", 12, "温都水城"));
        dataModel.put("stuList", stuList);

        // 第六步：创建一个Writer对象，一般创建一FileWriter对象，指定生成的文件名。
        Writer out = new FileWriter(new File("D:/temp/freemarker/student.html"));
        // 第七步：调用模板对象的process方法输出文件。
        template.process(dataModel, out);
        // 第八步：关闭流。
        out.close();
    }
}
