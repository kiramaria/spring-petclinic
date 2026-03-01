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

package org.springframework.samples.petclinic.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link Person}.
 */
class PersonTests {

	@Test
	void testFirstNameGetterSetter() {
		Person person = new Person();
		person.setFirstName("John");
		assertThat(person.getFirstName()).isEqualTo("John");
	}

	@Test
	void testLastNameGetterSetter() {
		Person person = new Person();
		person.setLastName("Doe");
		assertThat(person.getLastName()).isEqualTo("Doe");
	}

	@Test
	void testFirstNameDefaultIsNull() {
		Person person = new Person();
		assertThat(person.getFirstName()).isNull();
	}

	@Test
	void testLastNameDefaultIsNull() {
		Person person = new Person();
		assertThat(person.getLastName()).isNull();
	}

	@Test
	void testInheritsBaseEntityBehavior() {
		Person person = new Person();
		assertThat(person.isNew()).isTrue();
		person.setId(10);
		assertThat(person.isNew()).isFalse();
		assertThat(person.getId()).isEqualTo(10);
	}

}
