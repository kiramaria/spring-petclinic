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

package org.springframework.samples.petclinic.system;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import javax.cache.CacheManager;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.cache.autoconfigure.JCacheManagerCustomizer;

/**
 * Unit tests for {@link CacheConfiguration}.
 */
class CacheConfigurationTests {

	private final CacheConfiguration cacheConfiguration = new CacheConfiguration();

	@Test
	void testPetclinicCacheConfigurationCustomizerCreatesVetsCache() {
		JCacheManagerCustomizer customizer = cacheConfiguration.petclinicCacheConfigurationCustomizer();
		assertThat(customizer).isNotNull();

		CacheManager cacheManager = mock(CacheManager.class);
		customizer.customize(cacheManager);

		ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
		verify(cacheManager).createCache(nameCaptor.capture(),
				org.mockito.ArgumentMatchers.any(javax.cache.configuration.Configuration.class));
		assertThat(nameCaptor.getValue()).isEqualTo("vets");
	}

}
