/*
 * Copyright 2002-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.amqp.rabbit.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.amqp.rabbit.listener.AbstractRabbitListenerEndpoint;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpoint;

/**
 *
 * @author Stephane Nicoll
 */
public class RabbitListenerContainerTestFactory implements RabbitListenerContainerFactory<MessageListenerTestContainer> {

	private static final AtomicInteger counter = new AtomicInteger();

	private final List<MessageListenerTestContainer> listenerContainers =
			new ArrayList<MessageListenerTestContainer>();

	public List<MessageListenerTestContainer> getListenerContainers() {
		return listenerContainers;
	}

	@Override
	public MessageListenerTestContainer createListenerContainer(RabbitListenerEndpoint endpoint) {
		MessageListenerTestContainer container = new MessageListenerTestContainer(endpoint);
		this.listenerContainers.add(container);

		// resolve the id
		if (endpoint.getId() == null && endpoint instanceof AbstractRabbitListenerEndpoint) {
			((AbstractRabbitListenerEndpoint) endpoint).setId("endpoint#" + counter.getAndIncrement());
		}
		return container;
	}

}
