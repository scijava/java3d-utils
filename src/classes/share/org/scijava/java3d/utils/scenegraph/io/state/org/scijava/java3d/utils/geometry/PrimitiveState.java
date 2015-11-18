/*
 * Copyright (c) 2007 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * - Redistribution of source code must retain the above copyright
 *   notice, this list of conditions and the following disclaimer.
 *
 * - Redistribution in binary form must reproduce the above copyright
 *   notice, this list of conditions and the following disclaimer in
 *   the documentation and/or other materials provided with the
 *   distribution.
 *
 * Neither the name of Sun Microsystems, Inc. or the names of
 * contributors may be used to endorse or promote products derived
 * from this software without specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any
 * kind. ALL EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND
 * WARRANTIES, INCLUDING ANY IMPLIED WARRANTY OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE OR NON-INFRINGEMENT, ARE HEREBY
 * EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN") AND ITS LICENSORS SHALL
 * NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF
 * USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR
 * ANY LOST REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL,
 * CONSEQUENTIAL, INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND
 * REGARDLESS OF THE THEORY OF LIABILITY, ARISING OUT OF THE USE OF OR
 * INABILITY TO USE THIS SOFTWARE, EVEN IF SUN HAS BEEN ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or
 * intended for use in the design, construction, operation or
 * maintenance of any nuclear facility.
 *
 */

package org.scijava.java3d.utils.scenegraph.io.state.org.scijava.java3d.utils.geometry;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.scijava.java3d.utils.geometry.Primitive;
import org.scijava.java3d.utils.scenegraph.io.retained.Controller;
import org.scijava.java3d.utils.scenegraph.io.retained.SymbolTableData;
import org.scijava.java3d.utils.scenegraph.io.state.org.scijava.java3d.GroupState;

public class PrimitiveState extends GroupState {

    protected int primflags;

    public PrimitiveState( SymbolTableData symbol, Controller control ) {
	super( symbol, control );

    }

    @Override
    public void writeConstructorParams( DataOutput out ) throws IOException {
	super.writeConstructorParams( out );

        out.writeInt( ((Primitive)node).getPrimitiveFlags() );
    }

    @Override
    public void readConstructorParams( DataInput in ) throws IOException {
       super.readConstructorParams(in);

       primflags = in.readInt();
    }

    @Override
    public void buildGraph() {
        super.buildGraph(); // This must be the last call in the method
    }

  /**
   * Returns true if the groups children should be saved.
   *
   * This is overridden by 'black box' groups such a geometry primitives
   */
  @Override
  protected boolean processChildren() {
      return false;
  }
}