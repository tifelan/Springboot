package com.phoenix.service.cloud;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;
@Service("aws")
public class AwsServiceImpl implements CloudService{

    @Override
    public Map<?, ?> upload(byte[] file, Map<?, ?> params) {
        return null;
    }


}
