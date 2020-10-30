package com.linkkap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linkkap.entity.ProjectDynamicData;
import com.linkkap.entity.FileData;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author yeeq
 * @date 2020/10/29
 */
public class Cracker {

    private static List<ProjectDynamicData> dataList = new ArrayList<>();

    private static List<FileData> fileDataList = new ArrayList<>();

    public void getDataList() throws IOException {
        // 创建 httpClient 客户端
        HttpClient hClient = HttpClientBuilder.create().build();
        // 创建 http 发送请求对象，Httpget
        int count = 0;
        int pageCount = Integer.MAX_VALUE;
        int dataId = 1;
        HttpResponse response;
        do {
            HttpGet hget = new HttpGet("http://query.sse.com.cn/statusAction.do?jsonCallBack=jsonpCallback15697&isPagination=true&sqlId=SH_XM_LB&pageHelp.pageSize=20&offerType=&commitiResult=&registeResult=&csrcCode=&currStatus=&order=updateDate%7Cdesc&keyword=&auditApplyDateBegin=&auditApplyDateEnd=&pageHelp.pageNo=" + count + "&pageHelp.beginPage=" + count + "&pageHelp.endPage=" + count + "&_=1603962578469");
            hget.setHeader("Accept", "*/*");
            hget.setHeader("Accept-Encoding", "gzip, deflate");
            hget.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
            hget.setHeader("Cache-Control", "no-cache");
            hget.setHeader("Connection", "keep-alive");
            hget.setHeader("Cookie", "yfx_c_g_u_id_10000042=_ck20102910164215757275331377113; JSESSIONID=506D5A43B58E1E3F9AF04CF2D83DE981; yfx_f_l_v_t_10000042=f_t_1603937802565__r_t_1603937802565__v_t_1603962579116__r_c_0");
            hget.setHeader("Host", "query.sse.com.cn");
            hget.setHeader("Pragma", "no-cache");
            hget.setHeader("Referer", "http://kcb.sse.com.cn/");
            hget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36 Edg/86.0.622.51");
            response = hClient.execute(hget);
            // 获取网页内容
            String content = EntityUtils.toString(response.getEntity(), "utf-8");
            String json = content.substring(19, content.length() - 1);
            JSONObject jsonObject = JSON.parseObject(json);
            JSONObject jsonPageHelp = jsonObject.getJSONObject("pageHelp");
            JSONArray jsonArray = jsonPageHelp.getJSONArray("data");
            for (int i = 0; i < jsonArray.size(); i++) {
                String stockAuditName = jsonArray.getJSONObject(i).getString("stockAuditName");
                String stockAuditNum = jsonArray.getJSONObject(i).getString("stockAuditNum");
                String url = "http://kcb.sse.com.cn/renewal/xmxq/index.shtml?auditId=" + stockAuditNum + "&anchor_type=0";
                dataList.add(new ProjectDynamicData(dataId++, stockAuditNum, stockAuditName, url));
            }
            pageCount = Integer.parseInt(jsonPageHelp.getString("pageCount"));
            count++;
        } while (count <= pageCount);
        for (ProjectDynamicData data : dataList) {
            System.out.println(data);
        }
//        System.out.println(jsonObject.toString());
//        System.out.println(content);
        // 使用Jsoup解析网页内容
//        Document document = Jsoup.parse(content);
////         使用元素选择器选择网页的内容
//        Elements elements = document.select("tbody");
//        System.out.println(tbody);
//        System.out.println(doc2);

//        Runtime rt = Runtime.getRuntime();
//        String exec = "G:/SSS/tools/phantomjs-2.1.1-windows/bin/phantomjs G:/SSS/tools/phantomjs-2.1.1-windows/bin/code.js http://kcb.sse.com.cn/renewal/";
//        Process p = rt.exec(exec);
//        InputStream is = p.getInputStream();
//
//        Document doc = Jsoup.parse(is, "UTF-8", "http://kcb.sse.com.cn/renewal/");
//
//        doc.select("tbody").first().select("tr").forEach((element -> {
//            Elements id = element.select("td.td_no_break.text-align-center");
//            Elements publisher = element.select("td.td_break_word_7 a");
//            Element industry = element.select("td.td_break_word_3 a").last();
//            if (id != null) {
//                String idText = id.text();
//                System.out.println(idText);
//            }
//            if (publisher != null) {
//                String publisherText = publisher.text().replaceAll(" ", "");
//                System.out.println(publisherText);
//            }
//            if (industry != null) {
//                String industryText = industry.text().replaceAll(" ", "");
//                System.out.println(industryText);
//            }
//        }));

//        String exec2 = "G:/SSS/tools/phantomjs-2.1.1-windows/bin/phantomjs G:/SSS/tools/phantomjs-2.1.1-windows/bin/code.js http://query.sse.com.cn/statusAction.do?jsonCallBack=jsonpCallback71621&isPagination=true&sqlId=SH_XM_LB&pageHelp.pageSize=20&offerType=&commitiResult=&registeResult=&csrcCode=&currStatus=&order=updateDate%7Cdesc&keyword=&auditApplyDateBegin=&auditApplyDateEnd=&pageHelp.pageNo=1&pageHelp.beginPage=1&pageHelp.endPage=1&_=1603959293728";
//        Process p2 = rt.exec(exec);
//        InputStream is2 = p.getInputStream();
//        Document doc2 = Jsoup.parse(is, "UTF-8", "http://query.sse.com.cn/statusAction.do?jsonCallBack=jsonpCallback71621&isPagination=true&sqlId=SH_XM_LB&pageHelp.pageSize=20&offerType=&commitiResult=&registeResult=&csrcCode=&currStatus=&order=updateDate%7Cdesc&keyword=&auditApplyDateBegin=&auditApplyDateEnd=&pageHelp.pageNo=1&pageHelp.beginPage=1&pageHelp.endPage=1&_=1603959293728");
//        // 创建httpClient客户端
//        HttpClient hClient = new DefaultHttpClient();
//        // 创建http发送请求对象，Httpget
//        HttpGet hget = new HttpGet("http://kcb.sse.com.cn/renewal/");
//        // 发送请求
//        HttpResponse response = hClient.execute(hget);
//        // 获取网页内容
//        String content = EntityUtils.toString(response.getEntity(), "utf-8");
//        // 使用Jsoup解析网页内容
//        Document document = Jsoup.parse(content);
        // 使用元素选择器选择网页的内容
//        Elements elements = document.select("tbody");
//        System.out.println(tbody);
//        System.out.println(doc2);
    }

    public void getProspectus() throws IOException {
        getDataList();
        // 创建 httpClient 客户端
        HttpClient hClient = new DefaultHttpClient();
        HttpResponse response;
        for (ProjectDynamicData data : dataList) {
            HttpGet hget = new HttpGet("http://query.sse.com.cn/commonSoaQuery.do?jsonCallBack=jsonpCallback24590788&isPagination=false&sqlId=GP_GPZCZ_SHXXPL&stockAuditNum=" + data.getStockAuditNum() + "&_=1603982976887");
            hget.setHeader("Accept", "*/*");
            hget.setHeader("Accept-Encoding", "gzip, deflate");
            hget.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
            hget.setHeader("Cache-Control", "no-cache");
            hget.setHeader("Connection", "keep-alive");
            hget.setHeader("Cookie", "yfx_c_g_u_id_10000042=_ck20102910164215757275331377113; JSESSIONID=506D5A43B58E1E3F9AF04CF2D83DE981; yfx_f_l_v_t_10000042=f_t_1603937802565__r_t_1603937802565__v_t_1603962579116__r_c_0");
            hget.setHeader("Host", "query.sse.com.cn");
            hget.setHeader("Pragma", "no-cache");
            hget.setHeader("Referer", "http://kcb.sse.com.cn/");
            hget.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36 Edg/86.0.622.51");
            response = hClient.execute(hget);
            // 获取网页内容
            String content = EntityUtils.toString(response.getEntity(), "utf-8");
            String json = content.substring(22, content.length() - 1);
            JSONObject jsonObject = JSON.parseObject(json);
            JSONObject jsonPageHelp = jsonObject.getJSONObject("pageHelp");
            JSONArray jsonArray = jsonPageHelp.getJSONArray("data");
            Iterator<Object> iterator = jsonArray.iterator();
            String filePath = null;
            String filePath2 = null;
            while (iterator.hasNext()) {
                JSONObject next = (JSONObject) iterator.next();
                String fileTitle = next.getString("fileTitle");
                if (fileTitle.contains("上会稿")) {
                    filePath = next.getString("filePath");
                }
                if (fileTitle.contains("申报稿") && filePath == null) {
                    filePath = next.getString("filePath");
                }
                if (fileTitle.contains("发行人及保荐机构回复意见")) {
                    filePath2 = next.getString("filePath");
                }
            }
            FileData fileData = new FileData(data.getId(), data.getPublisher(), filePath, filePath2);
            fileDataList.add(fileData);
        }
        System.out.println("-------------------------------");
        for (FileData fileData : fileDataList) {
            System.out.println(fileData);
        }
    }

    public void downLoadByUrl() throws IOException {
        getProspectus();
        for (FileData fileData : fileDataList) {
            URL url = new URL("http://static.sse.com.cn/stock" + fileData.getProspectusUrl());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // 设置超时间为3秒
            conn.setConnectTimeout(5 * 1000);
            // 防止屏蔽程序抓取而返回403错误
            conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
            // 得到输入流
            InputStream inputStream = conn.getInputStream();
            // 获取自己数组
            byte[] getData = readInputStream(inputStream);
            // 文件保存位置
            File saveDir = new File("G:/pdf");
            if (!saveDir.exists()) {
                saveDir.mkdir();
            }
            File file = new File(saveDir + File.separator + fileData.getId() + "-" + fileData.getPublisher() + "招股书.pdf");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            if (fos != null) {
                fos.close();
            }
            if (inputStream != null) {
                inputStream.close();
            }
            System.out.println("info:" + url + " download success");
        }

    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static byte[] readInputStream(InputStream inputStream)
            throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
