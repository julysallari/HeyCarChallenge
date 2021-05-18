package com.heycarlight.utils;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.nio.file.Files;

public class MultimediaFileUtils {

    public static MockMultipartFile getMockedFile(String name, String fromFile) throws IOException {
        return new MockMultipartFile(
                name,
                name,
                MediaType.TEXT_PLAIN_VALUE,
                Files.readAllBytes(new ClassPathResource(fromFile).getFile().toPath()));
    }
}
