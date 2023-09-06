package primalcat.thaumcraft.aspects;

import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;

public class AspectListIterator implements Iterator<Map.Entry<Aspect, Integer>> {
    private AspectList aspectList;
    private Iterator<Map.Entry<Aspect, Integer>> iterator;

    public AspectListIterator(AspectList aspectList) {
        this.aspectList = aspectList;
        this.iterator = aspectList.aspects.entrySet().iterator();
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public Map.Entry<Aspect, Integer> next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return iterator.next();
    }

    @Override
    public void remove() {
        iterator.remove();
    }
}
