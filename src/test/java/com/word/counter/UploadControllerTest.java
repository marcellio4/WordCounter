package com.word.counter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.word.counter.controller.UploadController;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ContextConfiguration(classes = {UploadController.class})
@WebMvcTest
public class UploadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testDisplayResult() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk());
    }

    @Test
    void testHandleFileUpload() throws Exception {
        String fileName = "test-file.txt";
        MockMultipartFile sampleFile = new MockMultipartFile(
            "file", 
            fileName, 
            "text/plain",
            "This is the file content".getBytes());

        MockMultipartHttpServletRequestBuilder multipartRequest =
                MockMvcRequestBuilders.multipart("/");

        mockMvc.perform(multipartRequest.file(sampleFile)).andExpect(status().is(302));
    }
}
