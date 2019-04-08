package com.panacealab.panacare.controller;

import com.panacealab.panacare.entity.FileInfo;
import com.panacealab.panacare.entity.VersionInfo;
import com.panacealab.panacare.service.AppUpdateService;
import com.panacealab.panacare.utils.FileUtil;
import com.panacealab.panacare.utils.PropertyUtil;
import com.panacealab.panacare.utils.StateCode;
import com.panacealab.panacare.utils.TokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.plaf.nimbus.State;
import java.io.*;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author loveloliii
 * @description 上传最新app，更新app版本，获取最新app版本，获取最新app
 * @date 2019/3/11.
 */
@Controller
@Slf4j
@CrossOrigin
public class AppUpdateController {
    @Autowired
    private AppUpdateService appUpdateService;

    @RequestMapping(path = "getAppVersion", method = RequestMethod.POST)
    private @ResponseBody
    Map getAppVersion(@RequestBody Map map) {
        String product = (String) map.get("product");
        int platform = (int) map.get("platform");
        String channel = (String) map.get("channel");
        Map rsMap = new HashMap(2);
        String[] version = appUpdateService.getAppVersion(product, platform, channel);
        if (version == null) {
            rsMap.put("state", StateCode.DATA_QUERY_FAILED);
        } else {
            rsMap.put("state", StateCode.DATA_RETURN_SUCCESS);
            rsMap.put("data", version);
        }
        return rsMap;
    }

    /**
     * 上传新版本app文件
     */
    // fixme 未进行权限判定
    @RequestMapping(path = "uploadNewAppFile", method = RequestMethod.POST)
    private @ResponseBody
    Map uploadNewAppFile(HttpServletRequest httpServletRequest) throws IOException {
        log.info("进入uploadNewAppFile方法\n");
        Map rsMap = new HashMap(2);
        Properties p = PropertyUtil.Singleton.INSTANCE.getProperties();
        p.load(AppUpdateController.class.getResourceAsStream("/configure.properties"));
        String path = p.getProperty("panacare.update.path");
        boolean saveResult = FileUtil.saveFile(httpServletRequest, path);
        if (saveResult) {
            rsMap.put("state", StateCode.DATA_RETURN_SUCCESS);
        } else {
            rsMap.put("state", StateCode.UPLOAD_FAILED);
        }
        return rsMap;
    }

    @RequestMapping(path = "updateVersionInfo", method = RequestMethod.POST)
    private @ResponseBody
    Map updateVersionInfo(@RequestBody VersionInfo versionInfo) {
        Map rsMap = new HashMap(2);
        int rs = appUpdateService.updateVersionInfo(versionInfo);
        if (rs > 0) {
            rsMap.put("state", StateCode.DATA_RETURN_SUCCESS);
        } else {
            rsMap.put("state", StateCode.DATABASE_INSERT_ERROR);
        }
        return rsMap;
    }


    // 迅雷下载会报错 但不影响 判断是迅雷多线程的问题
    @RequestMapping("getInstallFile")
    private void getInstallFile(HttpServletResponse response) {
        log.info("进入getInstallFile方法\n");
        FileInputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            response.reset();
            String resultFileName = System.currentTimeMillis() + ".apk";
            resultFileName = URLEncoder.encode(resultFileName, "UTF-8");
            response.setCharacterEncoding("UTF-8");
            // 设定输出文件头
            response.setHeader("Content-disposition", "attachment; filename=" + resultFileName);
            // 定义输出类型
            Properties p = PropertyUtil.Singleton.INSTANCE.getProperties();
            p.load(AppUpdateController.class.getResourceAsStream("/configure.properties"));
            response.setContentType("application/vnd.android.package-archive");
            File rootPath = new File(ResourceUtils.getURL("classpath:").getPath());
            String path = p.getProperty("panacare.update.path");
            path=path.replace("/",File.separator);
            log.debug(rootPath.getAbsolutePath()+path);
            String pathname = rootPath.getAbsolutePath() + path + "app-release.apk";
            log.debug(pathname);
            File file = new File(pathname);
            response.addHeader("Content-Length", "" + file.length());
            //输入流：本地文件路径
            inputStream = new FileInputStream(file);
            //输出流
            outputStream = response.getOutputStream();
            //输出文件
            int bytes = 0;
            byte[] bufferOut = new byte[1024];
            while ((bytes = inputStream.read(bufferOut)) != -1) {
                outputStream.write(bufferOut, 0, bytes);
            }
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
