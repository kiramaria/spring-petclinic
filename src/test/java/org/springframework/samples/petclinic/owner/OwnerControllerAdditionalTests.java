/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Additional tests for {@link OwnerController} covering edge cases for maximum coverage.
 */
@WebMvcTest(OwnerController.class)
@DisabledInNativeImage
@DisabledInAotMode
class OwnerControllerAdditionalTests {

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OwnerRepository owners;

	@Test
	void testShowOwnerNotFound() {
		given(this.owners.findById(99)).willReturn(Optional.empty());
		assertThatThrownBy(() -> mockMvc.perform(get("/owners/{ownerId}", 99))).rootCause()
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void testFindOwnerNotFoundForEdit() {
		given(this.owners.findById(99)).willReturn(Optional.empty());
		assertThatThrownBy(() -> mockMvc.perform(get("/owners/{ownerId}/edit", 99))).rootCause()
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void testProcessFindFormWithNullLastName() throws Exception {
		given(this.owners.findByLastNameStartingWith(anyString(), any(Pageable.class)))
			.willReturn(new PageImpl<>(List.of()));
		mockMvc.perform(get("/owners?page=1")).andExpect(status().isOk()).andExpect(view().name("owners/findOwners"));
	}

}
