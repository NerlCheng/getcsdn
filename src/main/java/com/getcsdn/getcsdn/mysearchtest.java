package com.getcsdn.getcsdn;

import java.io.*;
import java.net.URL;
import java.util.*;
import com.itextpdf.text.pdf.BaseFont;

import com.itextpdf.text.DocumentException;
import com.sun.org.apache.bcel.internal.generic.NEW;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Controller
public class mysearchtest {
    private static String blogName = "aiming66";
    private static final String PRE_URL = "https://blog.csdn.net/";
    private static final String SUF_URL = "/article/list/";
    List<String>listArticle = new ArrayList<>();

    List<Element> elementList = new ArrayList<>();
    static String PDFNAME = "cjppdf";//pdf文件名
    static String HTMLNAME = "cjpthml";//html文件名

    private static final String CONTRACT = ConfigUtil.pro.getProperty("export.examPaper.CONTRACT");



    @Resource
    private getCsdnTitleAndUrl getCsdnTitleAndUrl;
    @Resource
    private  getCsdnArticle getCsdnArticle;



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
            elementList.forEach(a -> paramMap.put("csdnContent", elementList.toString().replaceAll("<br>","<br></br>")));
            contractHandler(templateName, paramMap, PDFNAME, HTMLNAME);




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


    public void contractHandler(String templateName, Map<String,
            Object> paramMap, String pdfName, String htmlName) throws Exception {
        // 获取本地模板存储路径、合同文件存储路径
        String fileUrl = this.getClass().getClassLoader().getResource("templates").getPath();
        String templatePath = fileUrl;
        String contractPath = CONTRACT;
        // 组装html和pdf合同的全路径URL
        String localHtmlUrl = contractPath + htmlName + ".html";
        String localPdfPath = contractPath + "/" + "chengjinpenggetcsdn";
        // 判断本地路径是否存在如果不存在则创建
        File localFile = new File(localPdfPath);
        if (!localFile.exists()) {
            localFile.mkdirs();
        }
        String localPdfUrl = localFile + "/" + pdfName + ".pdf";
        templateName = templateName + ".ftl";
        htmHandler(templatePath, templateName, localHtmlUrl, paramMap);// 生成html合同
        pdfHandler(localHtmlUrl, localPdfUrl);// 根据html合同生成pdf合同
//        deleteFile(localHtmlUrl);// 删除html格式合同
        System.out.println("PDF生成成功");
    }

    /**
     * @Description: 由ftl模板生成HTML文件
     * @Param:templatePath 模板路径
     * @param:templateName 模板名称
     * @param:htmUrl html的URL
     * @param:paramMap 存放参数的map
     * @return: 4:13 PM
     * @Author: 杨光彩
     * @Date: 5/16/2019
     */
    private static void htmHandler(String templatePath, String templateName,
                                   String htmUrl, Map<String, Object> paramMap) throws Exception {
        Configuration cfg = new Configuration();
        cfg.setDefaultEncoding("UTF-8");
        cfg.setDirectoryForTemplateLoading(new File(templatePath));

        Template template = cfg.getTemplate(templateName);

        File outHtmFile = new File(htmUrl);

        Writer out = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(outHtmFile), "utf-8"));
        template.process(paramMap, out);

        out.close();
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

