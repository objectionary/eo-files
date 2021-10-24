/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021 Yegor Bugayenko
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
// @checkstyle PackageNameCheck (1 line)
package EOorg.EOeolang.EOio;

import EOorg.EOeolang.EOmemory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhEta;
import org.eolang.PhWith;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test of copy.
 *
 * @since 0.1
 * @checkstyle TypeNameCheck (100 lines)
 */
public final class EOcopiedTest {

    @ParameterizedTest
    @MethodSource("packs")
    public void readsBytes(final String text, final int size) throws IOException {
        final byte[] bytes = text.getBytes();
        Phi copy = new PhWith(
            new EOcopied(new PhEta()), "input",
            new PhWith(
                new EObytes_as_input(),
                "b", new Data.ToPhi(bytes)
            )
        );
        final Phi mem = new EOmemory();
        copy = new PhWith(
            copy, "output",
            new PhWith(
                new EOmemory_as_output(),
                "m", mem
            )
        );
        copy = new PhWith(
            copy, "size",
            new Data.ToPhi((long) size)
        );
        MatcherAssert.assertThat(
            new Dataized(copy).take(Long.class),
            Matchers.equalTo((long) bytes.length)
        );
        MatcherAssert.assertThat(
            new String(
                new Dataized(mem).take(byte[].class),
                StandardCharsets.UTF_8
            ),
            Matchers.equalTo(text)
        );
    }

    static Stream<Arguments> packs() {
        return Stream.of(
            Arguments.arguments("", 1),
            Arguments.arguments("x", 1),
            Arguments.arguments("xx", 1),
            Arguments.arguments("你好, друг!", 2),
            Arguments.arguments("test", 10),
            Arguments.arguments("", 10),
            Arguments.arguments("hello, друг!", 1)
        );
    }
}
