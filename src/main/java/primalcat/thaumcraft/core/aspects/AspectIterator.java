package primalcat.thaumcraft.core.aspects;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AspectIterator implements Iterator<Aspect> {

    private List<Aspect> aspects;
    private int currentIndex;


    public AspectIterator() {
        aspects = new ArrayList<>();
        currentIndex = 0;
    }

    public void addAspect(Aspect aspect) {
        aspects.add(aspect);
    }

    public void removeAspect(Aspect aspect) {
        aspects.remove(aspect);
    }

    @Override
    public boolean hasNext() {
        return currentIndex < aspects.size();
    }

    @Override
    public Aspect next() {
        if (!hasNext()) {
            throw new IllegalStateException("No more aspects to iterate.");
        }
        return aspects.get(currentIndex++);
    }

    // Optionally, you can override the remove method if you want to remove the current aspect.
    @Override
    public void remove() {
        if (currentIndex > 0) {
            aspects.remove(--currentIndex);
        } else {
            throw new IllegalStateException("You must call next() before remove().");
        }
    }
}
