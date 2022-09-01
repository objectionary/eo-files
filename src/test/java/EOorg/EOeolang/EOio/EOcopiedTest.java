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

import EOorg.EOeolang.EOmemory;
import java.nio.charset.StandardCharsets;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhWith;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 * Test of copy.
 *
 * @since 0.1
 * @checkstyle TypeNameCheck (100 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class EOcopiedTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/EOlang/EOio/test-samples.csv")
    public void readsBytes(final String text, final int size) {
        final byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        Phi copied = new PhWith(
            new EOcopied(Phi.Φ),
            "input",
            new PhWith(
                new EObytes_as_input(Phi.Φ),
                "b", new Data.ToPhi(bytes)
            )
        );
        final Phi mem = new EOmemory(Phi.Φ);
        mem.attr(0).put(new Data.ToPhi(new byte[] {}));
        copied = new PhWith(
            copied, "output",
            new PhWith(
                new EOmemory_as_output(Phi.Φ),
                "m", mem
            )
        );
        copied = new PhWith(
            copied, "size",
            new Data.ToPhi((long) size)
        );
        MatcherAssert.assertThat(
            new Dataized(copied).take(Long.class),
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
}
