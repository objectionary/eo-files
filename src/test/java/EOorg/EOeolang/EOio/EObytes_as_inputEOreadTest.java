/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021-2022 Yegor Bugayenko
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhConst;
import org.eolang.PhMethod;
import org.eolang.PhWith;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 * Test.
 *
 * @since 0.1
 * @checkstyle TypeNameCheck (100 lines)
 */
public final class EObytes_as_inputEOreadTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/EOlang/EOio/test-samples.csv")
    public void readsBytes(final String text, final String max) throws IOException {
        final Phi bytes = new Data.ToPhi(text.getBytes(StandardCharsets.UTF_8));
        Phi input = new PhWith(
            new EObytes_as_input(bytes),
            "b", bytes
        );
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (true) {
            input = new PhConst(
                new PhWith(
                    input.attr("read").get(),
                    "max", new Data.ToPhi(Long.parseLong(max))
                )
            );
            final byte[] chunk = new Dataized(input).take(byte[].class);
            if (chunk.length == 0) {
                new Dataized(input.attr("close").get()).take();
                break;
            }
            baos.write(chunk);
        }
        MatcherAssert.assertThat(
            new String(baos.toByteArray(), StandardCharsets.UTF_8),
            Matchers.equalTo(text)
        );
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/EOlang/EOio/test-samples.csv")
    public void readsBytesFromString(final String text, final String max) {
        final Phi input = new PhWith(
            new EObytes_as_input(Phi.Φ),
            "b",
            new PhMethod(
                new Data.ToPhi(text),
                "as-bytes"
            )
        );
        final Phi first = new PhConst(
            new PhWith(
                input.attr("read").get(),
                "max", new Data.ToPhi(Long.parseLong(max))
            )
        );
        final Phi last = new PhConst(
            new PhWith(
                first.attr("read").get(),
                "max", new Data.ToPhi(Long.parseLong(max))
            )
        ).copy();
        Assertions.assertDoesNotThrow(
            () -> new Dataized(last).take(byte[].class)
        );
    }
}
