//
// Triple Play - utilities for use in PlayN-based games
// Copyright (c) 2011-2012, Three Rings Design, Inc. - All rights reserved.
// http://github.com/threerings/tripleplay/blob/master/LICENSE

package tripleplay.particle.init;

import playn.core.Layer;

import pythagoras.f.Point;

import tripleplay.particle.Emitter;
import tripleplay.particle.Initializer;
import tripleplay.particle.ParticleBuffer;

/**
 * Initializers for a particle's transform (scale, rotation and position).
 */
public class Transform
{
    /**
     * Returns an initializer that configures a particle with a constant translation, and no
     * scaling or rotation.
     */
    public static Initializer constant (float tx, float ty) {
        return constant(1, 0, tx, ty);
    }

    /**
     * Returns an initializer that configures a particle with a constant transform.
     */
    public static Initializer constant (final float scale, final float rotation,
                                        final float tx, final float ty) {
        return new Initializer() {
            @Override public void init (int index, float[] data, int start) {
                data[start + ParticleBuffer.POS_X] = tx;
                data[start + ParticleBuffer.POS_Y] = ty;
                data[start + ParticleBuffer.SCALE] = scale;
                data[start + ParticleBuffer.ROT] = rotation;
            }
        };
    }

    /**
     * Returns an initializer that configures a particle with the same transform (scale, rotation,
     * position) as the emitter's layer. This will the the fully computed transform, not the
     * layer's local transform.
     */
    public static Initializer emitter (final Emitter emitter) {
        return new Initializer() {
            @Override public void willInit (int count) {
                Layer.Util.layerToScreen(emitter.layer, _pos.set(0, 0), _pos);
            }
            @Override public void init (int index, float[] data, int start) {
                data[start + ParticleBuffer.POS_X] = _pos.x;
                data[start + ParticleBuffer.POS_Y] = _pos.y;
                data[start + ParticleBuffer.SCALE] = 1; // TODO
                data[start + ParticleBuffer.ROT] = 0; // TODO
            }
            protected final Point _pos = new Point();
        };
    }
}