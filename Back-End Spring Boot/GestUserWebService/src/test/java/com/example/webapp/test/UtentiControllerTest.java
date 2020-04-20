package com.example.webapp.test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.example.webapp.GestUserWebServiceApplication;
import com.example.webapp.repository.UtentiRepository;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = GestUserWebServiceApplication.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class UtentiControllerTest {
    
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UtentiRepository utentiRepository;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

	}
	
	String JsonData =  
			"{\n" + 
			"    \"username\": \"Nicola\",\n" + 
			"    \"password\": \"123Stella\",\n" + 
			"    \"attivo\": \"Si\",\n" + 
			"    \"ruoli\": [\n" + 
			"            \"USER\"\n" + 
			"        ]\n" + 
			"}";
	
	@Test
	public void A_testInsUtente1() throws Exception	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/utenti/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());
	}

	@Test
	public void B_testListUserByUsername() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/api/utenti/cerca/username/Nicola")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				  
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.username").exists())
				.andExpect(jsonPath("$.username").value("Nicola"))
				.andExpect(jsonPath("$.password").exists())
				.andExpect(jsonPath("$.attivo").exists())
				.andExpect(jsonPath("$.attivo").value("Si"))
				  
				.andExpect(jsonPath("$.ruoli[0]").exists())
				.andExpect(jsonPath("$.ruoli[0]").value("USER")) 
				.andDo(print());
		
				assertThat(passwordEncoder.matches("123Stella", 
						utentiRepository.findByUsername("Nicola").getPassword()))
				.isEqualTo(true);
	}
	
	String JsonData2 = 
			"{\n" + 
			"    \"username\": \"Admin\",\n" + 
			"    \"password\": \"VerySecretPwd\",\n" + 
			"    \"attivo\": \"Si\",\n" + 
			"    \"ruoli\": [\n" + 
			"            \"USER\",\n" + 
			"            \"ADMIN\"\n" + 
			"        ]\n" + 
			"}";
	
	@Test
	public void C_testInsUtente2() throws Exception	{
		mockMvc.perform(MockMvcRequestBuilders.post("/api/utenti/inserisci")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData2)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated())
				.andDo(print());
	}
	
	String JsonDataUsers = 
			"[\n" + 
			"	{\n" + 
			"	    \"username\": \"Nicola\",\n" + 
			"	    \"password\": \"123Stella\",\n" + 
			"	    \"attivo\": \"Si\",\n" + 
			"	    \"ruoli\": [\n" + 
			"		    \"USER\"\n" + 
			"		]\n" + 
			"	},\n" + 
			"	{\n" + 
			"	    \"username\": \"Admin\",\n" + 
			"	    \"password\": \"VerySecretPwd\",\n" + 
			"	    \"attivo\": \"Si\",\n" + 
			"	    \"ruoli\": [\n" + 
			"		    \"USER\",\n" + 
			"		    \"ADMIN\"\n" + 
			"		]\n" + 
			"	}\n" + 
			"]";
	
	@Test
	public void D_testGetAllUser() throws Exception	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/utenti/cerca/tutti")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(2)))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				 //UTENTE 1
				.andExpect(jsonPath("$[0].id").exists())
				.andExpect(jsonPath("$[0].username").exists())
				.andExpect(jsonPath("$[0].username").value("Nicola"))
				.andExpect(jsonPath("$[0].password").exists())
				.andExpect(jsonPath("$[0].attivo").exists())
				.andExpect(jsonPath("$[0].attivo").value("Si"))
				.andExpect(jsonPath("$[0].ruoli[0]").exists())
				.andExpect(jsonPath("$[0].ruoli[0]").value("USER")) 
				 //UTENTE 2
				.andExpect(jsonPath("$[1].id").exists())
				.andExpect(jsonPath("$[1].username").exists())
				.andExpect(jsonPath("$[1].username").value("Admin"))
				.andExpect(jsonPath("$[1].password").exists())
				.andExpect(jsonPath("$[1].attivo").exists())
				.andExpect(jsonPath("$[1].attivo").value("Si"))
				.andExpect(jsonPath("$[1].ruoli[0]").exists())
				.andExpect(jsonPath("$[1].ruoli[0]").value("USER")) 
				.andExpect(jsonPath("$[1].ruoli[1]").exists())
				.andExpect(jsonPath("$[1].ruoli[1]").value("ADMIN")) 
				.andReturn();
		
				assertThat(passwordEncoder.matches("VerySecretPwd", 
						utentiRepository.findByUsername("Admin").getPassword()))
				.isEqualTo(true);
	}
	/*
	@Test
	public void E_testDelUtente1() throws Exception	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/utenti/elimina/Nicola")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value("200 OK"))
				.andExpect(jsonPath("$.message").value("Eliminazione Utente Nicola Eseguita Con Successo"))
				.andDo(print());
	}
	
	@Test
	public void F_testDelUtente2() throws Exception	{
		mockMvc.perform(MockMvcRequestBuilders.delete("/api/utenti/elimina/Admin")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.code").value("200 OK"))
				.andExpect(jsonPath("$.message").value("Eliminazione Utente Admin Eseguita Con Successo"))
				.andDo(print());
	}
	*/
}
