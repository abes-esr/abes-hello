package fr.abes.helloabes.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.abes.helloabes.core.entities.AppUser;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Classe de test pour la route /register
 */
public class RegisterRouteTest extends PublicControllerTestBase {

    /**
     * Test la route /register avec la méthode GET
     * @throws Exception
     */
    @Test
    public void registerGetMethod() throws Exception {
        mockMvc.perform(get("/register")).andExpect(status().isMethodNotAllowed());
    }

    /**
     * Test la route /register avec la méthode POST
     * @throws Exception
     */
    @Test
    public void registerPostMethod() throws Exception {
        mockMvc.perform(post("/register")).andExpect(status().isBadRequest());
    }

    /**
     * Test la route /register avec la méthode PUT
     * @throws Exception
     */
    @Test
    public void registerPutMethod() throws Exception {
        mockMvc.perform(put("/register")).andExpect(status().isMethodNotAllowed());
    }

    /**
     * Test la route /register avec la méthode DELETE
     * @throws Exception
     */
    @Test
    public void registerDeleteMethod() throws Exception {
        mockMvc.perform(delete("/register")).andExpect(status().isMethodNotAllowed());
    }

    /**
     * Test l'enregistrement avec d'un utilisateur.
     * @throws Exception
     */
    @Test
    public void registerAdminUser() throws Exception {

        AppUser myUser = getAdminUser();
        AppUser myTestingUser = new AppUser(myUser.getUserName(),myUser.getPassWord());
        AppUser myDataBaseUser = getDataBaseUser(myUser);

        ObjectMapper Obj = new ObjectMapper();
        String json = Obj.writeValueAsString(myTestingUser);

        Mockito.when(userRepository.findByUserName("admin")).thenReturn(null);
        Mockito.when(userRepository.save(any(AppUser.class))).thenReturn(myDataBaseUser);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.identityNumber").value(1))
                .andExpect(jsonPath("$.userName").value(myUser.getUserName()))
                .andExpect(jsonPath("$.passWord").value(myDataBaseUser.getPassWord()));
    }

    /**
     * Test l'enregistrement d'un utilisateur avec un nom d'utilisateur qui existe déjà.
     * @throws Exception
     */
    @Test
    public void registerAlreadyExistingUser() throws Exception {

        registerAdminUser();

        AppUser myUser = getAdminUser();

        ObjectMapper Obj = new ObjectMapper();
        String json = Obj.writeValueAsString(myUser);

        Mockito.when(userRepository.findByUserName("admin")).thenReturn(myUser);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").value("Username not available"))
                .andExpect(jsonPath("$.debugMessage").isNotEmpty());

    }

    /**
     * Test l'enregistrement d'un utilisateur avec un mot de passe faible.
     * @throws Exception
     */
    @Test
    public void registerWeakPassword() throws Exception {

        AppUser myUser = getAdminUser();

        myUser.setPassWord("simple");

        ObjectMapper Obj = new ObjectMapper();
        String json = Obj.writeValueAsString(myUser);

        Mockito.when(userRepository.findByUserName("admin")).thenReturn(null);

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").value("The credentials are not valid"))
                .andExpect(jsonPath("$.debugMessage").isNotEmpty());

    }

    /**
     * Test l'enregistrement d'un utilisateur avec un JSON vide.
     * @throws Exception
     */
    @Test
    public void registerEmptyJson() throws Exception {

        String json = "{}";

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").value("The credentials are not valid"))
                .andExpect(jsonPath("$.debugMessage").isNotEmpty());

    }

    /**
     * Test l'enregistrement d'un utilisateur avec un JSON mal formé.
     * @throws Exception
     */
    @Test
    public void registerWrongJson() throws Exception {

        String json = "{ \"username\" : \"toto\"}";

        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("BAD_REQUEST"))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.message").value("The credentials are not valid"))
                .andExpect(jsonPath("$.debugMessage").isNotEmpty());

    }
}