/*
 *  Copyright (c) 2023 Aliaksei Bialiauski, EO-CQRS
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.eocqrs.kafka.fake;

import io.github.eocqrs.xfake.InFile;
import io.github.eocqrs.xfake.Synchronized;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Test case for {@link TopicExists}.
 *
 * @author Aliaksei Bialiauski (abialiauski.dev@gmail.com)
 * @since 0.3.5
 */
final class TopicExistsTest {

  private FkBroker broker;

  @BeforeEach
  void setUp() throws Exception {
    this.broker = new InXml(
      new Synchronized(
        new InFile(
          "topic-exists-test",
          "<broker/>"
        )
      )
    ).with(new TopicDirs("1.test").value());
  }

  @Test
  void returnsTrueOnExistingTopic() throws Exception {
    MatcherAssert.assertThat(
      "Topic is present",
      new TopicExists("1.test", this.broker).value(),
      Matchers.equalTo(true)
    );
  }

  @Test
  void returnsFalseOnUnknownTopic() throws Exception {
    MatcherAssert.assertThat(
      "Topic is not present",
      new TopicExists("unknown.test", this.broker).value(),
      Matchers.equalTo(false)
    );
  }
}
