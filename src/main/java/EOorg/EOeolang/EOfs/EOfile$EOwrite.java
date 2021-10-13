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
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.LinkedList;
import org.eolang.phi.AtBound;
import org.eolang.phi.AtFree;
import org.eolang.phi.AtLambda;
import org.eolang.phi.Data;
import org.eolang.phi.Dataized;
import org.eolang.phi.PhDefault;
import org.eolang.phi.Phi;

/**
 * File.write.
 *
 * @since 0.1
 * @checkstyle TypeNameCheck (100 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
public class EOfile$EOwrite extends PhDefault {

    /**
     * Ctor.
     * @param parent The parent
     * @checkstyle BracketsStructureCheck (200 lines)
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOfile$EOwrite(final Phi parent) {
        super(parent);
        this.add("input", new AtFree());
        this.add("mode", new AtFree());
        this.add("φ", new AtBound(new AtLambda(this, self -> {
            final String mode = new Dataized(self.attr("mode").get().copy()).take(String.class);
            final Path path = Paths.get(
                new Dataized(
                    self.attr("ρ").get().attr("path").get()
                ).take(String.class)
            );
            int total = 0;
            try (OutputStream out = EOfile$EOwrite.stream(path, mode)) {
                final Phi input = self.attr("input").get().copy();
                final byte[] chunk = new Dataized(input).take(byte[].class);
                out.write(chunk);
                total += chunk.length;
            }
            return new Data.ToPhi(total);
        })));
    }

    /**
     * Make an output stream.
     * @param path The path
     * @param opts Opts
     * @return Stream
     * @throws IOException If fails
     */
    private static OutputStream stream(final Path path, final String opts)
        throws IOException {
        final Collection<OpenOption> options = new LinkedList<>();
        for (final char chr : opts.toCharArray()) {
            if (chr == 'w') {
                options.add(StandardOpenOption.WRITE);
            }
            if (chr == 'a') {
                options.add(StandardOpenOption.APPEND);
            }
        }
        return Files.newOutputStream(path, options.toArray(new OpenOption[0]));
    }

}
