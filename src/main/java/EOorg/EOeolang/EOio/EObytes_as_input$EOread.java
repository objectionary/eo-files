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

import java.util.Arrays;
import org.eolang.AtComposite;
import org.eolang.AtFree;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.ExAbstract;
import org.eolang.PhDefault;
import org.eolang.PhWith;
import org.eolang.Phi;
import org.eolang.XmirObject;

/**
 * Bytes-as-input.read.
 *
 * @since 0.1
 * @checkstyle TypeNameCheck (100 lines)
 */
@XmirObject(oname = "bytes-as-input.read")
public class EObytes_as_input$EOread extends PhDefault {

    /**
     * Ctor.
     * @param sigma The \sigma
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EObytes_as_input$EOread(final Phi sigma) {
        super(sigma);
        this.add("max", new AtFree());
        this.add(
            "φ",
            new AtComposite(
                this,
                rho -> {
                    final long max = new Dataized(rho.attr("max").get()).take(Long.class);
                    final Phi input = rho.attr("σ").get();
                    long next;
                    try {
                        next = new Dataized(input.attr("next").get()).take(Long.class);
                    } catch (final ExAbstract ex) {
                        next = 0L;
                    }
                    final Phi data = input.attr("b").get();
                    final byte[] bytes = new Dataized(data).take(byte[].class);
                    new Dataized(data).take(byte[].class);
                    final byte[] buf = Arrays.copyOfRange(
                        bytes,
                        (int) next,
                        Integer.min((int) (next + max), bytes.length)
                    );
                    return new PhWith(
                        new PhWith(
                            new PhWith(
                                new EObytes_as_input(Phi.Φ),
                                "b", data
                            ),
                            "next", new Data.ToPhi(next + (long) buf.length)
                        ),
                        "buf", new Data.ToPhi(buf)
                    );
                }
            )
        );
    }

}
