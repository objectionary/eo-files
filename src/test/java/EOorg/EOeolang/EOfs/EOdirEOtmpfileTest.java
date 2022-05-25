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

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhConst;
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
public final class EOdirEOtmpfileTest {

    @Test
    public void makesTempFile(@TempDir final Path temp) {
        final Phi phi = new EOdir$EOtmpfile(
            new PhWith(
                new EOfile(Phi.Φ),
                "path",
                new Data.ToPhi(temp.toAbsolutePath().toString())
            )
        );
        final String path = new Dataized(phi).take(String.class);
        MatcherAssert.assertThat(
            Files.exists(Paths.get(path)),
            Matchers.is(true)
        );
    }

    @Test
    public void makesNewFile(@TempDir final Path temp) {
        final Phi phi = new EOdir$EOtmpfile(
            new PhWith(
                new EOfile(Phi.Φ),
                0, new Data.ToPhi(temp.toAbsolutePath().toString())
            )
        );
        MatcherAssert.assertThat(
            new Dataized(phi).take(String.class),
            Matchers.not(
                Matchers.equalTo(new Dataized(phi).take(String.class))
            )
        );
    }

    @Test
    public void makesSameFileWithConst(@TempDir final Path temp) {
        final Phi phi = new PhConst(
            new EOdir$EOtmpfile(
                new PhWith(
                    new EOfile(Phi.Φ),
                    0, new Data.ToPhi(temp.toAbsolutePath().toString())
                )
            )
        );
        MatcherAssert.assertThat(
            new Dataized(phi).take(String.class),
            Matchers.equalTo(new Dataized(phi).take(String.class))
        );
    }
}
