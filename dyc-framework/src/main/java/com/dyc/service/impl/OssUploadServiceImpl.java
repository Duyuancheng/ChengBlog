package com.dyc.service.impl;

import java.io.File;
import com.dyc.domain.ResponseResult;
import com.dyc.enums.AppHttpCodeEnum;
import com.dyc.exception.SystemException;
import com.dyc.service.UploadService;
//import com.dyc.utils.PathUtils;
import com.dyc.utils.PathUtils;
import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
@Data
//从配置中读取对应的属性名调用set方法赋值
@ConfigurationProperties(prefix = "oss")
public class OssUploadServiceImpl implements UploadService {

    private String accessKey;
    private String secretKey;
    private String bucket;

    private String externalLinks;

    @Override
    public ResponseResult uploadImg(MultipartFile img) throws IOException {
        //判断文件类型或者文件大小
        //获取原始文件名
        String originalFilename=img.getOriginalFilename();
        //对原始文件名进行判断
        if(!originalFilename.endsWith(".png")&&!originalFilename.endsWith(".jpg")){
            throw new SystemException(AppHttpCodeEnum.FILE_TYPE_ERROR);
        }
//        如果判断通过上传通过上传到OSS
        String path = PathUtils.generateFilePath(originalFilename);
        String url=uploadOss(img,path);
        return ResponseResult.okResult(url);

//        File file=new File("D:/BlogCode/imgs");
//        if(!file.exists()){
//            file.mkdirs();
//        }
//
//        String originalFilename = img.getOriginalFilename();
//        String path = "D:/BlogCode/imgs" + originalFilename;
//
//        //文件实现上传
//
//        img.transferTo(new File(path));
//
//        return null;
    }



    private String uploadOss(MultipartFile imgFile, String path){
        Configuration cfg = new Configuration(Region.autoRegion());
        cfg.resumableUploadAPIVersion = Configuration.ResumableUploadAPIVersion.V2;// 指定分片上传版本
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
//...生成上传凭证，然后准备上传

//        String accessKey = "pEmeVhUrZHlXaifmBPz2iKZSAGsqBksM7gSbHX6U";
//        String secretKey = "7eouzlxdFuajCInE5x4vsgNFgwYu2G52305XoWks";
//        String bucket = "dyc-blog";


//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = path;
        try {
//            byte[] uploadBytes = "hello qiniu cloud".getBytes("utf-8");
//            ByteArrayInputStream byteInputStream=new ByteArrayInputStream(uploadBytes);
            InputStream inputStream=imgFile.getInputStream();
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                System.out.println(putRet.key);
                System.out.println(putRet.hash);
                return externalLinks+key;

            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return "错误";
    }
}
