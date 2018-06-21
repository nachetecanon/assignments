package org.icanon.assignment3;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.icanon.assignement3.Application3;
import org.icanon.assignements.model.Data;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application3.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WebControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(MockMvcRestDocumentation.documentationConfiguration(restDocumentation))
                .build();
    }

    @Test
    public void assert_regular_call_returns_expected_string() throws Exception {
        final String expectedData = "2:ttttttttt/3:eeeeeeee/3:aaaaaaa/5:ooooooo/5:iiiii/2:lllll/3:nnnnn/5:rrrrr/2:sssss/2,4:mmmm/3:ggg/2,4:hhh/5:cc/3:uu/5:ww/2,5:ff/3,5:vv/4,5:xx";
        Data data = new Data();
        data.setStrings(Arrays.asList("\n",
                "Calling stream() method on the list to get a stream of values from the list\n",
                "Calling mapToInt(value -> value) on the stream to get an Integer Stream\n",
                "Calling max() method on the stream to get the max value\n",
                "Calling orElseThrow() to throw an exception if no value is received from max()"));
        MvcResult result = mockMvc.perform(post("/api/mix")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isOk())
                .andDo(document("okcall"))
                .andReturn();

        assertThat(result.getResponse()).
                isNotNull();
        assertThat(result.getResponse().getContentAsString())
                .isEqualTo(expectedData);

    }

    @Test
    public void assert_that_validations_works_as_expected_when_no_content_sent() throws Exception {
        mockMvc.perform(post("/api/mix")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andDo(document("badcall1"));
    }

    @Test
    public void assert_that_validations_works_as_expected_when_null_string_sent() throws Exception {
        Data data = new Data().setStrings(Arrays.asList("string1", null, "string three"));
        assertThat(mockMvc.perform(post("/api/mix")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(data)))
                .andExpect(status().isBadRequest())
                .andDo(document("badcall2"))
                .andReturn().getResponse().getContentAsString())
                .isEqualTo("argument list must not be null!");
    }
}
