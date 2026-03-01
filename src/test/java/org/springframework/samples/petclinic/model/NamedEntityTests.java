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
 * Unit tests for {@link NamedEntity}.
 */
class NamedEntityTests {

	/**
	 * Concrete subclass for testing abstract-like NamedEntity.
	 */
	private static class TestNamedEntity extends NamedEntity {

	}

	@Test
	void testNameDefaultIsNull() {
		NamedEntity entity = new TestNamedEntity();
		assertThat(entity.getName()).isNull();
	}

	@Test
	void testGetSetName() {
		NamedEntity entity = new TestNamedEntity();
		entity.setName("TestName");
		assertThat(entity.getName()).isEqualTo("TestName");
	}

	@Test
	void testToStringWithName() {
		NamedEntity entity = new TestNamedEntity();
		entity.setName("Fido");
		assertThat(entity.toString()).isEqualTo("Fido");
	}

	@Test
	void testToStringWithNullName() {
		NamedEntity entity = new TestNamedEntity();
		assertThat(entity.toString()).isEqualTo("<null>");
	}

	@Test
	void testInheritsBaseEntityBehavior() {
		NamedEntity entity = new TestNamedEntity();
		assertThat(entity.isNew()).isTrue();
		entity.setId(5);
		assertThat(entity.isNew()).isFalse();
		assertThat(entity.getId()).isEqualTo(5);
	}

}
