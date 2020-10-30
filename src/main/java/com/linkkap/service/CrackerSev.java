package com.linkkap.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.linkkap.dao.CrackerDao;
import com.linkkap.entity.ProjectDynamicData;
import com.linkkap.entity.FileData;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class CrackerSev {

    @Autowired
    private CrackerDao crackerDao;

    /**
     * 获取科创板项目动态信息列表
     *
     * @return 科创板项目动态信息列表
     * @throws IOException IOException
     */
    public List<ProjectDynamicData> getDataList() throws IOException {

        List<ProjectDynamicData> dataList = new ArrayList<>();
        // 创建 httpClient 客户端
        HttpClient hClient = HttpClientBuilder.create().build();
        // 计数
        int count = 1;
        // 总页数
        int pageCount;
        // 序号
        int dataId = 1;
        HttpResponse response;
        do {
            // 创建 http 发送请求对象
            HttpGet get = new HttpGet("http://query.sse.com.cn/statusAction.do?jsonCallBack=jsonpCallback15697&isPagination=true&sqlId=SH_XM_LB&pageHelp.pageSize=20&offerType=&commitiResult=&registeResult=&csrcCode=&currStatus=&order=updateDate%7Cdesc&keyword=&auditApplyDateBegin=&auditApplyDateEnd=&pageHelp.pageNo=" + count + "&pageHelp.beginPage=" + count + "&pageHelp.endPage=" + count + "&_=1603962578469");
            // 设置请求头
            setHttpGetHeader(get);
            response = hClient.execute(get);
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
        return dataList;
    }

    /**
     * 将科创板项目动态信息列表保存到数据库
     * @param dataList 科创板项目动态信息列表
     */
    public void saveDataList(List<ProjectDynamicData> dataList) {

        crackerDao.deleteAllProjectDynamicData();
        crackerDao.createProjectDynamicData(dataList);
    }

    /**
     * 获取科创板项目招股书和问询报告文件信息列表
     *
     * @return 科创板项目招股书和问询报告文件信息列表
     * @throws IOException IOException
     */
    public List<FileData> getFileDataList() throws IOException {

        List<ProjectDynamicData> projectDynamicDataList = crackerDao.selectProjectDynamicData();
        if (projectDynamicDataList.size() == 0) {
            saveDataList(getDataList());
            projectDynamicDataList = crackerDao.selectProjectDynamicData();
        }
        List<FileData> fileDataList = new ArrayList<>();
        // 创建 httpClient 客户端
        HttpClient hClient = HttpClientBuilder.create().build();
        HttpResponse response;
        for (ProjectDynamicData data : projectDynamicDataList) {
            HttpGet get = new HttpGet("http://query.sse.com.cn/commonSoaQuery.do?jsonCallBack=jsonpCallback24590788&isPagination=false&sqlId=GP_GPZCZ_SHXXPL&stockAuditNum=" + data.getStockAuditNum() + "&_=1603982976887");
            setHttpGetHeader(get);
            response = hClient.execute(get);
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
            FileData fileData = new FileData(data.getPid(), data.getStockAuditName(), filePath, filePath2);
            fileDataList.add(fileData);
        }
        for (FileData fileData : fileDataList) {
            System.out.println(fileData);
        }
        return fileDataList;
    }

    /**
     * 将科创板项目动态信息列表保存到数据库
     * @param fileDataList 科创板项目动态信息列表
     */
    public void saveFileDataList(List<FileData> fileDataList) {
        crackerDao.deleteAllFileData();
        crackerDao.createFileData(fileDataList);
    }

    /**
     * 下载招股书和问询报告
     * @throws IOException IOException
     */
    public void downLoadFileByUrl() throws IOException {

        List<FileData> fileDataList = crackerDao.selectFileData();
        if (fileDataList.size() == 0) {
            saveFileDataList(getFileDataList());
            fileDataList = crackerDao.selectFileData();
        }
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
            // 招股书保存位置
            File saveDir1 = new File("G:/pdf6");
//            File saveDir = new File(savePath);
            // 问询报告保存位置
            File saveDir2 = new File("G:/pdf7");
//            File saveDir2 = new File("G:/pdf2");
            if (!saveDir1.exists()) {
                boolean mkdir = saveDir1.mkdir();
                if (!mkdir) {
                    throw new RuntimeException("招股书文件夹创建失败");
                }
            }if (!saveDir2.exists()) {
                boolean mkdir = saveDir2.mkdir();
                if (!mkdir) {
                    throw new RuntimeException("咨询报告文件夹创建失败");
                }
            }
            // 保存招股书
            File file = new File(saveDir1 + File.separator + fileData.getFid() + "-" + fileData.getStockAuditName() + "招股书.pdf");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(getData);
            // 保存咨询报告
            file = new File(saveDir2 + File.separator + fileData.getFid() + "-" + fileData.getStockAuditName() + "问询报告.pdf");
            fos = new FileOutputStream(file);
            fos.close();
            inputStream.close();
            System.out.println("info:" + url + " download success");
        }
    }

    /**
     * 设置 Get 请求头
     * @param get Get 请求
     */
    private void setHttpGetHeader(HttpGet get) {
        get.setHeader("Accept", "*/*");
        get.setHeader("Accept-Encoding", "gzip, deflate");
        get.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6");
        get.setHeader("Cache-Control", "no-cache");
        get.setHeader("Connection", "keep-alive");
        get.setHeader("Cookie", "yfx_c_g_u_id_10000042=_ck20102910164215757275331377113; JSESSIONID=506D5A43B58E1E3F9AF04CF2D83DE981; yfx_f_l_v_t_10000042=f_t_1603937802565__r_t_1603937802565__v_t_1603962579116__r_c_0");
        get.setHeader("Host", "query.sse.com.cn");
        get.setHeader("Pragma", "no-cache");
        get.setHeader("Referer", "http://kcb.sse.com.cn/");
        get.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/86.0.4240.111 Safari/537.36 Edg/86.0.622.51");
    }

    /**
     * 从输入流中获取字节数组
     *
     * @param inputStream 字节流
     * @return 字节数组
     * @throws IOException IOException
     */
    private byte[] readInputStream(InputStream inputStream)
            throws IOException {
        byte[] buffer = new byte[1024];
        int len;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}