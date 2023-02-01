package com.avalon.website.cat.controller;

import com.avalon.website.cat.component.UploadOss;
import com.avalon.website.cat.dto.uploadDto.UploadDto;
import com.avalon.website.cat.http.AvalonHttpResp;
import com.avalon.website.cat.model.GmApp;
import com.avalon.website.cat.service.GmAppService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@Slf4j
@RequestMapping("/admin/upload")
@Api(tags = "上传")
public class UploadController {
    @Value("${oss.keyId}")
    private String keyId;
    @Value("${oss.accessKeySecret}")
    private String accessKeySecret;
    @Value("${oss.region}")
    private String region;
    @Value("${oss.bucket}")
    private String bucket;
    @Value("${oss.queryPath}")
    private String ossHost;

    @Resource
    GmAppService gmAppService;

    @ApiOperation(value = "文件上传")
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AvalonHttpResp<String> upload(@Validated UploadDto dto) throws InterruptedException, IOException {
        String siteId = dto.getSiteId();
        GmApp site = gmAppService.getById(siteId);
        if (null == site) {
            return AvalonHttpResp.failed("站点未找到");
        }
        MultipartFile file = dto.getFile();
        String fileName = file.getOriginalFilename();
        String uploadName = "gameWeb/" + site.getSiteId() + "/" + new Date().getTime() + fileName;
        InputStream fileStream = new ByteArrayInputStream(file.getBytes());
        if (Objects.equals(dto.getFast(), "1")) {
            try {
                UploadOss.uploadFileStream(fileStream, uploadName, region, keyId, accessKeySecret, bucket, file.getOriginalFilename(), 0);
                return AvalonHttpResp.ok(ossHost + uploadName);
            } catch (IOException ie) {
                return AvalonHttpResp.failed(ie.toString());
            }
        }
        setTreadForUpload(fileStream, uploadName, region, keyId, accessKeySecret, bucket, file.getOriginalFilename(), 0);
        return AvalonHttpResp.ok(ossHost + uploadName);
    }

    static void setTreadForUpload (
            InputStream fileStream,
            String uploadName,
            String region,
            String keyId,
            String accessKeySecret,
            String bucket,
            String fileName,
            Integer symobl
    ) {
        new Thread(() -> {
            int cur = symobl + 1;
            try {
                UploadOss.uploadFileStream(fileStream, uploadName, region, keyId, accessKeySecret, bucket, fileName, cur);
                log.info("上传OSS成功第{}次", cur);
                Thread.interrupted();
            } catch (IOException e) {
                log.warn("上传OSS失败第{}次", cur);
                if (cur < 5) {
                    setTreadForUpload(fileStream, uploadName, region, keyId, accessKeySecret, bucket, fileName, cur);
                } else {
                    Thread.interrupted();
                }
            }
        }).start();
    }
}
