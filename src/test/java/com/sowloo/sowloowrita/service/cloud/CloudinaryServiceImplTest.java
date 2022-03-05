package com.sowloo.sowloowrita.service.cloud;

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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
@SpringBootTest
class CloudinaryServiceImplTest {

//    @Autowired
//    @Qualifier("cloudinary-service")
//    private CloudService cloudService;

    @Autowired
    private Cloudinary cloudinary;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("cloudinary object instanciated test")
    void cloudinaryInstanceTest(){
        assertThat(cloudinary).isNotNull();
    }

    @Test
    void uploadToCloudinaryTest() throws IOException {

        Path file = Paths.get("src/test/resources/WIN_20210722_10_04_16_Pro.jpg");
        assertThat(file.toFile().exists()).isTrue();
//        Map<?,?> uploadResult = cloudService.upload(Files.readAllBytes(file), ObjectUtils.emptyMap());
//        log.info("Upload result to cloud -> {}", uploadResult);
//        assertThat(uploadResult.get("url")).isNotNull();
    }

    @Test
    void uploadMultipartToCloudinaryTest() throws IOException {
//        load the file
        Path path = Paths.get("src/test/resources/WIN_20210722_10_04_16_Pro.jpg"); //We get File location here
        assertThat(path.toFile().exists());
        assertThat(path.getFileName().toString()).isEqualTo("WIN_20210722_10_04_16_Pro.jpg");
//        Convert it to multipart
        MultipartFile multipartFile = new MockMultipartFile(path.getFileName().toString(),
                path.getFileName().toString(), "img/png", Files.readAllBytes(path));
        assertThat(multipartFile).isNotNull();
        assertThat(multipartFile.isEmpty()).isFalse();
//        upload to cloud

//        Map<?, ?> uploadResult= cloudService.upload(multipartFile.getBytes(),
//                ObjectUtils.asMap( "overwrite", true));
//        assertThat(uploadResult.get("url")).isNotNull();
    }


}