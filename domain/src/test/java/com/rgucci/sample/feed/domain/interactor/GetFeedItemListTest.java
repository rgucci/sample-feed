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
package com.rgucci.sample.feed.domain.interactor;

import com.rgucci.sample.feed.domain.executor.PostExecutionThread;
import com.rgucci.sample.feed.domain.executor.ThreadExecutor;
import com.rgucci.sample.feed.domain.repository.FeedItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;

public class GetFeedItemListTest {

  private GetFeedItems getFeedItems;

  @Mock private ThreadExecutor mockThreadExecutor;
  @Mock private PostExecutionThread mockPostExecutionThread;
  @Mock private FeedItemRepository mockFeedItemRepository;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    getFeedItems = new GetFeedItems(mockFeedItemRepository, mockThreadExecutor,
        mockPostExecutionThread);
  }

  @Test
  public void testGetUserListUseCaseObservableHappyCase() {
    getFeedItems.buildUseCaseObservable();

    verify(mockFeedItemRepository).feedItems();
    verifyNoMoreInteractions(mockFeedItemRepository);
    verifyZeroInteractions(mockThreadExecutor);
    verifyZeroInteractions(mockPostExecutionThread);
  }
}
