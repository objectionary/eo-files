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

import org.eolang.AtBound;
import org.eolang.AtFree;
import org.eolang.AtLambda;
import org.eolang.Data;
import org.eolang.Dataized;
import org.eolang.PhDefault;
import org.eolang.PhWith;
import org.eolang.Phi;

/**
 * Copu.
 *
 * @since 0.1
 * @checkstyle TypeNameCheck (100 lines)
 */
@SuppressWarnings("PMD.AvoidDollarSigns")
public class EOcopy extends PhDefault {

    /**
     * Ctor.
     * @param parent The parent
     * @checkstyle BracketsStructureCheck (200 lines)
     */
    @SuppressWarnings("PMD.ConstructorOnlyInitializesOrCallOtherConstructors")
    public EOcopy(final Phi parent) {
        super(parent);
        this.add("input", new AtFree());
        this.add("output", new AtFree());
        this.add("size", new AtFree());
        this.add("φ", new AtBound(new AtLambda(this, self -> {
            final long size = new Dataized(self.attr("size").get()).take(Long.class);
            int total = 0;
            Phi input = self.attr("input").get();
            Phi output = self.attr("output").get();
            while (true) {
                input = new Dataized(
                    new PhWith(
                        input.attr("read").get().copy(),
                        0, new Data.ToPhi(size)
                    )
                ).take(Phi.class);
                final byte[] chunk = new Dataized(input).take(byte[].class);
                if (chunk.length == 0) {
                    new Dataized(input.attr("close").get()).take();
                    break;
                }
                output = new Dataized(
                    new PhWith(
                        output.attr("write").get().copy(),
                        0, new Data.ToPhi(size)
                    )
                ).take(Phi.class);
                total += chunk.length;
            }
            new Dataized(output.attr("close").get()).take();
            return new Data.ToPhi(total);
        })));
    }

}
