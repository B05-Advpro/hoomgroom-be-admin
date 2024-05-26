package id.ac.ui.cs.advprog.hoomgroombeadmin.controller;

import id.ac.ui.cs.advprog.hoomgroombeadmin.model.PromoCode;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.JwtService;
import id.ac.ui.cs.advprog.hoomgroombeadmin.service.PromoCodeService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.InjectMocks;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PromoCodeControllerTest {
    private MockMvc mvc;

    @InjectMocks
    private PromoCodeController controller;

    @Mock
    private PromoCodeService service;

    @Mock
    private JwtService jwtService;

    PromoCode promoCode1;

    @BeforeEach
    void setUp(){
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        promoCode1 = new PromoCode();
        promoCode1.setCodeId("6e282868-9b5b-48a2-b509-0db4aa3615e6");
        promoCode1.setMinimumPayment((double)90000);
        promoCode1.setDescription("diskon");
    }

    @Test
    void createPromoCodeTest() throws Exception {
        when(service.save(any(PromoCode.class))).thenReturn(promoCode1);
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mvc.perform(post("/admin/promo-code/create").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer jwtToken")
                        .content("{\"id\":\"6e282868-9b5b-48a2-b509-0db4aa3615e6\",\"codeName\":\"BELANJA45\",\"minimumPayment\":90000.0,\"endDate\":[2024,12,31],\"description\":\"bagus\",\"discPercentage\":45}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
                    assertEquals("6e282868-9b5b-48a2-b509-0db4aa3615e6", jsonObject.getString("codeId"));
                    assertTrue(jsonObject.has("codeId"));
                    assertTrue(jsonObject.has("discPercentage"));
                    assertTrue(jsonObject.has("endDate"));
                    assertTrue(jsonObject.has("minimumPayment"));
                    assertTrue(jsonObject.has("codeName"));
                    assertTrue(jsonObject.has("description"));
                });
        verify(service, times(1)).save(any(PromoCode.class));
        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
    }

    @Test
    void createPromoCodeNotAdminTest() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mvc.perform(post("/admin/promo-code/create").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer jwtToken")
                        .content("{\"id\":\"6e282868-9b5b-48a2-b509-0db4aa3615e6\",\"codeName\":\"BELANJA45\",\"minimumPayment\":90000.0,\"endDate\":[2024,12,31],\"description\":\"bagus\",\"discPercentage\":45}"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
    }

    @Test
    void createPromoCodeNotLoggedInTest() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mvc.perform(post("/admin/promo-code/create").contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer jwtToken")
                        .content("{\"id\":\"6e282868-9b5b-48a2-b509-0db4aa3615e6\",\"codeName\":\"BELANJA45\",\"minimumPayment\":90000.0,\"endDate\":[2024,12,31],\"description\":\"bagus\",\"discPercentage\":45}"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
    }

    @Test
    void updatePromoCodePostTest() throws Exception {
        when(service.save(any(PromoCode.class))).thenReturn(promoCode1);
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mvc.perform(post("/admin/promo-code/update/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer jwtToken")
                        .content("{\"id\":\"6e282868-9b5b-48a2-b509-0db4aa3615e6\",\"codeName\":\"diskonn45\",\"minimumPayment\":90000.0,\"endDate\":[2024,12,31],\"description\":\"bagus\",\"discPercentage\":45}"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
                    assertEquals("6e282868-9b5b-48a2-b509-0db4aa3615e6", jsonObject.getString("codeId"));
                    assertTrue(jsonObject.has("codeId"));
                    assertTrue(jsonObject.has("discPercentage"));
                    assertTrue(jsonObject.has("endDate"));
                    assertTrue(jsonObject.has("minimumPayment"));
                    assertTrue(jsonObject.has("codeName"));
                    assertTrue(jsonObject.has("description"));
                });
        verify(service, times(1)).save(any(PromoCode.class));
        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
    }

    @Test
    void updatePromoCodePostNotAdminTest() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mvc.perform(post("/admin/promo-code/update/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer jwtToken")
                        .content("{\"id\":\"6e282868-9b5b-48a2-b509-0db4aa3615e6\",\"codeName\":\"diskonn45\",\"minimumPayment\":90000.0,\"endDate\":[2024,12,31],\"description\":\"bagus\",\"discPercentage\":45}"))
                .andExpect(status().isForbidden());
        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
    }

    @Test
    void updatePromoCodePostNotLoggedInTest() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mvc.perform(post("/admin/promo-code/update/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer jwtToken")
                        .content("{\"id\":\"6e282868-9b5b-48a2-b509-0db4aa3615e6\",\"codeName\":\"diskonn45\",\"minimumPayment\":90000.0,\"endDate\":[2024,12,31],\"description\":\"bagus\",\"discPercentage\":45}"))
                .andExpect(status().isForbidden());
        verify(jwtService, times(1)).isTokenValid(anyString());
    }

    @Test
    void updatePromoCodePageTest() throws Exception {
        UUID promoCodeId = new UUID(32, 10);
        promoCode1.setCodeId(promoCodeId.toString());
        promoCode1.setCodeName("Belanja55");
        when(service.getPromoCodeById(promoCodeId.toString())).thenReturn(promoCode1);
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mvc.perform(get("/admin/promo-code/update/" + promoCodeId.toString())
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    JSONObject jsonObject = new JSONObject(result.getResponse().getContentAsString());
                    assertEquals(promoCodeId.toString(), jsonObject.getString("codeId"));
                    assertTrue(jsonObject.has("codeId"));
                    assertTrue(jsonObject.has("discPercentage"));
                    assertTrue(jsonObject.has("endDate"));
                    assertTrue(jsonObject.has("minimumPayment"));
                    assertTrue(jsonObject.has("codeName"));
                    assertTrue(jsonObject.has("description"));
                });
        verify(service, times(1)).getPromoCodeById(promoCodeId.toString());
        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
    }

    @Test
    void updatePromoCodePageIdNotFoundTest() throws Exception {
        when(service.getPromoCodeById(anyString())).thenReturn(null);
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mvc.perform(get("/admin/promo-code/update/" + "ABC123")
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isBadRequest());

        verify(service, times(1)).getPromoCodeById(anyString());
        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
    }

    @Test
    void updatePromoCodePageNotAdminTest() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mvc.perform(get("/admin/promo-code/update/" + "ABC123")
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
    }

    @Test
    void updatePromoCodePageNotLoggedInTest() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mvc.perform(get("/admin/promo-code/update/" + "ABC123")
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
    }

    @Test
    void deletePromoCodeTest() throws Exception {
        UUID promoCodeId = new UUID(32, 10);
        String expectedResult = "Deleted promo code with ID " + promoCodeId;
        when(service.delete(promoCodeId.toString())).thenReturn(promoCodeId.toString());
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mvc.perform(delete("/admin/promo-code/delete/" + promoCodeId.toString())
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isOk())
                .andDo(result -> {String responseBody = result.getResponse().getContentAsString();
                assertEquals(expectedResult, responseBody);
                });
        verify(service, times(1)).delete(promoCodeId.toString());
        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
    }

    @Test
    void deletePromoCodeNotAdminTest() throws Exception {
        UUID promoCodeId = new UUID(32, 10);
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mvc.perform(delete("/admin/promo-code/delete/" + promoCodeId.toString())
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
    }

    @Test
    void deletePromoCodeNotLoggedInTest() throws Exception {
        UUID promoCodeId = new UUID(32, 10);
        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mvc.perform(delete("/admin/promo-code/delete/" + promoCodeId.toString())
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
    }

    @Test
    void listPromoCodeTest() throws Exception {
        promoCode1.setCodeName("belanjaaa99");
        when(service.getAll()).thenReturn(Arrays.asList(promoCode1));
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("ADMIN");

        mvc.perform(get("/admin/promo-code/manage")
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(result -> {
                    JSONArray jsonArray = new JSONArray(result.getResponse().getContentAsString());
                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    assertEquals("6e282868-9b5b-48a2-b509-0db4aa3615e6", jsonObject.getString("codeId"));
                    assertTrue(jsonObject.has("codeId"));
                    assertTrue(jsonObject.has("discPercentage"));
                    assertTrue(jsonObject.has("endDate"));
                    assertTrue(jsonObject.has("minimumPayment"));
                    assertTrue(jsonObject.has("codeName"));
                    assertTrue(jsonObject.has("description"));
                });
        verify(service, times(1)).getAll();
        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
    }

    @Test
    void listPromoCodeNotAdminTest() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(true);
        when(jwtService.extractRole(anyString())).thenReturn("USER");

        mvc.perform(get("/admin/promo-code/manage")
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
        verify(jwtService, times(1)).extractRole(anyString());
    }

    @Test
    void listPromoCodeNotLoggedInTest() throws Exception {
        when(jwtService.isTokenValid(anyString())).thenReturn(false);

        mvc.perform(get("/admin/promo-code/manage")
                        .header("Authorization", "Bearer jwtToken"))
                .andExpect(status().isForbidden());

        verify(jwtService, times(1)).isTokenValid(anyString());
    }
}
