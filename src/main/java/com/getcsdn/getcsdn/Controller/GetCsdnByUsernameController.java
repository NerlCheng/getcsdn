package com.getcsdn.getcsdn.Controller;

import java.io.*;
import java.util.*;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.getcsdn.getcsdn.Handler.contractHandler;

@Controller
public class GetCsdnByUsernameController {
    private static String blogName = "aiming66";
    private static final String PRE_URL = "https://blog.csdn.net/";
    private static final String SUF_URL = "/article/list/";
    List<String> listArticle = new ArrayList<>();

    List<Element> elementList = new ArrayList<>();
    static String PDFNAME = "cjppdf";//pdf文件名
    static String HTMLNAME = "cjpthml";//html文件名


    @Resource
    private com.getcsdn.getcsdn.getCsdnTitleAndUrl getCsdnTitleAndUrl;
    @Resource
    private com.getcsdn.getcsdn.getCsdnArticle getCsdnArticle;
    @Resource
    private contractHandler contractHandler;


    @RequestMapping("/getcsdn")
    public @ResponseBody
    void main(String[] args) {
        try {
            String ul = "https://blog.csdn.net/aiming66/article/details/95164488";
//            获取博客的连接和标题
//            List<String[]> list = getCsdnTitleAndUrl.getcsdn(PRE_URL, SUF_URL,blogName);
            getCsdnArticle.getArticleFromUrl(ul, blogName, elementList);
//            list.forEach(e -> getCsdnArticle.getArticleFromUrl(e[0], blogName, elementList));


            String templateName = "getcsdn";
            Map<String, Object> paramMap = new HashMap<>();
            elementList.forEach(a -> paramMap.put("csdnContent", elementList.toString()));
            contractHandler.contractHandler(templateName, paramMap, PDFNAME, HTMLNAME);
        } catch (IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            e.printStackTrace();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


//    /**
//     * 保存文章到本地
//     *
//     * @param titile
//     * @param content
//     * @param blogName
//     */
//    public static void saveArticle(String titile, String content, String blogName) {
//        String lujing = "d:\\MyLoadArticle\\" + blogName + "\\" + titile + ".txt";//保存到本地的路径和文件名
//        File file = new File(lujing);
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//        try {
//            file.createNewFile();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        try {
//            FileWriter fw = new FileWriter(file, true);
//            BufferedWriter bw = new BufferedWriter(fw);
//            bw.write(content);
//            bw.flush();
//            bw.close();
//            fw.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


}

