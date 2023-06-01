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
import org.eolang.AtComposite;
import org.eolang.AtFree;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.PhWith;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Memory-as-output.write.
 *
 * @since 0.1
 * @checkstyle TypeNameCheck (100 lines)
 */
@XmirObject(oname = "memory-as-output.write")
public class EOmemory_as_output$EOwrite extends PhDefault {

    /**
     * Ctor.
     * @param sigma The \sigma
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOmemory_as_output$EOwrite(final Phi sigma) {
        super(sigma);
        this.add("data", new AtFree());
        this.add(
            "φ",
            new AtComposite(
                this,
                rho -> {
                    final Phi mem = rho.attr("σ").get().attr("m").get();
                    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    baos.write(new Dataized(mem).take(byte[].class));
                    final byte[] chunk = new Dataized(rho.attr("data").get()).take(byte[].class);
                    baos.write(chunk);
                    new Dataized(
                        new PhWith(
                            mem.attr("write").get(),
                            0, new Data.ToPhi(baos.toByteArray())
                        )
                    ).take();
                    return new PhWith(
                        new EOmemory_as_output(rho),
                        "m", mem
                    );
                }
            )
        );
    }

}
