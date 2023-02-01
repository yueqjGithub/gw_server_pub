package com.avalon.website.cat.dto.uploadDto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Data
public class UploadDto {
    private MultipartFile file;
    @NotBlank(message = "siteId不能为空")
    private String siteId;

    private String fast;
}
