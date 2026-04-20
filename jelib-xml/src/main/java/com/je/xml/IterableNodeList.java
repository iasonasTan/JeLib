package com.je.xml;

import org.w3c.dom.*;
import java.util.*;
import java.util.*;
import java.util.*;
import java.util.function.*;

public class IterableNodeList implements Iterable<Node> {
    private final NodeList mNodeList;

    public IterableNodeList(NodeList nodeList) {
        mNodeList = nodeList;
    }

    @Override
    public Iterator<Node> iterator() {
        return new Iter(mNodeList);
    }

    @Override
    public void forEach(Consumer<? super Node> action) {
        for (int i = 0; i < mNodeList.getLength(); i++) {
            Node node = mNodeList.item(i);
            action.accept(node);
        }
    }

    @Override
    public Spliterator<Node> spliterator() {
        throw new UnsupportedOperationException();
    }

    private static final class Iter implements Iterator<Node> {
        private Node[] mNodes;
        private int mIndex = -1;
        private boolean mNextCalled = false;

        public Iter(NodeList nodes) {
            mNodes = new Node[nodes.getLength()];
            for (int i = 0; i < nodes.getLength(); i++) {
                mNodes[i] = nodes.item(i);
            }
        }

        @Override
        public boolean hasNext() {
            return mIndex+1 < mNodes.length;
        }

        @Override
        public Node next() {
            if (!hasNext())
                throw new NoSuchElementException();
            mNextCalled = true;
            return mNodes[++mIndex];
        }

        @Override
        public void remove() {
            if (!mNextCalled)
                throw new IllegalStateException();
            mNextCalled = false;

            Node[] newNodes = new Node[mNodes.length - 1];
            System.arraycopy(mNodes, 0, newNodes, 0, mIndex);
            System.arraycopy(mNodes, mIndex+1, newNodes, mIndex, mNodes.length-mIndex-1);

            mNodes = newNodes;
            mIndex--;
        }
    }
}
