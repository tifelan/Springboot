package com.phoenix.service.cloud;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service("cloudinary-service")
public class CloudinaryServiceImpl implements CloudService {

    @Autowired
    Cloudinary cloudinary;

    @Override
    public Map<?, ?> upload(File file, Map<?, ?> params) throws IOException {
        return cloudinary.uploader().upload(file, params);
    }

    @Override
    public Map<?, ?> upload(MultipartFile multipart, Map<?, ?> params) throws IOException {
        return cloudinary.uploader().upload(multipart, params);
    }
}
