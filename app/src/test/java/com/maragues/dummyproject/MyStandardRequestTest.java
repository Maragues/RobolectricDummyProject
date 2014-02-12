package com.maragues.dummyproject;

import com.google.mockwebserver.MockResponse;
import com.google.mockwebserver.MockWebServer;
import com.maragues.dummyproject.test.RobolectricGradleTestRunner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;

import static org.junit.Assert.assertEquals;

/**
 * Created by maragues on 12/02/14.
 */
@RunWith(RobolectricGradleTestRunner.class)
public class MyStandardRequestTest {
  MyStandardRequest request;

  MockWebServer mMockWebServer;

  private static final String WORD = "LEON";
  private static final String REVERSE_WORD = "NOEL";
  private static final String MOCKED_WORD = "MOCKED";

  @Test
  public void assertRealRequestReturnsExpectedResult() throws Exception {
    String result = request.loadDataFromNetwork();

    assertEquals(REVERSE_WORD, result);
  }

  @Test
  public void mockedRequestUsingMockServer() throws Exception {
    mMockWebServer.enqueue(new MockResponse().setBody(MOCKED_WORD));
    mMockWebServer.play();

    Robolectric.getFakeHttpLayer().interceptHttpRequests(false);
    Robolectric.getFakeHttpLayer().interceptResponseContent(false);

    String result = request.loadDataFromNetwork();

    assertEquals(MOCKED_WORD, result);
  }

  @Test
  public void mockedRequestUsingRobolectric() throws Exception {
    Robolectric.setDefaultHttpResponse(200, MOCKED_WORD);

    String result = request.loadDataFromNetwork();

    assertEquals(MOCKED_WORD, result);
  }

  @Before
  public void setUp() throws Exception {
    mMockWebServer = new MockWebServer();

    request = new MyStandardRequest(WORD);
  }

  @After
  public void tearDown() throws Exception {
      mMockWebServer.shutdown();
  }
}
