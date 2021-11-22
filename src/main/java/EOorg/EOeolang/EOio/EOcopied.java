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
package EOorg.EOeolang.EOio;

import org.eolang.AtComposite;
import org.eolang.AtFree;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhConst;
import org.eolang.PhDefault;
import org.eolang.PhMethod;
import org.eolang.PhWith;
import org.eolang.Phi;

/**
 * Copu.
 *
 * @since 0.1
 * @checkstyle TypeNameCheck (100 lines)
 * @checkstyle ClassDataAbstractionCouplingCheck (500 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
public class EOcopied extends PhDefault {

    /**
     * Ctor.
     * @param sigma The \sigma
     * @checkstyle BracketsStructureCheck (200 lines)
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOcopied(final Phi sigma) {
        super(sigma);
        this.add("input", new AtFree());
        this.add("output", new AtFree());
        this.add("size", new AtFree());
        this.add("φ", new AtComposite(this, rho -> {
            final long size = new Dataized(rho.attr("size").get()).take(Long.class);
            int total = 0;
            Phi input = rho.attr("input").get();
            Phi output = rho.attr("output").get();
            while (true) {
                input = new PhConst(
                    new PhWith(
                        input.attr("read").get(),
                        "max", new Data.ToPhi(size)
                    )
                );
                final byte[] chunk = new Dataized(input).take(byte[].class);
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
                total += chunk.length;
                if (chunk.length == 0) {
                    new Dataized(input.attr("close").get()).take();
                    break;
                }
            }
            new Dataized(output.attr("close").get()).take();
            return new Data.ToPhi((long) total);
        }));
    }

}
