//package com.sowloo.sowloowrita.service.cloud;
//
//import com.cloudinary.Cloudinary;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.Map;
//
//
//@Service("cloudinary-service")
//public class CloudinaryServiceImpl implements CloudService {
//
//    @Autowired
//    private Cloudinary cloudinary;
//
//    @Override
//    public Map<?, ?> upload(byte[] bytes, Map<?, ?> params) throws IOException {
//
//        return cloudinary.uploader().upload(bytes, params);
//
//    }
//
////    @Override
////    public Map<?, ?> upload(byte[] bytes, Map<?, ?> params) {
////        return null;
////    }
//}
