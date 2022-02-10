package com.phoenix.service.cloud;

import java.io.File;
import java.util.Map;

public interface CloudService {

    Map<?,?> upload(File file, Map<?,?> params);
}
