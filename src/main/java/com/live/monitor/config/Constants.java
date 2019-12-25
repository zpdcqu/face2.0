package com.live.monitor.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:server.properties")
public class Constants {
    public static final String CURRENT_USER_KEY = "yuejiaface-admin-login-user";
    public static final Integer SUPER_ADMIN = 1;
    private static String NETFACE_MODULE_FILE_PATH_VAL=null;
    private  Boolean WETHER_START_VECTOR=false;
//    @Value("${upload.base.save.path}")
//    public String fileSavePath;
//    @Value("${upload.face.data.save.path}")
//    public String faceUploadSavePath;
//    @Value("${upload.hd.data.save.path}")
//    public String hdUploadSavePath;
//    @Value("${python.vector.match.range.val}")
//    public String vectorMatchRange;
//    @Value("${image.base.path}")
//    public String IMAGE_BASE_PATH;
//    @Value("${netface_module_file_path}")
//    public String NETFACE_MODULE_FILE_PATH;
//    @Value("${upload.scan.temp.img.save.path}")
//    public String scanImgUploadSavePath;
    @Value("${upload.video.data.save.path}")
    public String scanVideoUploadSavePath;

    public Boolean getWetherStartVector() {
        return WETHER_START_VECTOR;
    }

    public void setWetherStartVector(Boolean wetherStartVector) {
       WETHER_START_VECTOR = wetherStartVector;
    }
}
