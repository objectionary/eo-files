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
package EOorg.EOeolang.EOfs;

import EOorg.EOeolang.EOmemory;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.stream.Stream;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhConst;
import org.eolang.PhMethod;
import org.eolang.PhWith;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * Test.
 *
 * @since 0.1
 * @checkstyle TypeNameCheck (100 lines)
 */
public final class EOmemory_as_outputEOwriteTest {

    @ParameterizedTest
    @MethodSource("packs")
    public void writesBytesToMemory(final String text, final int max) {
        final Phi mem = new EOmemory();
        Phi output = new PhWith(new EOmemory_as_output(), "m", mem);
        final byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        int pos = 0;
        while (true) {
            final byte[] chunk = Arrays.copyOfRange(
                bytes, pos, Integer.min(pos + max, bytes.length)
            );
            output = new PhConst(
                new PhMethod(
                    new PhWith(
                        output.attr("write").get().copy(),
                        "data", new Data.ToPhi(chunk)
                    ),
                    "φ"
                )
            );
            new Dataized(output).take();
            pos += chunk.length;
            if (pos >= bytes.length) {
                new Dataized(output.attr("close").get()).take();
                break;
            }
        }
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
