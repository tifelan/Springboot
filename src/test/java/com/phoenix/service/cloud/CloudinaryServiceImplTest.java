package com.phoenix.service.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Slf4j
class CloudinaryServiceImplTest {

    @Autowired
    @Qualifier("cloudinary-service")
    CloudService cloudService;

    @Autowired
    Cloudinary cloudinary;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Cloudinary object instantiated test")
    void cloudinaryObjectInstanceTest(){
        assertThat(cloudinary).isNotNull();
    }

    @Test
    void uploadToCloudinaryTest() throws IOException {
        File file  = new File("src/test/resources/Screenshot (321).png");
        assertThat(file.exists()).isTrue();
        Map<?,?> uploadResult = cloudService.upload(file, ObjectUtils.emptyMap());
        log.info("Upload result to cloud --> {}", uploadResult);
        assertThat(uploadResult.get("url")).isNotNull();
    }

    @Test
    void uploadMultipartToCloudinaryTest() throws IOException {
        //load the file
        Path path = Paths.get("src/test/resources/Screenshot (321).png");
        assertThat(path.toFile().exists());
        assertThat(path.getFileName().toString()).isEqualTo("Screenshot (321).png");
        //load multipart
        MultipartFile multipartFile = new MockMultipartFile(path.getFileName().toString(),
                path.getFileName().toString(),"img/png", Files.readAllBytes(path));

        assertThat(multipartFile).isNotNull();
        assertThat(multipartFile.isEmpty()).isFalse();
        //store multipart to file
        //convert multipart to file

        //upload to cloud
        cloudService.upload(multipartFile, ObjectUtils.emptyMap());

    }




}