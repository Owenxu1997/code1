package com.tjj.bysjerp.sys.common;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Owen on 2020/4/17 17:42
 * 文件上传下载工具类
 */
public class AppFileUtils {
    public static  String UPLOAD_PATH = "F:/upload/"; //默认值

    static {
        //读取配置文件的值的存储地址
        InputStream stream = AppFileUtils.class.getClassLoader().getResourceAsStream("file.properties");
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String property = properties.getProperty("filepath");
        if (null!=property) {
            UPLOAD_PATH = property;
        }
    }

    /**
     * 根据文件旧名字得到新名字
     * @param oldName
     * @return
     */
    public static String createNewFileName(String oldName) {
//        String s = StringUtils.substringAfterLast(oldName, ".");  //这个代码生成的图片没有"."也就是"."被作为切割划分
        String stuff = oldName.substring(oldName.lastIndexOf("."),oldName.length());
        return IdUtil.simpleUUID().toUpperCase()+stuff;
    }

    /**
     * 文件下载
     * @param path
     * @return
     */
    public static ResponseEntity<Object> createResponseEntity(String path) {
        //1.构造文件对象
        File file = new File(UPLOAD_PATH,path);
        if (file.exists()) {
            //将下载的文件，封装byte[]
            byte[] bytes = null;
            try {
                bytes = FileUtil.readBytes(file);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //创建封装响应头信息的对象
            HttpHeaders header = new HttpHeaders();
            //封装响应内容类型(APPLICATION_OCTET_STREAM 响应的内容不限定)
            header.setContentType(MediaType.APPLICATION_OCTET_STREAM);

            //设置下载的文件的名称
//            header.setContentDispositionFormData("attachment","1a.jpg");

            //创建ResponseEntity对象
            ResponseEntity<Object> entity = new ResponseEntity<>(bytes, header, HttpStatus.CREATED);
            return entity;

        }
        return null;
    }
/**
 * @author owen
 * @date 2020/4/18 0:13
 * 根据路径改名字
 */
    public static String renameFile(String goodsimg) {

        File file = new File(UPLOAD_PATH,goodsimg);
        String replace = goodsimg.replace("_temp", "");
        if (file.exists()) {
            file.renameTo(new File(UPLOAD_PATH, replace));
        }
        return replace;
    }

    /**
     * @author owen
     * @date 2020/4/18 0:56
     * 根据老路径删除图片
     */
    public static void removeFileByPath(String oldPath) {
        if (!oldPath.equals(Constant.IMAGES_DEFAULT_PNG)){
            File file = new File(UPLOAD_PATH,oldPath);
            if (file.exists()) {
                file.delete();
            }
        }
    }
}
