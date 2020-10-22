package com.proxy.monitor;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import com.proxy.monitor.controller.ProxyController;

@WebMvcTest({ ProxyController.class })
public class ProxyControllerTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testPacEndpoint() throws Exception {
		mockMvc.perform(get("/api/pac")).andExpect(status().isOk());
	}

}
