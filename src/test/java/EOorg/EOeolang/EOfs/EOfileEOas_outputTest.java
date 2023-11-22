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
package EOorg.EOeolang.EOfs;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
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
 */
public final class EOfileEOas_outputTest {

    /**
     * Temp dir.
     * @checkstyle VisibilityModifierCheck (4 lines)
     */
    @TempDir
    public Path temp;

    @ParameterizedTest
    @CsvFileSource(resources = "/EOlang/EOio/test-samples.csv")
    public void writesBytesToFile(final String text, final String max) throws IOException {
        final Path file = this.temp.resolve("test.txt");
        Phi output = new PhWith(
            new PhMethod(
                new PhWith(
                    new EOfile(Phi.Φ), "path",
                    new Data.ToPhi(file.toAbsolutePath().toString())
                ),
                "as-output"
            ),
            "mode",
            new Data.ToPhi("w")
        );
        final byte[] bytes = text.getBytes(StandardCharsets.UTF_8);
        int pos = 0;
        while (true) {
            final byte[] chunk = Arrays.copyOfRange(
                bytes, pos, Integer.min(pos + Integer.parseInt(max), bytes.length)
            );
            output = new PhConst(
                new PhMethod(
                    new PhWith(
                        output.attr("write").get(),
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
            file.toFile().length(),
            Matchers.equalTo((long) bytes.length)
        );
        MatcherAssert.assertThat(
            new String(Files.readAllBytes(file), StandardCharsets.UTF_8),
            Matchers.equalTo(text)
        );
    }

}
