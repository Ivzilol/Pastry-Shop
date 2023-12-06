package com.example.pastry.shop.controllerTest;

import com.example.pastry.shop.model.dto.CommentDto;
import com.example.pastry.shop.testRepository.TestH2RepositoryComments;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentsControllerIntegrationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private MockMvc mockMvc;

    private static TestRestTemplate restTemplate;

    private String baseUrl = "http://localhost";

    @Autowired
    private TestH2RepositoryComments testH2RepositoryComments;

    @BeforeAll
    public static void init() {
        restTemplate = new TestRestTemplate();
        restTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/comments");
    }

    @Test
    @Order(1)
    @WithUserDetails("Ivo")
    public void testCreateComment() throws Exception {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(null);
        commentDto.setShopId(1L);
        commentDto.setText("Пробен коментар");
        commentDto.setUser("Ivo");
        String jsonRequest = new ObjectMapper().writeValueAsString(commentDto);
        mockMvc.perform(MockMvcRequestBuilders.post(baseUrl)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy")
                        .value("Ivo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text")
                        .value("Пробен коментар"))
                .andReturn();
        Assertions.assertEquals(1, testH2RepositoryComments.count());
    }

    @Test
    @Order(2)
    @WithUserDetails("Ivo")
    public void testUpdateComment() throws Exception {
        Long commentId = 1L;
        CommentDto commentDto = new CommentDto();
        commentDto.setId(commentId);
        commentDto.setShopId(1L);
        commentDto.setText("Пробен коментар редакция");
        commentDto.setUser("Ivo");
        String jsonRequest = new ObjectMapper().writeValueAsString(commentDto);
        mockMvc.perform(MockMvcRequestBuilders.put(baseUrl + "/{commentId}", commentId)
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdBy")
                        .value("Ivo"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.text")
                        .value("Пробен коментар редакция"))
                .andReturn();
    }

    @Test
    @Order(3)
    public void testGetAllComments() throws Exception {
        long shopId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get(baseUrl)
                        .param("shopId", Long.toString(shopId)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].text")
                        .value("Пробен коментар редакция"))
                .andReturn();
    }

    @Test
    @Order(4)
    public void testDeleteCommentFromAdmin() throws Exception {
        Long id = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    public void testDeleteCommentFromUser() {
        Long id = 2L;
        Assertions.assertThrows(Exception.class,
                () -> {
                    mockMvc.perform(MockMvcRequestBuilders.delete(baseUrl + "/user/{id}", id))
                            .andExpect(status().isOk());
                });
    }
}
