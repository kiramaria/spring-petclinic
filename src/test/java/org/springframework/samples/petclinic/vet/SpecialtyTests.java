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

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Specialty}.
 */
class SpecialtyTests {

	@Test
	void testNameGetterSetter() {
		Specialty specialty = new Specialty();
		specialty.setName("dentistry");
		assertThat(specialty.getName()).isEqualTo("dentistry");
	}

	@Test
	void testToString() {
		Specialty specialty = new Specialty();
		specialty.setName("surgery");
		assertThat(specialty.toString()).isEqualTo("surgery");
	}

	@Test
	void testToStringWithNullName() {
		Specialty specialty = new Specialty();
		assertThat(specialty.toString()).isEqualTo("<null>");
	}

	@Test
	void testIdGetterSetter() {
		Specialty specialty = new Specialty();
		specialty.setId(5);
		assertThat(specialty.getId()).isEqualTo(5);
	}

}
