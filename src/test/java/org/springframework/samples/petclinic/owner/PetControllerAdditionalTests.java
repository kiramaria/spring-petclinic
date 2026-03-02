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
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledInNativeImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Additional test class for {@link PetController} covering edge cases for maximum
 * coverage.
 */
@WebMvcTest(value = PetController.class,
		includeFilters = @ComponentScan.Filter(value = PetTypeFormatter.class, type = FilterType.ASSIGNABLE_TYPE))
@DisabledInNativeImage
@DisabledInAotMode
class PetControllerAdditionalTests {

	private static final int TEST_OWNER_ID = 1;

	private static final int TEST_PET_ID = 2;

	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private OwnerRepository owners;

	@MockitoBean
	private PetTypeRepository types;

	@BeforeEach
	void setup() {
		PetType hamster = new PetType();
		hamster.setId(3);
		hamster.setName("hamster");
		given(this.types.findPetTypes()).willReturn(List.of(hamster));

		// Pets sorted by name: "alpha" (id=1) comes before "zeta" (id=2)
		Owner owner = new Owner();
		Pet alpha = new Pet();
		Pet zeta = new Pet();
		owner.addPet(alpha);
		owner.addPet(zeta);
		alpha.setId(1);
		zeta.setId(TEST_PET_ID);
		alpha.setName("alpha");
		zeta.setName("zeta");
		given(this.owners.findById(TEST_OWNER_ID)).willReturn(Optional.of(owner));
	}

	@Test
	void testProcessUpdateFormWithDuplicateName() throws Exception {
		// Edit "zeta" (id=2) and rename to "alpha" which already exists as id=1.
		// Since pets are @OrderBy("name"), "alpha" (id=1) is first in the list.
		// getPet("alpha", false) finds the original "alpha" (id=1) first,
		// which differs from the edited pet's id (2) => duplicate detected.
		mockMvc
			.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).param("name", "alpha")
				.param("type", "hamster")
				.param("birthDate", "2015-02-12"))
			.andExpect(model().attributeHasErrors("pet"))
			.andExpect(model().attributeHasFieldErrors("pet", "name"))
			.andExpect(model().attributeHasFieldErrorCode("pet", "name", "duplicate"))
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	void testProcessUpdateFormWithFutureBirthDate() throws Exception {
		LocalDate futureDate = LocalDate.now().plusMonths(1);
		mockMvc
			.perform(post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).param("name", "Betty")
				.param("type", "hamster")
				.param("birthDate", futureDate.toString()))
			.andExpect(model().attributeHasErrors("pet"))
			.andExpect(model().attributeHasFieldErrors("pet", "birthDate"))
			.andExpect(model().attributeHasFieldErrorCode("pet", "birthDate", "typeMismatch.birthDate"))
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdatePetForm"));
	}

	@Test
	void testFindOwnerNotFound() {
		given(this.owners.findById(99)).willReturn(Optional.empty());
		assertThatThrownBy(() -> mockMvc.perform(get("/owners/{ownerId}/pets/new", 99))).rootCause()
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void testProcessUpdateFormUpdatesExistingPet() throws Exception {
		// Test the successful update path where the existing pet is found
		mockMvc
			.perform(
					post("/owners/{ownerId}/pets/{petId}/edit", TEST_OWNER_ID, TEST_PET_ID).param("name", "UpdatedName")
						.param("type", "hamster")
						.param("birthDate", "2015-02-12"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

	@Test
	void testProcessUpdateFormAddsPetWhenNotFound() throws Exception {
		// When the pet returned by @ModelAttribute findPet is not found by id
		// in updatePetDetails, the pet is added to the owner as a new pet
		Owner owner2 = new Owner();
		Pet existingPet = new Pet();
		existingPet.setName("existing");
		owner2.addPet(existingPet);
		existingPet.setId(50);
		given(this.owners.findById(2)).willReturn(Optional.of(owner2));

		// Pet id 50 exists in owner2, so findPet returns it.
		// The successful update path should redirect.
		mockMvc
			.perform(post("/owners/{ownerId}/pets/{petId}/edit", 2, 50).param("name", "NewName")
				.param("type", "hamster")
				.param("birthDate", "2015-02-12"))
			.andExpect(status().is3xxRedirection())
			.andExpect(view().name("redirect:/owners/{ownerId}"));
	}

}
