package org.apfloat.internal;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

import org.apfloat.ApfloatRuntimeException;
import org.apfloat.spi.DataStorage;
import org.apfloat.spi.ArrayAccess;

/**
 * Disk-based data storage for the <code>float</code> element type.
 *
 * @version 1.8.0
 * @author Mikko Tommila
 */

public class FloatDiskDataStorage
    extends DiskDataStorage
{
    /**
     * Default constructor.
     */

    public FloatDiskDataStorage()
        throws ApfloatRuntimeException
    {
    }

    /**
     * Subsequence constructor.
     *
     * @param floatDiskDataStorage The originating data storage.
     * @param offset The subsequence starting position.
     * @param length The subsequence length.
     */

    protected FloatDiskDataStorage(FloatDiskDataStorage floatDiskDataStorage, long offset, long length)
    {
        super(floatDiskDataStorage, offset, length);
    }

    protected DataStorage implSubsequence(long offset, long length)
        throws ApfloatRuntimeException
    {
        return new FloatDiskDataStorage(this, offset + getOffset(), length);
    }

    private class FloatDiskArrayAccess
        extends FloatMemoryArrayAccess
    {
        // fileOffset is absolute position in file
        public FloatDiskArrayAccess(int mode, long fileOffset, int length)
            throws ApfloatRuntimeException
        {
            super(new float[length], 0, length);
            this.mode = mode;
            this.fileOffset = fileOffset;

            if ((mode & READ) != 0)
            {
                final float[] array = getFloatData();
                WritableByteChannel out = new WritableByteChannel()
                {
                    public int write(ByteBuffer buffer)
                    {
                        FloatBuffer src = buffer.asFloatBuffer();
                        int readLength = src.remaining();

                        src.get(array, this.readPosition, readLength);

                        this.readPosition += readLength;
                        buffer.position(buffer.position() + readLength * 4);

                        return readLength * 4;
                    }

                    public void close() {}
                    public boolean isOpen() { return true; }

                    private int readPosition = 0;
                };

                transferTo(out, fileOffset * 4, (long) length * 4);
            }
        }

        public void close()
            throws ApfloatRuntimeException
        {
            if ((this.mode & WRITE) != 0 && getData() != null)
            {
                final float[] array = getFloatData();
                ReadableByteChannel in = new ReadableByteChannel()
                {
                    public int read(ByteBuffer buffer)
                    {
                        FloatBuffer dst = buffer.asFloatBuffer();
                        int writeLength = dst.remaining();

                        dst.put(array, this.writePosition, writeLength);

                        this.writePosition += writeLength;
                        buffer.position(buffer.position() + writeLength * 4);

                        return writeLength * 4;
                    }

                    public void close() {}
                    public boolean isOpen() { return true; }

                    private int writePosition = 0;
                };

                transferFrom(in, this.fileOffset * 4, (long) array.length * 4);
            }

            super.close();
        }

        private static final long serialVersionUID = 1750388414420962922L;

        private int mode;
        private long fileOffset;
    }

    protected ArrayAccess implGetArray(int mode, long offset, int length)
        throws ApfloatRuntimeException
    {
        return new FloatDiskArrayAccess(mode, getOffset() + offset, length);
    }

    protected ArrayAccess createArrayAccess(int mode, int startColumn, int columns, int rows)
    {
        return new MemoryArrayAccess(mode, new float[columns * rows], startColumn, columns, rows);
    }

    protected ArrayAccess createTransposedArrayAccess(int mode, int startColumn, int columns, int rows)
    {
        return new TransposedMemoryArrayAccess(mode, new float[columns * rows], startColumn, columns, rows);
    }

    private class MemoryArrayAccess
        extends FloatMemoryArrayAccess
    {
        public MemoryArrayAccess(int mode, float[] data, int startColumn, int columns, int rows)
        {
            super(data, 0, data.length);
            this.mode = mode;
            this.startColumn = startColumn;
            this.columns = columns;
            this.rows = rows;
        }

        public void close()
            throws ApfloatRuntimeException
        {
            if ((this.mode & WRITE) != 0 && getData() != null)
            {
                setArray(this, this.startColumn, this.columns, this.rows);
            }
            super.close();
        }

        private static final long serialVersionUID = -3536582909010606907L;

        private int mode,
                    startColumn,
                    columns,
                    rows;
    }

    private class TransposedMemoryArrayAccess
        extends FloatMemoryArrayAccess
    {
        public TransposedMemoryArrayAccess(int mode, float[] data, int startColumn, int columns, int rows)
        {
            super(data, 0, data.length);
            this.mode = mode;
            this.startColumn = startColumn;
            this.columns = columns;
            this.rows = rows;
        }

        public void close()
            throws ApfloatRuntimeException
        {
            if ((this.mode & WRITE) != 0 && getData() != null)
            {
                setTransposedArray(this, this.startColumn, this.columns, this.rows);
            }
            super.close();
        }

        private static final long serialVersionUID = 898289922606519237L;

        private int mode,
                    startColumn,
                    columns,
                    rows;
    }

    private class BlockIterator
        extends AbstractIterator
    {
        public BlockIterator(int mode, long startPosition, long endPosition)
            throws IllegalArgumentException, IllegalStateException, ApfloatRuntimeException
        {
            super(mode, startPosition, endPosition);
            this.arrayAccess = null;
            this.remaining = 0;
        }

        public void next()
            throws IllegalStateException, ApfloatRuntimeException
        {
            checkLength();

            assert (this.remaining > 0);

            checkAvailable();

            this.offset += getIncrement();
            this.remaining--;

            if (this.remaining == 0)
            {
                close();
            }

            super.next();
        }

        public float getFloat()
            throws IllegalStateException, ApfloatRuntimeException
        {
            checkGet();
            checkAvailable();
            return this.data[this.offset];
        }

        public void setFloat(float value)
            throws IllegalStateException, ApfloatRuntimeException
        {
            checkSet();
            checkAvailable();
            this.data[this.offset] = value;
        }

        public <T> T get(Class<T> type)
            throws UnsupportedOperationException, IllegalStateException
        {
            if (!(type.equals(Float.TYPE)))
            {
                throw new UnsupportedOperationException("Unsupported data type " + type.getCanonicalName() + ", the only supported type is float");
            }
            @SuppressWarnings("unchecked")
            T value = (T) (Float) getFloat();
            return value;
        }

        public <T> void set(Class<T> type, T value)
            throws UnsupportedOperationException, IllegalArgumentException, IllegalStateException
        {
            if (!(type.equals(Float.TYPE)))
            {
                throw new UnsupportedOperationException("Unsupported data type " + type.getCanonicalName() + ", the only supported type is float");
            }
            if (!(value instanceof Float))
            {
                throw new IllegalArgumentException("Unsupported value type " + value.getClass().getCanonicalName() + ", the only supported type is Float");
            }
            setFloat((Float) value);
        }

        /**
         * Closes the iterator. This needs to be called only if the
         * iterator is not iterated to the end.
         */

        public void close()
            throws ApfloatRuntimeException
        {
            if (this.arrayAccess != null)
            {
                this.data = null;
                this.arrayAccess.close();
                this.arrayAccess = null;
            }
        }

        private void checkAvailable()
            throws ApfloatRuntimeException
        {
            if (this.arrayAccess == null)
            {
                boolean isForward = (getIncrement() > 0);
                int length = (int) Math.min(getLength(), getBlockSize() / 4);
                long offset = (isForward ? getPosition() : getPosition() - length + 1);

                this.arrayAccess = getArray(getMode(), offset, length);
                this.data = this.arrayAccess.getFloatData();
                this.offset = this.arrayAccess.getOffset() + (isForward ? 0 : length - 1);
                this.remaining = length;
            }
        }

        private static final long serialVersionUID = 8503701548995236882L;

        private ArrayAccess arrayAccess;
        private float[] data;
        private int offset,
                    remaining;
    }

    public Iterator iterator(int mode, long startPosition, long endPosition)
        throws IllegalArgumentException, IllegalStateException, ApfloatRuntimeException
    {
        if ((mode & READ_WRITE) == 0)
        {
            throw new IllegalArgumentException("Illegal mode: " + mode);
        }
        return new BlockIterator(mode, startPosition, endPosition);
    }

    protected int getUnitSize()
    {
        return 4;
    }

    private static final long serialVersionUID = 1045290368963828503L;
}
