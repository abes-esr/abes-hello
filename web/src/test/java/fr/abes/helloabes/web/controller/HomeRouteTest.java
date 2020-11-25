package fr.abes.helloabes.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classe de test pour la route /
 */
public class HomeRouteTest extends PublicControllerTestBase {

    /**
     * Test la route / avec la méthode GET
     * @throws Exception
     */
    @Test
    public void homeGetMethod() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Hello from ABES - PUBLIC API PAGE"));
    }

    /**
     * Test la route / avec la méthode POST
     * @throws Exception
     */
    @Test
    public void homePostMethod() throws Exception {
        mockMvc.perform(post("/"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.status").value("METHOD_NOT_ALLOWED"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Method is not supported for this request"))
                .andExpect(jsonPath("$.debugMessage").exists());
    }

    /**
     * Test la route / avec la méthode PUT
     * @throws Exception
     */
    @Test
    public void homePutMethod() throws Exception {
        mockMvc.perform(put("/"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.status").value("METHOD_NOT_ALLOWED"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Method is not supported for this request"))
                .andExpect(jsonPath("$.debugMessage").exists());
    }

    /**
     * Test la route / avec la méthode DELETE
     * @throws Exception
     */
    @Test
    public void homeDeleteMethod() throws Exception {
        mockMvc.perform(delete("/"))
                .andExpect(status().isMethodNotAllowed())
                .andExpect(jsonPath("$.status").value("METHOD_NOT_ALLOWED"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Method is not supported for this request"))
                .andExpect(jsonPath("$.debugMessage").exists());
    }   

    /**
     * Test la route / avec la méthode GET et un JSON en body
     * @throws Exception
     */
    @Test
    public void homeEmptyBody() throws Exception {

        String json = "{ }";
        mockMvc.perform(get("/").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Hello from ABES - PUBLIC API PAGE"));
    }
}
