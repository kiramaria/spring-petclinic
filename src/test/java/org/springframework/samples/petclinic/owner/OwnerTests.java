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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Owner}.
 */
class OwnerTests {

	private Owner owner;

	@BeforeEach
	void setUp() {
		owner = new Owner();
		owner.setId(1);
		owner.setFirstName("George");
		owner.setLastName("Franklin");
		owner.setAddress("110 W. Liberty St.");
		owner.setCity("Madison");
		owner.setTelephone("6085551023");
	}

	@Test
	void testGettersAndSetters() {
		assertThat(owner.getAddress()).isEqualTo("110 W. Liberty St.");
		assertThat(owner.getCity()).isEqualTo("Madison");
		assertThat(owner.getTelephone()).isEqualTo("6085551023");

		owner.setAddress("New Address");
		owner.setCity("New City");
		owner.setTelephone("1234567890");

		assertThat(owner.getAddress()).isEqualTo("New Address");
		assertThat(owner.getCity()).isEqualTo("New City");
		assertThat(owner.getTelephone()).isEqualTo("1234567890");
	}

	@Test
	void testGetPetsReturnsEmptyListByDefault() {
		assertThat(owner.getPets()).isNotNull();
		assertThat(owner.getPets()).isEmpty();
	}

	@Test
	void testAddNewPet() {
		Pet pet = new Pet();
		pet.setName("Fido");
		owner.addPet(pet);

		assertThat(owner.getPets()).hasSize(1);
		assertThat(owner.getPets().get(0).getName()).isEqualTo("Fido");
	}

	@Test
	void testAddPetWithIdDoesNotAdd() {
		Pet pet = new Pet();
		pet.setId(1);
		pet.setName("Fido");
		owner.addPet(pet);

		assertThat(owner.getPets()).isEmpty();
	}

	@Test
	void testGetPetByName() {
		Pet pet = new Pet();
		pet.setName("Fido");
		owner.addPet(pet);

		Pet found = owner.getPet("Fido");
		assertThat(found).isNotNull();
		assertThat(found.getName()).isEqualTo("Fido");
	}

	@Test
	void testGetPetByNameIgnoreCase() {
		Pet pet = new Pet();
		pet.setName("Fido");
		owner.addPet(pet);

		Pet found = owner.getPet("fido");
		assertThat(found).isNotNull();
		assertThat(found.getName()).isEqualTo("Fido");
	}

	@Test
	void testGetPetByNameNotFound() {
		Pet found = owner.getPet("NonExistent");
		assertThat(found).isNull();
	}

	@Test
	void testGetPetByNameWithIgnoreNewTrue() {
		Pet newPet = new Pet();
		newPet.setName("Buddy");
		owner.addPet(newPet);

		// ignoreNew=true should skip new pets (no id set)
		Pet found = owner.getPet("Buddy", true);
		assertThat(found).isNull();
	}

	@Test
	void testGetPetByNameWithIgnoreNewFalse() {
		Pet newPet = new Pet();
		newPet.setName("Buddy");
		owner.addPet(newPet);

		// ignoreNew=false should return new pets
		Pet found = owner.getPet("Buddy", false);
		assertThat(found).isNotNull();
		assertThat(found.getName()).isEqualTo("Buddy");
	}

	@Test
	void testGetPetByNameWithIgnoreNewTrueAndSavedPet() {
		Pet savedPet = new Pet();
		savedPet.setName("Max");
		owner.addPet(savedPet);
		savedPet.setId(10);

		// ignoreNew=true should return saved pets
		Pet found = owner.getPet("Max", true);
		assertThat(found).isNotNull();
		assertThat(found.getName()).isEqualTo("Max");
	}

	@Test
	void testGetPetByIdFound() {
		Pet pet = new Pet();
		pet.setName("Rex");
		owner.addPet(pet);
		pet.setId(5);

		Pet found = owner.getPet(5);
		assertThat(found).isNotNull();
		assertThat(found.getName()).isEqualTo("Rex");
	}

	@Test
	void testGetPetByIdNotFound() {
		Pet pet = new Pet();
		pet.setName("Rex");
		owner.addPet(pet);
		pet.setId(5);

		Pet found = owner.getPet(999);
		assertThat(found).isNull();
	}

	@Test
	void testGetPetByIdSkipsNewPets() {
		Pet newPet = new Pet();
		newPet.setName("NewPet");
		owner.addPet(newPet);
		// pet has no id (isNew() returns true)

		Pet found = owner.getPet(Integer.valueOf(1));
		assertThat(found).isNull();
	}

	@Test
	void testGetPetByNameWithNullName() {
		Pet pet = new Pet();
		// name is null
		owner.addPet(pet);

		Pet found = owner.getPet("something");
		assertThat(found).isNull();
	}

	@Test
	void testToString() {
		String result = owner.toString();
		assertThat(result).contains("George");
		assertThat(result).contains("Franklin");
		assertThat(result).contains("110 W. Liberty St.");
		assertThat(result).contains("Madison");
		assertThat(result).contains("6085551023");
	}

	@Test
	void testAddVisitSuccess() {
		Pet pet = new Pet();
		pet.setName("Buddy");
		owner.addPet(pet);
		pet.setId(10);

		Visit visit = new Visit();
		visit.setDescription("checkup");
		owner.addVisit(10, visit);

		assertThat(pet.getVisits()).hasSize(1);
	}

	@Test
	void testAddVisitWithNullPetIdThrows() {
		assertThatThrownBy(() -> owner.addVisit(null, new Visit())).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void testAddVisitWithNullVisitThrows() {
		assertThatThrownBy(() -> owner.addVisit(1, null)).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void testAddVisitWithInvalidPetIdThrows() {
		assertThatThrownBy(() -> owner.addVisit(999, new Visit())).isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void testMultiplePetsRetrieval() {
		Pet pet1 = new Pet();
		pet1.setName("Alpha");
		owner.addPet(pet1);
		pet1.setId(1);

		Pet pet2 = new Pet();
		pet2.setName("Beta");
		owner.addPet(pet2);
		pet2.setId(2);

		assertThat(owner.getPets()).hasSize(2);
		assertThat(owner.getPet("Alpha")).isNotNull();
		assertThat(owner.getPet("Beta")).isNotNull();
		assertThat(owner.getPet(1)).isNotNull();
		assertThat(owner.getPet(2)).isNotNull();
	}

}
