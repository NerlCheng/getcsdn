package com.getcsdn.getcsdn;

import java.io.*;
import java.util.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.DocumentException;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import org.xhtmlrenderer.pdf.ITextRenderer;
import com.getcsdn.getcsdn.Handler.contractHandler;

@Controller
public class mysearchtest {
    private static String blogName = "aiming66";
    private static final String PRE_URL = "https://blog.csdn.net/";
    private static final String SUF_URL = "/article/list/";
    List<String>listArticle = new ArrayList<>();

    List<Element> elementList = new ArrayList<>();
    static String PDFNAME = "cjppdf";//pdf文件名
    static String HTMLNAME = "cjpthml";//html文件名





    @Resource
    private getCsdnTitleAndUrl getCsdnTitleAndUrl;
    @Resource
    private  getCsdnArticle getCsdnArticle;
    @Resource
    private contractHandler contractHandler;


    @RequestMapping("/getcsdn")
    public @ResponseBody void   main(String[] args) {
        try {
            String ul="https://blog.csdn.net/aiming66/article/details/95164488";
//            获取博客的连接和标题
//            List<String[]> list = getCsdnTitleAndUrl.getcsdn(PRE_URL, SUF_URL,blogName);
            getCsdnArticle.getArticleFromUrl(ul,blogName, elementList);
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


    /**
     * 保存文章到本地
     *
     * @param titile
     * @param content
     * @param blogName
     */
    public static void saveArticle(String titile, String content, String blogName) {
        String lujing = "d:\\MyLoadArticle\\" + blogName + "\\" + titile + ".txt";//保存到本地的路径和文件名
        File file = new File(lujing);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            FileWriter fw = new FileWriter(file, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(content);
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }






    /**
     * @Description: 由html生成PDF
     * @Param:htmUrl html的URL
     * @param: pdfUrl pdf的URL
     * @return: 4:15 PM
     * @Author: 杨光彩
     * @Date: 5/16/2019
     */
    private void pdfHandler(String htmUrl, String pdfUrl)
            throws DocumentException, IOException {
        File htmFile = new File(htmUrl);
        File pdfFile = new File(pdfUrl);

        String url = htmFile.toURI().toURL().toString();

        OutputStream os = new FileOutputStream(pdfFile);

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocument(url);

        org.xhtmlrenderer.pdf.ITextFontResolver fontResolver = renderer
                .getFontResolver();
        // 解决中文支持问题
        String fileUrl = this.getClass().getClassLoader().getResource("templates/simsun.ttc").getPath();
        try {
            fontResolver.addFont(fileUrl,
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (com.itextpdf.text.DocumentException e) {
            System.out.println("由html生成pdf失败");
        }

        try {
            fontResolver.addFont(fileUrl, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        } catch (com.itextpdf.text.DocumentException e) {
            System.out.println("由html生成pdf失败");
        }

        renderer.layout();
        try {
            renderer.createPDF(os);
            renderer.finishPDF();
        } catch (com.itextpdf.text.DocumentException e) {
            System.out.println("由html生成pdf失败");
        }
        os.close();
    }
}

