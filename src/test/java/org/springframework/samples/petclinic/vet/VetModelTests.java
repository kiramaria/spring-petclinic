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

package org.springframework.samples.petclinic.vet;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Vet} domain model logic.
 */
class VetModelTests {

	@Test
	void testGetSpecialtiesReturnsEmptyListByDefault() {
		Vet vet = new Vet();
		List<Specialty> specialties = vet.getSpecialties();

		assertThat(specialties).isNotNull();
		assertThat(specialties).isEmpty();
	}

	@Test
	void testAddSpecialty() {
		Vet vet = new Vet();
		Specialty specialty = new Specialty();
		specialty.setName("radiology");

		vet.addSpecialty(specialty);

		assertThat(vet.getSpecialties()).hasSize(1);
		assertThat(vet.getSpecialties().get(0).getName()).isEqualTo("radiology");
	}

	@Test
	void testAddMultipleSpecialties() {
		Vet vet = new Vet();

		Specialty radiology = new Specialty();
		radiology.setName("radiology");
		Specialty surgery = new Specialty();
		surgery.setName("surgery");

		vet.addSpecialty(radiology);
		vet.addSpecialty(surgery);

		assertThat(vet.getSpecialties()).hasSize(2);
	}

	@Test
	void testGetSpecialtiesReturnsSortedByName() {
		Vet vet = new Vet();

		Specialty surgery = new Specialty();
		surgery.setName("surgery");
		Specialty dentistry = new Specialty();
		dentistry.setName("dentistry");
		Specialty radiology = new Specialty();
		radiology.setName("radiology");

		vet.addSpecialty(surgery);
		vet.addSpecialty(dentistry);
		vet.addSpecialty(radiology);

		List<Specialty> sorted = vet.getSpecialties();
		assertThat(sorted).extracting(Specialty::getName).containsExactly("dentistry", "radiology", "surgery");
	}

	@Test
	void testGetNrOfSpecialtiesWhenEmpty() {
		Vet vet = new Vet();
		assertThat(vet.getNrOfSpecialties()).isZero();
	}

	@Test
	void testGetNrOfSpecialtiesWithSpecialties() {
		Vet vet = new Vet();
		Specialty s1 = new Specialty();
		s1.setName("dentistry");
		Specialty s2 = new Specialty();
		s2.setName("surgery");

		vet.addSpecialty(s1);
		vet.addSpecialty(s2);

		assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
	}

	@Test
	void testVetPersonProperties() {
		Vet vet = new Vet();
		vet.setFirstName("James");
		vet.setLastName("Carter");
		vet.setId(1);

		assertThat(vet.getFirstName()).isEqualTo("James");
		assertThat(vet.getLastName()).isEqualTo("Carter");
		assertThat(vet.getId()).isEqualTo(1);
	}

}
