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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhConst;
import org.eolang.PhMethod;
import org.eolang.PhWith;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

/**
 * Test.
 *
 * @since 0.1
 * @checkstyle TypeNameCheck (100 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
public final class EOfileEOas_inputTest {

    /**
     * Temp dir.
     * @checkstyle VisibilityModifierCheck (4 lines)
     */
    @TempDir
    public Path temp;

    @ParameterizedTest
    @CsvFileSource(resources = "/EOlang/EOio/test-samples.csv")
    public void readsBytes(final String text, final int max) throws IOException {
        final Path file = this.temp.resolve("test.txt");
        Files.write(file, text.getBytes(StandardCharsets.UTF_8));
        Phi input = new PhWith(
            new PhMethod(
                new PhWith(
                    new EOfile(Phi.Φ), "path",
                    new Data.ToPhi(file.toAbsolutePath().toString())
                ),
                "as-input"
            ),
            "mode",
            new Data.ToPhi("r")
        );
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        while (true) {
            input = new PhConst(
                new PhMethod(
                    new PhWith(
                        input.attr("read").get().copy(input),
                        "max", new Data.ToPhi((long) max)
                    ),
                    "φ"
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
}
