package com.keithmartin.people;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PeopleApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@Order(1)
	public void initialLoad() throws Exception {
		this.mockMvc.perform(get("/api/count")).andDo(print()).andExpect(status().isOk())
		.andExpect(content().string(containsString("3")));
	}
	@Test
	@Order(2)
	public void getAllPeople() throws Exception {
		this.mockMvc.perform(get("/api/people")).andDo(print()).andExpect(status().isOk());
	}
	@Test
	@Order(3)
	public void getPerson() throws Exception {
		this.mockMvc.perform(get("/api/person/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Keith"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.addresses.length()").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.addresses.[0].id").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.addresses.[0].street").value("Swords"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.addresses.[0].city").value("Dublin"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.addresses.[0].state").value("Leinster"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.addresses.[0].postalCode").value("555"));

	}
	@Test
	@Order(4)
	public void addPerson() throws Exception {
		this.mockMvc.perform(post("/api/person").contentType(MediaType.APPLICATION_JSON)
				.content("{\n" +
						"    \"name\": \"Joe\",\n" +
						"    \"surname\": \"Blogs\"\n" +
						"}"))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("4"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Joe"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Blogs"));

	}

	@Test
	@Order(5)
	public void editPerson() throws Exception {
		this.mockMvc.perform(put("/api/person").contentType(MediaType.APPLICATION_JSON)
				.content("{\n" +
						"    \"id\":\"4\",\n" +
						"    \"name\": \"Steve\",\n" +
						"    \"surname\": \"Jobs\"\n" +
						"}"))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("4"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Steve"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Jobs"));

		this.mockMvc.perform(get("/api/person/4")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Steve"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.surname").value("Jobs"));

	}

	@Test
	@Order(6)
	public void deletePerson() throws Exception {
		this.mockMvc.perform(delete("/api/person/4")).andExpect(status().isOk());
		this.mockMvc.perform(get("/api/person/4")).andDo(print()).andExpect(status().isNotFound());
	}


	@Test
	@Order(7)
	public void getAddress() throws Exception {
		this.mockMvc.perform(get("/api/person/address/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.street").value("Swords"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.city").value("Dublin"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.state").value("Leinster"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.postalCode").value("555"));

	}
	@Test
	@Order(8)
	public void editAddress() throws Exception {

		this.mockMvc.perform(put("/api/person/1/address").contentType(MediaType.APPLICATION_JSON)
				.content("{\n" +
						"    \"id\": \"1\",\n" +
						"    \"street\": \"123 Street\",\n" +
						"    \"city\": \"London\",\n" +
						"    \"state\": \"UK\",\n" +
						"    \"postalCode\": \"123\"\n" +
						"}"))
				.andDo(print()).andExpect(status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.street").value("123 Street"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.city").value("London"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.state").value("UK"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.postalCode").value("123"));


		this.mockMvc.perform(get("/api/person/address/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.street").value("123 Street"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.city").value("London"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.state").value("UK"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.postalCode").value("123"));

	}

	@Test
	@Order(9)
	public void addAddress() throws Exception {

		this.mockMvc.perform(post("/api/person/1/address").contentType(MediaType.APPLICATION_JSON)
				.content("{\n" +
						"    \"street\": \"123 Street\",\n" +
						"    \"state\": \"UK\",\n" +
						"    \"postalCode\": \"123\"\n" +
						"}"))
				.andDo(print()).andExpect(status().isCreated());


		this.mockMvc.perform(get("/api/person/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Keith"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.addresses.length()").value("2"));


	}

	@Test
	@Order(10)
	public void deleteAddress() throws Exception {
		this.mockMvc.perform(delete("/api/address/1")).andExpect(status().isOk());
		this.mockMvc.perform(get("/api/person/address/1")).andDo(print()).andExpect(status().isNotFound());
	}


}

