package com.bijenkorf.demo.controller;

import com.bijenkorf.demo.model.images.Image;
import com.bijenkorf.demo.model.images.predefinedTypes.PredefinedImage;
import com.bijenkorf.demo.model.images.predefinedTypes.Thumbnail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import javax.inject.Inject;
import java.io.FileInputStream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("testing")
public class ImageControllerTest {

    @Inject
    private MockMvc mockMvc;

    @Test
    public void getImage__returns_status_ok_and_the_optimized_image() throws Exception {
        MvcResult result = this.mockMvc
                                .perform(get("/image/show/thumbnail/pathtofile.jpg"))
                                .andDo(print())
                                .andExpect(status()
                                        .isOk())
                                .andReturn();

        Image optimizedImage = new Image(new FileInputStream("/pathtofile.jpg"));

        PredefinedImage thumbnail = new Thumbnail();
        optimizedImage = thumbnail.resize(optimizedImage);
        optimizedImage = thumbnail.optimize(optimizedImage);

        //TODO assert
    }
}
