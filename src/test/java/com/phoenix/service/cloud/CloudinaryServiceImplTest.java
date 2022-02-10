package com.phoenix.service.cloud;

import com.cloudinary.utils.ObjectUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class CloudinaryServiceImplTest {

    @Autowired
    @Qualifier("cloudinary")
    CloudService cloudService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void uploadToCloudinaryTest(){
        File file  = new File("Screenshot (321).png");
        cloudService.upload(file, ObjectUtils.emptyMap());
    }




}