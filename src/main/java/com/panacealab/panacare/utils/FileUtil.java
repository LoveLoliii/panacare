package com.panacealab.panacare.utils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

public class FileUtil {
private static Logger logger = LoggerFactory.getLogger(FileUtil.class.getName());



    /***
     * @param file 文件
     * @return suffix 后缀名
     * */
    public static String getFileSuffix(File file){
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return  suffix;
    }
    /***
     * @describe
     * @param httpServletRequest
     * @return 存入文件的id
     *
     * */
    public static List<Map> saveFile(HttpServletRequest httpServletRequest){
        logger.debug("start parse httpServletRequest:");
        boolean flag;
        String fileName = "";
        List<Map> fileNameList = new ArrayList<>();
        String fileId = "";
        // 检查输入请求是否为multipart表单数据。
        boolean isMultipart = ServletFileUpload.isMultipartContent(httpServletRequest);
        Properties properties = new Properties();
        if(isMultipart){
            logger.debug("请求是multipart表单数据。");
            /** 为该请求创建一个DiskFileItemFactory对象，通过它来解析请求。执行解析后，所有的表单项目都保存在一个List中。 **/
            try{
                properties.load(FileUtil.class.getResourceAsStream("/configure.properties"));
                DiskFileItemFactory factory = new DiskFileItemFactory();
                File pathFile = new File(httpServletRequest.getServletContext().getRealPath("img/temp"));
                if(!pathFile.exists()){
                    pathFile.mkdirs();
                }
                factory.setRepository(new File(httpServletRequest.getServletContext().getRealPath("img/temp")));//设置缓冲区目录tempPathFile
                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setHeaderEncoding("UTF-8");//解决文件乱码问题
                //upload.setSizeMax();//设置文件最大尺寸
                List<FileItem> items = upload.parseRequest(httpServletRequest);
                //检查是否符合上传类型 前端判定
                //if(!checkFileType(items)) return false;
                Iterator<FileItem> itr = items.iterator();//所有的表单项
                //保存文件
                while(itr.hasNext()){
                    FileItem item = itr.next();
                    if(!item.isFormField()){//表示是文件
                        String name = item.getName();//获得文件名（包括路径？
                        if(name!= null){
                            File fullFile = new File(item.getName());
                            String UUID = StringUtil.getUUID();
                            //File renameFile = new File(fullFile.getParent()+File.separator+UUID);
                           // fullFile.renameTo(renameFile);
                            String suffix = getFileSuffix(fullFile);
                            File savedFile = new File(httpServletRequest.getSession().getServletContext().getRealPath("img"),
                                    UUID+"."+suffix/*fullFile.getName().trim()*/);

                            item.write(savedFile);
                            logger.debug("文件写入成功，文件名为:{}",fullFile.getName());
                            Map map = new HashMap();
                            map.put("fileName",UUID+"."+suffix);
                            map.put("UUID",UUID);
                            //map.put("path",savedFile.getAbsolutePath());


                            fileNameList.add(map);
                        }
                    }else{
                        //读取表单数据
                       Map map = new HashMap();
                       map.put(item.getFieldName(),item.getString());
                        fileNameList.add(map);

                    }
                }
                flag = true;
            }catch (Exception e){
                flag = false;
                e.printStackTrace();
                logger.error("文件上传异常：{}",e.getCause().getMessage());
            }finally {

            }
        }else {
            flag = false;
            System.out.println("the enctype must be multipart/form-data");
            logger.debug("非表单数据。");
        }


            //返回保存文件名
            return fileNameList;

    }

}
