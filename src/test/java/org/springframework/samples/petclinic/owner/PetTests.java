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

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Pet}.
 */
class PetTests {

	private Pet pet;

	@BeforeEach
	void setUp() {
		pet = new Pet();
	}

	@Test
	void testBirthDateGetterSetter() {
		LocalDate date = LocalDate.of(2020, 5, 15);
		pet.setBirthDate(date);
		assertThat(pet.getBirthDate()).isEqualTo(date);
	}

	@Test
	void testBirthDateDefaultIsNull() {
		assertThat(pet.getBirthDate()).isNull();
	}

	@Test
	void testTypeGetterSetter() {
		PetType type = new PetType();
		type.setName("cat");
		pet.setType(type);

		assertThat(pet.getType()).isNotNull();
		assertThat(pet.getType().getName()).isEqualTo("cat");
	}

	@Test
	void testTypeDefaultIsNull() {
		assertThat(pet.getType()).isNull();
	}

	@Test
	void testGetVisitsReturnsEmptyCollectionByDefault() {
		assertThat(pet.getVisits()).isNotNull();
		assertThat(pet.getVisits()).isEmpty();
	}

	@Test
	void testAddVisit() {
		Visit visit = new Visit();
		visit.setDescription("annual checkup");
		pet.addVisit(visit);

		assertThat(pet.getVisits()).hasSize(1);
	}

	@Test
	void testAddMultipleVisits() {
		Visit visit1 = new Visit();
		visit1.setDescription("first visit");
		Visit visit2 = new Visit();
		visit2.setDescription("second visit");

		pet.addVisit(visit1);
		pet.addVisit(visit2);

		assertThat(pet.getVisits()).hasSize(2);
	}

	@Test
	void testSetName() {
		pet.setName("Buddy");
		assertThat(pet.getName()).isEqualTo("Buddy");
	}

	@Test
	void testIsNewWhenNoId() {
		assertThat(pet.isNew()).isTrue();
	}

	@Test
	void testIsNewWhenIdSet() {
		pet.setId(1);
		assertThat(pet.isNew()).isFalse();
	}

}
