/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
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
package com.rgucci.sample.feed.domain;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class FeedItemTest {

  private static final int FAKE_USER_ID = 8;

  private FeedItem feedItem;

  @Before
  public void setUp() {
    feedItem = new FeedItem(FAKE_USER_ID);
  }

  @Test
  public void testUserConstructorHappyCase() {
    int userId = feedItem.getId();

    assertThat(userId, is(FAKE_USER_ID));
  }
}
