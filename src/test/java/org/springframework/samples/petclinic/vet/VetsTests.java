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
 * Unit tests for {@link Vets}.
 */
class VetsTests {

	@Test
	void testGetVetListInitializesWhenNull() {
		Vets vets = new Vets();
		List<Vet> vetList = vets.getVetList();

		assertThat(vetList).isNotNull();
		assertThat(vetList).isEmpty();
	}

	@Test
	void testGetVetListReturnsSameInstance() {
		Vets vets = new Vets();
		List<Vet> first = vets.getVetList();
		List<Vet> second = vets.getVetList();

		assertThat(first).isSameAs(second);
	}

	@Test
	void testAddVetToList() {
		Vets vets = new Vets();
		Vet vet = new Vet();
		vet.setFirstName("James");
		vet.setLastName("Carter");
		vets.getVetList().add(vet);

		assertThat(vets.getVetList()).hasSize(1);
		assertThat(vets.getVetList().get(0).getFirstName()).isEqualTo("James");
	}

	@Test
	void testAddMultipleVets() {
		Vets vets = new Vets();

		Vet vet1 = new Vet();
		vet1.setFirstName("James");
		vet1.setLastName("Carter");

		Vet vet2 = new Vet();
		vet2.setFirstName("Helen");
		vet2.setLastName("Leary");

		vets.getVetList().add(vet1);
		vets.getVetList().add(vet2);

		assertThat(vets.getVetList()).hasSize(2);
	}

}
