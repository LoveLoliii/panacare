package com.panacealab.panacare.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

public class FileUtil {
    private static Logger logger = LoggerFactory.getLogger(FileUtil.class.getName());


    /***
     * @param file 文件
     * @return suffix 后缀名
     * */
    public static String getFileSuffix(File file) {
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return suffix;
    }

    /***
     * @describe
     * @param httpServletRequest
     * @return 存入文件的id
     *
     * */
    public static Map<String,Map> saveFile(HttpServletRequest httpServletRequest) {
        logger.debug("start parse httpServletRequest:");
        boolean flag;
        String fileName = "";
        Map<String, Map> mapData = new HashMap<>(16);
        String fileId = "";
        // 检查输入请求是否为multipart表单数据。
        boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);
        Properties properties = new Properties();
        if (isMultipart) {
            logger.debug("请求是multipart表单数据。");
            /** 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。 **/
            try {
                File path =new File(ResourceUtils.getURL("classpath:").getPath()) ;
                if(!path.exists()) {path = new File("");}
                logger.debug("path:"+path.getAbsolutePath());
                //如果上传目录为/static/images/upload/，则可以如下获取：
                File uploadf = new File(path.getAbsolutePath(),"static/images/upload/");
                File uploadT = new File(path.getAbsolutePath(),"static/images/temp/");
                if(!uploadf.exists()) {uploadf.mkdirs();}
                logger.debug("upload url:"+uploadf.getAbsolutePath());


                properties.load(FileUtil.class.getResourceAsStream("/configure.properties"));
                DiskFileItemFactory factory = new DiskFileItemFactory();
                //File pathFile = new File(httpServletRequest.getServletContext().getRealPath("img/temp"));
                if (!uploadT.exists()) {
                    uploadT.mkdirs();
                }
                //设置缓冲区目录tempPathFile
                factory.setRepository(new File(uploadT.getAbsolutePath()));
                ServletFileUpload upload = new ServletFileUpload(factory);
                //解决文件乱码问题
                upload.setHeaderEncoding("UTF-8");
                //upload.setSizeMax();//设置文件最大尺寸
                List<FileItem> items = upload.parseRequest(httpServletRequest);
                //检查是否符合上传类型 前端判定
                //if(!checkFileType(items)) return false;
                //所有的表单项
                Iterator<FileItem> itr = items.iterator();
                //保存文件
                while (itr.hasNext()) {
                    FileItem item = itr.next();
                    //表示是文件
                    if (!item.isFormField()) {
                        //获得文件名（包括路径？
                        String name = item.getName();
                        if (name != null) {
                            File fullFile = new File(item.getName());
                            String UUID = StringUtil.getUUID();
                            String suffix = getFileSuffix(fullFile);
                            //fixme 应该使用具体的文件路径 不然重启服务器，其目录下会文件丢失


                            File savedFile = new File(uploadf.getAbsolutePath(),
                                    UUID + "." + suffix);
                            item.write(savedFile);
                            logger.debug("文件写入成功，文件名为:{},{}", fullFile.getName(),uploadf.getAbsolutePath());
                            Map map = new HashMap(4);
                            map.put("fileName", UUID + "." + suffix);
                            mapData.put("file", map);
                        }
                    } else {
                        //读取表单数据
                        Map mapParams = new HashMap(4);
                        mapParams.put(item.getFieldName(), item.getString());
                        mapData.put("map", mapParams);

                    }
                }
                flag = true;
            } catch (Exception e) {
                flag = false;
                e.printStackTrace();
                logger.error("文件上传异常：{}", e);
            } finally {

            }
        } else {
            flag = false;
            System.out.println("the enctype must be multipart/form-data");
            logger.debug("非表单数据。");
        }
        //返回保存文件名
        return mapData;

    }

}
