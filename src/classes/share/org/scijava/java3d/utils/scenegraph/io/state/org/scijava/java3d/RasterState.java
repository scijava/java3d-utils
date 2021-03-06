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

package org.scijava.java3d.utils.scenegraph.io.state.org.scijava.java3d;

import java.awt.Dimension;
import java.awt.Point;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.scijava.java3d.DepthComponent;
import org.scijava.java3d.ImageComponent2D;
import org.scijava.java3d.Raster;
import org.scijava.vecmath.Point3f;

import org.scijava.java3d.utils.scenegraph.io.retained.Controller;
import org.scijava.java3d.utils.scenegraph.io.retained.SymbolTableData;

public class RasterState extends GeometryState {

    int image;
    int depthComponent;

    public RasterState(SymbolTableData symbol,Controller control) {
        super( symbol, control );

	// Set up references during save
        if ( node!=null ) {
	    image = control.getSymbolTable().addReference( ((Raster)node).getImage() );
	    depthComponent =
		control.getSymbolTable().addReference( ((Raster)node).getDepthComponent() );
        }
    }

    @Override
    public void writeObject( DataOutput out ) throws IOException {
        super.writeObject( out );

	out.writeInt( image );
	out.writeInt( depthComponent );

	Point3f pos = new Point3f();
	((Raster)node).getPosition( pos );
	control.writePoint3f( out, pos );

	out.writeInt( ((Raster)node).getType() );
	out.writeInt( ((Raster)node).getClipMode() );

	Point offset = new Point();
	((Raster)node).getSrcOffset( offset );
	out.writeInt( offset.x );
	out.writeInt( offset.y );

	Dimension size = new Dimension();
	((Raster)node).getSize( size );
	out.writeInt( size.width );
	out.writeInt( size.height );

	((Raster)node).getDstOffset( offset );
	out.writeInt( offset.x );
	out.writeInt( offset.y );
    }

    @Override
    public void readObject( DataInput in ) throws IOException {
        super.readObject( in );

	image = in.readInt();
	depthComponent = in.readInt();

	((Raster)node).setPosition( control.readPoint3f( in ) );
	((Raster)node).setType( in.readInt() );
	((Raster)node).setClipMode( in.readInt() );
	((Raster)node).setSrcOffset( new Point( in.readInt(), in.readInt() ) );
	((Raster)node).setSize( new Dimension( in.readInt(), in.readInt() ) );
	((Raster)node).setDstOffset( new Point( in.readInt(), in.readInt() ) );
    }

    /**
     * Called when this component reference count is incremented.
     * Allows this component to update the reference count of any components
     * that it references.
     */
    @Override
    public void addSubReference() {
        control.getSymbolTable().incNodeComponentRefCount( image );
        control.getSymbolTable().incNodeComponentRefCount( depthComponent );
    }

    // Set up references during load
    @Override
    public void buildGraph() {
	((Raster)node).setImage( (ImageComponent2D)control.getSymbolTable().getJ3dNode( image ) );
	((Raster)node).setDepthComponent(
	    (DepthComponent)control.getSymbolTable().getJ3dNode( depthComponent ) );
	super.buildGraph(); // Must be last call in method
    }

    @Override
    protected org.scijava.java3d.SceneGraphObject createNode() {
        return new Raster();
    }
}
