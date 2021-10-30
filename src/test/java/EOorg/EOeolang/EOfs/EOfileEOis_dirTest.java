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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhEta;
import org.eolang.PhWith;
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
public final class EOfileEOis_dirTest {

    @Test
    public void isDirectory(@TempDir final Path temp) throws Exception {
        final Path file = temp.resolve("a.txt");
        Files.write(file, "Hello, world!".getBytes());
        MatcherAssert.assertThat(
            new Dataized(
                new EOfile$EOis_dir(
                    new PhWith(
                        new EOfile(new PhEta()),
                        "path",
                        new Data.ToPhi(file.toAbsolutePath().toString())
                    )
                )
            ).take(Boolean.class),
            Matchers.is(false)
        );
    }

    @Test
    public void isCurrentDirectory() {
        final Path file = Paths.get(".");
        MatcherAssert.assertThat(
            new Dataized(
                new EOfile$EOis_dir(
                    new PhWith(
                        new EOfile(new PhEta()),
                        "path",
                        new Data.ToPhi(file.toAbsolutePath().toString())
                    )
                )
            ).take(Boolean.class),
            Matchers.is(true)
        );
    }

}
