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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhWith;
import org.eolang.Phi;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Test.
 *
 * @since 0.1
 * @checkstyle TypeNameCheck (100 lines)
 */
public final class EOdirEOrm_rfTest {

    @Test
    public void makesDirectory(@TempDir final Path temp) throws IOException {
        final Path file = temp.resolve("foo/bar/test.txt");
        file.toFile().getParentFile().mkdirs();
        Files.write(file, "".getBytes());
        MatcherAssert.assertThat(
            new Dataized(
                new EOdir$EOrm_rf(
                    new PhWith(
                        new EOfile(Phi.Φ),
                        "path",
                        new Data.ToPhi(temp.toAbsolutePath().toString())
                    )
                )
            ).take(Boolean.class),
            Matchers.is(true)
        );
        MatcherAssert.assertThat(
            Files.exists(temp),
            Matchers.is(false)
        );
    }
}
